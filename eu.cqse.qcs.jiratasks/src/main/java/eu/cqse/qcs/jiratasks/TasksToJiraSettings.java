package eu.cqse.qcs.jiratasks;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.conqat.lib.commons.filesystem.FileSystemUtils;
import org.conqat.lib.commons.string.StringUtils;

/**
 * Settings for {@link JiraTaskCreator}, created from a Java properties file.
 *
 */
public class TasksToJiraSettings {

	private static final String JIRA_EPIC_LINK_FIELD_NAME = "jiraEpicLinkFieldName";
	private static final String JIRA_EPIC_KEY = "jiraEpicKey";
	private static final String JIRA_ISSUE_TYPE = "jiraIssueType";
	private static final String JIRA_PROJECT = "jiraProject";
	private static final String JIRA_PASSWORD = "jiraPassword";
	private static final String JIRA_USER = "jiraUser";
	private static final String JIRA_URL = "jiraUrl";
	private static final String TEAMSCALE_API_KEY = "teamscaleApiKey";
	private static final String TEAMSCALE_USER = "teamscaleUser";
	private static final String TEAMSCALE_URL = "teamscaleUrl";
	private static final String TEAMSCALE_PROJECT = "teamscaleProject";
	private static final String TASK_LINK_TEXT = "taskLinkText";
	private static final String ADDITIONAL_JIRA_FIELD_PREFIX = "jiraField.";

	public final String teamscaleProject;
	public final String teamscaleUrl;
	public final String teamscaleUser;
	public final String teamscaleApiKey;
	public final String jiraUrl;
	public final String jiraUser;
	public final String jiraPassword;
	public final String jiraProject;
	public final Long jiraIssueType;
	public final String jiraEpicKey;
	public final String jiraEpicLinkFieldName;
	public final String taskLinkText;
	public final Map<String, Object> jiraAddtionalFields;

	public TasksToJiraSettings(File propertiesFile) throws IOException {
		Properties properties = FileSystemUtils.readPropertiesFile(propertiesFile);
		teamscaleProject = readValue(properties, TEAMSCALE_PROJECT);
		teamscaleUrl = StringUtils.stripSuffix(readValue(properties, TEAMSCALE_URL), "/");
		teamscaleApiKey = readValue(properties, TEAMSCALE_API_KEY);
		teamscaleUser = readValue(properties, TEAMSCALE_USER);
		jiraUrl = StringUtils.stripSuffix(readValue(properties, JIRA_URL), "/");
		jiraUser = readValue(properties, JIRA_USER);
		jiraProject = readValue(properties, JIRA_PROJECT);
		jiraEpicKey = readValue(properties, JIRA_EPIC_KEY);
		jiraEpicLinkFieldName = readValue(properties, JIRA_EPIC_LINK_FIELD_NAME);
		jiraIssueType = Long.parseLong(readValue(properties, JIRA_ISSUE_TYPE));
		jiraPassword = readPassword(properties, JIRA_PASSWORD);
		taskLinkText = readValue(properties, TASK_LINK_TEXT);
		jiraAddtionalFields = buildJiraAddtionalFieldsMap(properties);
	}

	private static Map<String, Object> buildJiraAddtionalFieldsMap(Properties properties) {
		Map<String, Object> additionalFields = new HashMap<>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			if (!key.startsWith(ADDITIONAL_JIRA_FIELD_PREFIX)) {
				continue;
			}
			additionalFields.put(StringUtils.stripPrefix(key, ADDITIONAL_JIRA_FIELD_PREFIX), entry.getValue());
		}
		return additionalFields;
	}

	/**
	 * Reads a value from the properties and trims spaces fromt the value.
	 */
	private String readValue(Properties properties, String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			return null;
		}

		return value.trim();
	}

	/**
	 * Reads a password fromthe properties, if it is specified there. If not, a
	 * console input is prompted to the user.
	 */
	private String readPassword(Properties properties, String key) {
		String password = readValue(properties, key);
		if (!StringUtils.isEmpty(password)) {
			return password;
		}

		Console console = System.console();
		if (console == null) {
			System.out.println("Couldn't get Console instance to enter " + key);
			System.exit(0);
		}

		char[] passwordArray = console.readPassword("Enter " + key + ": ");
		return new String(passwordArray);
	}
}
