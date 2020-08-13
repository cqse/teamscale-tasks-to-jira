package eu.cqse.qcs.jiratasks;

import eu.cqse.qcs.jiratasks.jiraclient.IssueResponse;
import eu.cqse.qcs.jiratasks.jiraclient.JiraClient;
import eu.cqse.qcs.jiratasks.teamscaleclient.Task;
import eu.cqse.qcs.jiratasks.teamscaleclient.TeamscaleClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creator for Jira issues from Teamscale tasks.
 */
public class JiraTaskCreator {

	/** basic settings, e.g. server urls */
	private TasksToJiraSettings settings;
	/** Client to access Teamscale */
	private TeamscaleClient teamscaleClient;
	/** Coient to access Jira */
	private JiraClient jiraClient;

	/**
	 * Main method - requires path to settings file as first argument and ids of
	 * tasks to create in Jira as following arguments.
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Error: Expected argument for settings file and at least one task id");
		}
		try {
			TasksToJiraSettings settings = new TasksToJiraSettings(new File(args[0]));
			List<Integer> taskIds = new ArrayList<>();
			for (int i = 1; i < args.length; i++) {
				taskIds.add(Integer.parseInt(args[i]));
			}
			JiraTaskCreator taskCreator = new JiraTaskCreator(settings);
			taskCreator.run(taskIds);

		} catch (IOException | JiraTaskException e) {
			e.printStackTrace();
		}
	}

	JiraTaskCreator(TasksToJiraSettings settings) {
		this.settings = settings;
		teamscaleClient = new TeamscaleClient(settings);
		jiraClient = new JiraClient(settings);
	}

	/**
	 * Runs task creation.
	 */
	 void run(List<Integer> taskIds) throws JiraTaskException {
		List<Task> selectedTasks = readTasksFromTeamscale(taskIds);
		for (Task task : selectedTasks) {
			System.out.println("\n" + task.getId() + " : " + task.getSubject());
			IssueResponse newIssue = pushTaskToJira(task);
			updateTaskInTeamscale(task, newIssue);
		}
		System.out.println("\nfinished.");

	}

	/**
	 * Reads task objects from Teamscale
	 */
	private List<Task> readTasksFromTeamscale(List<Integer> taskIds) throws JiraTaskException {
		List<Task> selectedTasks = teamscaleClient.retrieveTasks(taskIds);
		return selectedTasks;
	}

	/**
	 * Pushes a task to Jira
	 */
	private IssueResponse pushTaskToJira(Task task) throws JiraTaskException {
		IssueResponse newIssue = jiraClient.pushTask(task);
		System.out.println("  pushed to Jira, issue: " + newIssue.getKey());
		return newIssue;
	}

	/**
	 * Updates the task in Teamscale to add information on the Jira issue.
	 */
	private void updateTaskInTeamscale(Task task, IssueResponse newIssue) throws JiraTaskException {
		addIssueLinkToTask(task, newIssue);
		teamscaleClient.updateTask(task);
		System.out.println("  added Jira link in task");
	}

	/**
	 * Adds an link to the Jira issue to the task description.
	 */
	private void addIssueLinkToTask(Task task, IssueResponse issue) {
		String markdownIssueLink = "\n\n*Jira Issue: [" + issue.getKey() + "](" + settings.jiraUrl + "/browse/"
				+ issue.getKey() + ")*";
		task.setDescription(task.getDescription() + markdownIssueLink);
	}

}
