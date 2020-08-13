package eu.cqse.qcs.jiratasks.teamscaleclient;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.cqse.qcs.jiratasks.TasksToJiraSettings;
import eu.cqse.qcs.jiratasks.TasksToJiraTestBase;

public class TeamscaleClientTest extends TasksToJiraTestBase {

	private static TeamscaleClient client;

	@BeforeClass
	public static void initialize() throws Exception {
		client = new TeamscaleClient(new TasksToJiraSettings(propertiesFile));
	}

	@Test
	public void testRetrieveAndUpdateTask() throws Exception {
		Assume.assumeNotNull(client);
		List<Task> tasks = client.retrieveTasks(getTestTaskId());
		Assert.assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		task.setDescription(task.getDescription() + "  \nupdated at " + ZonedDateTime.now());
		client.updateTask(task);
	}

}
