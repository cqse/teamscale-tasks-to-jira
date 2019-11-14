package eu.cqse.qcs.jiratasks.jiraclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response data after creating an issue.
 */
@JsonIgnoreProperties({ "self" })
public class IssueResponse {

	/** Jira JSON field name */
	private static final String KEY = "key";

	/** Jira JSON field name */
	private static final String ID = "id";

	/** Numeric id of the issue */
	@JsonProperty(ID)
	private String id;

	/** Key, e.g. ABC-123, of the issue */
	@JsonProperty(KEY)
	private String key;

	@JsonCreator
	public IssueResponse(@JsonProperty(ID) String id, @JsonProperty(KEY) String key) {
		this.id = id;
		this.key = key;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
}
