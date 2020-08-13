package eu.cqse.qcs.jiratasks;

import org.junit.Test;

public class JiraTaskCreatorTest  extends TasksToJiraTestBase {

    @Test
    public void testCreateTaskInJira() throws Exception {
        TasksToJiraSettings settings = new TasksToJiraSettings(propertiesFile);
        JiraTaskCreator jiraTaskCreator = new JiraTaskCreator(settings);
        jiraTaskCreator.run(getTestTaskId());
    }
}
