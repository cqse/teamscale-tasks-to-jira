package eu.cqse.qcs.jiratasks.teamscaleclient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import eu.cqse.qcs.jiratasks.JiraTaskException;
import eu.cqse.qcs.jiratasks.RestClientBase;
import eu.cqse.qcs.jiratasks.TasksToJiraSettings;
import retrofit2.Response;

/** Client for communication with Teamscale server */
public class TeamscaleClient extends RestClientBase {

	/** Service API */
	private final ITeamscaleAPI teamscaleAPI;

	public TeamscaleClient(TasksToJiraSettings settings) {
		super("Teamscale", settings);
		teamscaleAPI = createAPI(ITeamscaleAPI.class, settings.teamscaleUrl, settings.teamscaleUser,
				settings.teamscaleApiKey, settings.teamscaleTrustAllSslCerts);
	}

	/** Retrieves the tasks specified in the settings */
	public List<Task> retrieveTasks(List<Integer> taskIds) throws JiraTaskException {
		try {
			Response<List<Task>> response = teamscaleAPI.getTasks(settings.teamscaleProject).execute();
			throwErrorOnUnsuccessfulResponse(response);
			return response.body().stream().filter(task -> taskIds.contains(task.getId())).collect(Collectors.toList());
		} catch (IOException e) {
			throw new JiraTaskException("Error when trying to get tasks from Teamscale", e);
		}
	}

	/**
	 * Updates the given task in Teamscale
	 */
	public void updateTask(Task task) throws JiraTaskException {
		try {
			Response<Task> response = teamscaleAPI.updateTask(settings.teamscaleProject, task.getId(), task).execute();
			throwErrorOnUnsuccessfulResponse(response);
		} catch (IOException e) {
			throw new JiraTaskException("Error when trying to update task " + task.getId() + " in Teamscale", e);
		}
	}

}
