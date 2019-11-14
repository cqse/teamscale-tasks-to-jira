package eu.cqse.qcs.jiratasks.jiraclient;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Remote link in jira, e.g. used to link to Teamscale task page.
 */
public class RemoteLink {

	/** Jira JSON field name */
	private static final String TITLE = "title";

	/** Jira JSON field name */
	private static final String URL = "url";

	/** Jira JSON field name */
	private static final String OBJECT = "object";

	/** Jira JSON field name */
	private static final String ICON = "icon";

	/** Jira JSON field name */
	private static final String URL16X16 = "url16x16";

	/**
	 * Data must be wihtin data named 'object', hence we use a map to get the nested
	 * structure
	 */
	@JsonProperty(OBJECT)
	private Map<String, Object> object;

	public RemoteLink(String url, String title, String iconUrl) {
		object = new HashMap<>();
		object.put(URL, url);
		object.put(TITLE, title);

		Map<String, String> icon = new HashMap<>();
		icon.put(URL16X16, iconUrl);
		object.put(ICON, icon);

	}

	public Map<String, Object> getObject() {
		return object;
	}
}
