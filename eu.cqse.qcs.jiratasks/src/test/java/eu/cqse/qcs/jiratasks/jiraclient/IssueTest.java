package eu.cqse.qcs.jiratasks.jiraclient;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IssueTest {

	@Test
	public void test() throws JsonProcessingException {
		Issue issue = new Issue("ABC", "A sample ticket", "The description of the example", 10006);
		issue.setAdditionalField("customfield_10001", "ABC-1");

		String result = new ObjectMapper().writeValueAsString(issue);
		System.out.println(result);
		assertThat(result, is(
				"{\"fields\":{\"summary\":\"A sample ticket\",\"issuetype\":{\"id\":\"10006\"},\"project\":{\"key\":\"ABC\"},\"description\":\"The description of the example\",\"customfield_10001\":\"ABC-1\"}}"));
	}

}
