package eu.cqse.qcs.jiratasks.teamscaleclient;

import eu.cqse.qcs.jiratasks.TasksToJiraSettings;
import org.conqat.lib.commons.filesystem.FileSystemUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class TeamscaleClientTest {

    /**
     * Name of properties file for test, this must be copied from /local-test-template.properties
     * and parameters like user name, api key, project must be adjusted
     */
    public static final String PROPERTIES_FILE_NAME = "/local-tests.properties";

    /** Property for the id of a test task, must exist in the specified project */
    public static final String TEST_TASK_ID = "testTaskId";

    private static TeamscaleClient client;
    private static Properties properties;

    @BeforeClass
    public static void initialize() throws Exception {
       try {
           URL propertiesURL = TeamscaleClientTest.class.getResource(PROPERTIES_FILE_NAME);
            if(propertiesURL == null) {
                throw new Exception("ERROR: properties file " + PROPERTIES_FILE_NAME + " does not exit, probably you need " +
                        "to copy and adjust it from /local-tests-template.properties");
            }

            File propertiesFile = new File(propertiesURL.getFile());
            properties = FileSystemUtils.readPropertiesFile(propertiesFile);
            client = new TeamscaleClient(new TasksToJiraSettings(propertiesFile));
       } catch (Exception e) {
            System.err.println("Can't create Teamscale client, aborting tests");
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrieveAndUpdateTask() throws Exception {
        Assume.assumeNotNull(client);
        List<Task> tasks = client.retrieveTasks(Collections.singletonList(Integer.parseInt(properties.getProperty(TEST_TASK_ID))));
        Assert.assertTrue(tasks.size() == 1);
        client.updateTask(tasks.get(0));
    }

}
