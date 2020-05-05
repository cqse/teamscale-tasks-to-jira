package eu.cqse.qcs.jiratasks.jiraclient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * API for Jira REST calls
 */
public interface IJiraAPI {

	/**
	 * Creates a new {@link Issue}, the issue key and id is contained in the
	 * returned response.
	 */
	@Headers({"Accept: application/json"})
	@POST("/rest/api/2/issue/")
	Call<IssueResponse> createIssue(@Body Issue issue);

	/**
	 * Adds a {@link RemoteLink} to an issue of the given id or key.
	 */
	@POST("/rest/api/2/issue/{issueIdOrKey}/remotelink")
	Call<Void> createRemoteLink(@Path("issueIdOrKey") String issueIdOrKey, @Body RemoteLink url);

}
