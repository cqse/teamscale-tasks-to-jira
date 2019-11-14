package eu.cqse.qcs.jiratasks.teamscaleclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * API for the required Teamscale REST services.
 */
public interface ITeamscaleAPI {
	/**
	 * Gets all tasks for a project
	 */
	@GET("/api/projects/{project}/tasks")
	Call<List<Task>> getTasks(@Path("project") String project);

	/**
	 * Updates a task, e.g. to add Link to Jira
	 */
	@PUT("/api/projects/{project}/tasks/{id}?keep-findings=true")
	Call<Task> updateTask(@Path("project") String project, @Path("id") int taskId, @Body Task task);
}
