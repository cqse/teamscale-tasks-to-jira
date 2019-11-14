package eu.cqse.qcs.jiratasks.jiraclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Field property of Jira which is identified by an key.
 */
public class KeyField {

	@JsonProperty("key")
	private String key;

	public KeyField(String key) {
		this.key = key;
	}

	public KeyField(long id) {
		this(Long.toString(id));
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

}
