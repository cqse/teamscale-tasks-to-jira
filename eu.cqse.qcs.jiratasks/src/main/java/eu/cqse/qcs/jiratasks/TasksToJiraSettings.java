package eu.cqse.qcs.jiratasks;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.conqat.lib.commons.filesystem.FileSystemUtils;
import org.conqat.lib.commons.string.StringUtils;

import eu.cqse.qcs.jiratasks.jiraclient.IdField;

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
	private static final String JIRA_TRUST_ALL_SSL_CERTS = "jiraTrustAllSslCerts";
	private static final String TEAMSCALE_API_KEY = "teamscaleApiKey";
	private static final String TEAMSCALE_USER = "teamscaleUser";
	private static final String TEAMSCALE_URL = "teamscaleUrl";
	private static final String TEAMSCALE_PROJECT = "teamscaleProject";
	private static final String TEAMSCALE_TRUST_ALL_SSL_CERTS = "teamscaleTrustAllSslCerts";
	private static final String TASK_LINK_TEXT = "taskLinkText";
	private static final String ISSUE_TITLE_PREFIX = "issueTitlePrefix";
	private static final String ADDITIONAL_JIRA_FIELD_PREFIX = "jiraField.";
	public static final Pattern JIRA_ID_FIELD_PATTERN = Pattern.compile("^id:(.*)");
	public static final Pattern JIRA_ARRAY_FIELD_PATTERN = Pattern.compile("^\\[(.*)\\]$");

	public final String teamscaleProject;
	public final String teamscaleUrl;
	public final boolean teamscaleTrustAllSslCerts;
	public final String teamscaleUser;
	public final String teamscaleApiKey;
	public final String jiraUrl;
	public final boolean jiraTrustAllSslCerts;
	public final String jiraUser;
	public final String jiraPassword;
	public final String jiraProject;
	public final Long jiraIssueType;
	public final String jiraEpicKey;
	public final String jiraEpicLinkFieldName;
	public final String taskLinkText;
	public final String issueTitlePrefix;
	public final Map<String, Object> jiraAddtionalFields;

	public TasksToJiraSettings(File propertiesFile) throws IOException {
		Properties properties = FileSystemUtils.readPropertiesFile(propertiesFile);
		teamscaleProject = readValue(properties, TEAMSCALE_PROJECT);
		teamscaleUrl = StringUtils.stripSuffix(readValue(properties, TEAMSCALE_URL), "/");
		teamscaleApiKey = readValue(properties, TEAMSCALE_API_KEY);
		teamscaleUser = readValue(properties, TEAMSCALE_USER);
		teamscaleTrustAllSslCerts = Boolean.parseBoolean(readValue(properties, TEAMSCALE_TRUST_ALL_SSL_CERTS));
		jiraUrl = StringUtils.stripSuffix(readValue(properties, JIRA_URL), "/");
		jiraUser = readValue(properties, JIRA_USER);
		jiraProject = readValue(properties, JIRA_PROJECT);
		jiraEpicKey = readValue(properties, JIRA_EPIC_KEY);
		jiraEpicLinkFieldName = readValue(properties, JIRA_EPIC_LINK_FIELD_NAME);
		jiraIssueType = Long.parseLong(readValue(properties, JIRA_ISSUE_TYPE));
		jiraPassword = readPassword(properties, JIRA_PASSWORD);
		taskLinkText = readValue(properties, TASK_LINK_TEXT);
		issueTitlePrefix = readValue(properties, ISSUE_TITLE_PREFIX);
		jiraAddtionalFields = buildJiraAdditionalFieldsMap(properties);
		jiraTrustAllSslCerts = Boolean.parseBoolean(readValue(properties, JIRA_TRUST_ALL_SSL_CERTS));
	}

	/**
	 * Adds additional Jira field properties, these are properties whre the name
	 * starts with {@link #ADDITIONAL_JIRA_FIELD_PREFIX}
	 */
	private static Map<String, Object> buildJiraAdditionalFieldsMap(Properties properties) {
		Map<String, Object> additionalFields = new HashMap<>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String key = (String) entry.getKey();
			if (!key.startsWith(ADDITIONAL_JIRA_FIELD_PREFIX)) {
				continue;
			}
			additionalFields.put(StringUtils.stripPrefix(key, ADDITIONAL_JIRA_FIELD_PREFIX),
					buildJiraValue(entry.getValue()));
		}
		return additionalFields;
	}

	/**
	 * Builds the JIRA representation of the given properties value. Normally, this
	 * is just the value string, expect: For values matching
	 * {@link #JIRA_ARRAY_FIELD_PATTERN} the value is parsed in to an array where
	 * fields are split at commas. Values matching {@link #JIRA_ID_FIELD_PATTERN}
	 * are interpreted as an id and put as an id object in Jira
	 */
	private static Object buildJiraValue(Object propertiesValue) {
		if (propertiesValue == null) {
			return null;
		}

		Matcher idMatcher = JIRA_ID_FIELD_PATTERN.matcher(propertiesValue.toString());
		if (idMatcher.matches()) {
			return new IdField(idMatcher.group(1));
		}

		Matcher arrayMatcher = JIRA_ARRAY_FIELD_PATTERN.matcher(propertiesValue.toString());
		if (arrayMatcher.matches()) {
			String[] values = arrayMatcher.group(1).split(",");
			Object[] objValues = new Object[values.length];
			for (int i = 0; i < values.length; i++) {
				objValues[i] = buildJiraValue(values[i]);
			}
			return objValues;
		}
		return propertiesValue;
	}

	/**
	 * Reads a value from the properties and trims spaces fromt the value.
	 */
	private static String readValue(Properties properties, String key) {
		String value = properties.getProperty(key);
		if (value == null) {
			return null;
		}

		return value.trim();
	}

	/**
	 * Reads a password from the properties, if it is specified there. If not, a
	 * console input is prompted to the user.
	 */
	private static String readPassword(Properties properties, String key) {
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
