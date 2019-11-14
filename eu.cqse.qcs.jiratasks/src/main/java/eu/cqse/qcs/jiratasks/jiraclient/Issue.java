package eu.cqse.qcs.jiratasks.jiraclient;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Issue {

	/** Jira JSON field name */
	private static final String ISSUETYPE = "issuetype";
	/** Jira JSON field name */
	private static final String DESCRIPTION = "description";
	/** Jira JSON field name */
	private static final String SUMMARY = "summary";
	/** Jira JSON field name */
	private static final String PROJECT = "project";
	/** Jira JSON field name */
	private static final String FIELDS = "fields";

	/**
	 * Map holding the field values, as all relevant values of an issue are within a
	 * object
	 */
	@JsonProperty(FIELDS)
	private Map<String, Object> fields;

	public Issue(String projectKey, String summary, String description, long issueTypeId) {
		fields = new HashMap<>();
		fields.put(PROJECT, new KeyField(projectKey));
		fields.put(SUMMARY, summary);
		fields.put(DESCRIPTION, description);
		fields.put(ISSUETYPE, new IdField(issueTypeId));
	}

	/**
	 * Sets additional field value, for example for epic link what is a custom field
	 */
	public void setAdditionalField(String fieldId, Object value) {
		fields.put(fieldId, value);
	}

	/**
	 * Sets additional field values from the given map.
	 */
	public void setAddtionalFields(Map<String, Object> additionalFieldsMap) {
		fields.putAll(additionalFieldsMap);
	}

	public Map<String, Object> getFields() {
		return fields;
	}
}
