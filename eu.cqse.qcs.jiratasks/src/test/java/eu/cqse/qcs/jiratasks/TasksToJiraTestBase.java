package eu.cqse.qcs.jiratasks;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.conqat.lib.commons.filesystem.FileSystemUtils;
import org.junit.BeforeClass;

import eu.cqse.qcs.jiratasks.teamscaleclient.TeamscaleClientTest;

public abstract class TasksToJiraTestBase {

	/**
	 * Name of properties file for test, this must be copied from
	 * /local-test-template.properties and parameters like user name, api key,
	 * project must be adjusted
	 */
	protected static final String PROPERTIES_FILE_NAME = "/local-tests.properties";

	/** Property for the id of a test task, must exist in the specified project */
	protected static final String TEST_TASK_ID = "testTaskId";

	protected static File propertiesFile;

	protected static Properties properties;

	@BeforeClass
	public static void baseInitialize() {
		try {
			URL propertiesURL = TeamscaleClientTest.class.getResource(PROPERTIES_FILE_NAME);
			if (propertiesURL == null) {
				throw new Exception(
						"ERROR: properties file " + PROPERTIES_FILE_NAME + " does not exit, probably you need "
								+ "to copy and adjust it from /local-tests-template.properties");
			}

			propertiesFile = new File(propertiesURL.getFile());
			properties = FileSystemUtils.readPropertiesFile(propertiesFile);
		} catch (Exception e) {
			System.err.println("Can't create Teamscale client, aborting tests");
			e.printStackTrace();
		}
	}

	protected static List<Integer> getTestTaskId() {
		return Collections.singletonList(Integer.parseInt(properties.getProperty(TEST_TASK_ID)));
	}
}
