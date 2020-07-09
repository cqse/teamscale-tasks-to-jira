package eu.cqse.qcs.jiratasks.jiraclient;

import eu.cqse.qcs.jiratasks.JiraTaskCreatorUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JiraTaskCreatorUtilsTest {

    @Test
    public void TestConvertMarkdownToJira() {

        String jiraText = JiraTaskCreatorUtils.convertMarkdownToJira("normal text *emphais* `code`\n" +
                "```\n" +
                "code area\n" +
                "```\n" +
                "open this [link](http://link)");

        assertThat(jiraText, is("normal text *emphais* {{code}}\n" +
                "{code}\n" +
                "code area\n" +
                "{code}\n" +
                "open this [link|http://link]"));
    }
}
