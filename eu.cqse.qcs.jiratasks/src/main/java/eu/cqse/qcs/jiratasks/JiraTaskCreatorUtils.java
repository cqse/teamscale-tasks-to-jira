package eu.cqse.qcs.jiratasks;

import org.conqat.lib.commons.string.StringUtils;

import eu.cqse.qcs.jiratasks.teamscaleclient.Task;

/**
 * Utility methods for {@link JiraTaskCreator}.
 */
public class JiraTaskCreatorUtils {

	/**
	 * Builds the Teamscale URL pointing to the page of the task
	 */
	public static String buildUrlForTask(TasksToJiraSettings settings, Task task) {
		return settings.teamscaleUrl + "/tasks.html#details/" + settings.teamscaleProject + "/?id=" + task.getId();
	}

	/**
	 * Some basic conversions of markdown (what is used in tasks) to the Jira wiki
	 * syntax.
	 */
	public static String convertMarkdownToJira(String markdown) {
		if (markdown == null) {
			return StringUtils.EMPTY_STRING;
		}
		return markdown.replaceAll("`(.*?)`", "{{$1}}").replaceAll("\\[(.*?)\\]\\((.*?)\\)", "[$1|$2]");
	}

}
