package eu.cqse.qcs.jiratasks;

import java.io.Console;
import java.io.File;
import java.io.IOException;
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

	public TasksToJiraSettings(File propertiesFile) throws IOException {
		Properties properties = FileSystemUtils.readPropertiesFile(propertiesFile);
		teamscaleProject = readValue(properties, TEAMSCALE_PROJECT);
		teamscaleUrl = readValue(properties, TEAMSCALE_URL);
		teamscaleApiKey = readValue(properties, TEAMSCALE_API_KEY);
		teamscaleUser = readValue(properties, TEAMSCALE_USER);
		jiraUrl = readValue(properties, JIRA_URL);
		jiraUser = readValue(properties, JIRA_USER);
		jiraProject = readValue(properties, JIRA_PROJECT);
		jiraEpicKey = readValue(properties, JIRA_EPIC_KEY);
		jiraEpicLinkFieldName = readValue(properties, JIRA_EPIC_LINK_FIELD_NAME);
		jiraIssueType = Long.parseLong(readValue(properties, JIRA_ISSUE_TYPE));
		jiraPassword = readPassword(properties, JIRA_PASSWORD);
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
