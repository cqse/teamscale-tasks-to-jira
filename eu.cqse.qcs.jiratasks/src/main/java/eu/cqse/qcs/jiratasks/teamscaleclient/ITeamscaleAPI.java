package eu.cqse.qcs.jiratasks.teamscaleclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * API for the required Teamscale REST services.
 */
public interface ITeamscaleAPI {
	/**
	 * Gets all tasks for a project
	 */
	@Headers({ "Accept: application/json" })
	@GET("projects/{project}/tasks")
	Call<List<Task>> getTasks(@Path("project") String project);

	/**
	 * Updates a task, e.g. to add Link to Jira
	 */
	@Headers({ "Accept: application/json" })
	@PUT("projects/{project}/tasks/{id}?keep-findings=true")
	Call<Task> updateTask(@Path("project") String project, @Path("id") int taskId, @Body Task task);
}
