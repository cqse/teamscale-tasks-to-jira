package eu.cqse.qcs.jiratasks.jiraclient;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RemoteLinkTest {

	@Test
	public void test() throws JsonProcessingException {
		RemoteLink remoteLink = new RemoteLink("http://foo", "FOO", "https://demo.teamscale.com/favicon.ico");
	
		String result = new ObjectMapper().writeValueAsString(remoteLink);
		System.out.println(result);
		assertThat(result, is(
				"{\"fields\":{\"summary\":\"A sample ticket\",\"issuetype\":{\"id\":\"10006\"},\"project\":{\"id\":\"ABC\"},\"description\":\"The description of the example\",\"customfield_10001\":\"ABC-1\"}}"));
	}

}
