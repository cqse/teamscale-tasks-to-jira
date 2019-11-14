package eu.cqse.qcs.jiratasks.jiraclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Field property of Jira which is identified by an id.
 */
public class IdField {

	@JsonProperty("id")
	private String id;

	public IdField(String id) {
		this.id = id;
	}

	public IdField(long id) {
		this(Long.toString(id));
	}

	public String getId() {
		return id;
	}

}
