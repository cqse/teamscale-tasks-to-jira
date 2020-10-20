package eu.cqse.qcs.jiratasks.jiraclient;

import java.io.IOException;

import eu.cqse.qcs.jiratasks.JiraTaskCreatorUtils;
import eu.cqse.qcs.jiratasks.JiraTaskException;
import eu.cqse.qcs.jiratasks.RestClientBase;
import eu.cqse.qcs.jiratasks.TasksToJiraSettings;
import eu.cqse.qcs.jiratasks.teamscaleclient.Task;
import retrofit2.Response;

/**
 * Client for Jira REST API
 */
public class JiraClient extends RestClientBase {

	/** Service API */
	private final IJiraAPI jiraAPI;

	/** Constructor */
	public JiraClient(TasksToJiraSettings settings) {
		super("Jira", settings);
		jiraAPI = createAPI(IJiraAPI.class, settings.jiraUrl, settings.jiraUser, settings.jiraPassword,
				settings.jiraTrustAllSslCerts);
	}

	/**
	 * Pushes a {@link Task} as new issue to Jira
	 *
	 * @return the {@link IssueResponse} for the created issue
	 */
	public IssueResponse pushTask(Task task) throws JiraTaskException {
		try {
			IssueResponse newIssue = createNewIssue(task);
			addLinkToTask(task, newIssue.getId());
			return newIssue;
		} catch (IOException e) {
			throw new JiraTaskException("Error when trying to push task " + task.getId() + " to Jira", e);
		}
	}

	/**
	 * Adds a remote link pointing the task in Teamscale to the Jira issue of the
	 * given id.
	 */
	private void addLinkToTask(Task task, String issueId) throws IOException {
		RemoteLink remoteLink = new RemoteLink(JiraTaskCreatorUtils.buildUrlForTask(settings, task),
				"Teamscale: Task#" + task.getId(), settings.teamscaleUrl + "/favicon.ico");
		Response<Void> response = jiraAPI.createRemoteLink(issueId, remoteLink).execute();
		throwErrorOnUnsuccessfulResponse(response);
	}

	/**
	 * Creates a new Jira issue from the given {@link Task}
	 */
	private IssueResponse createNewIssue(Task task) throws IOException {
		Issue issue = new Issue(settings.jiraProject, buildIssueTitle(task), buildIssueDescription(task),
				settings.jiraIssueType);
		if (settings.jiraEpicLinkFieldName != null) {
			issue.setAdditionalField(settings.jiraEpicLinkFieldName, settings.jiraEpicKey);
		}
		issue.setAddtionalFields(settings.jiraAddtionalFields);
		Response<IssueResponse> response = jiraAPI.createIssue(issue).execute();
		throwErrorOnUnsuccessfulResponse(response);
		return response.body();
	}

	/**
	 * Builds the title for the issue by prepending a prefix, if set.
	 */
	private String buildIssueTitle(Task task) {
		String titlePrefix = settings.issueTitlePrefix;

		if (titlePrefix == null || titlePrefix.isEmpty()) {
			return task.getSubject();
		}

		return titlePrefix + " " + task.getSubject();
	}

	/**
	 * Builds the description Text for the issue
	 */
	private String buildIssueDescription(Task task) {
		String taskLink = "[Task #" + task.getId() + "|" + JiraTaskCreatorUtils.buildUrlForTask(settings, task) + "]";

		return JiraTaskCreatorUtils.convertMarkdownToJira(task.getDescription()) + "\n\n"
				+ settings.taskLinkText.replace("{task}", taskLink);

	}

}
