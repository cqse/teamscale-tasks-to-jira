package eu.cqse.qcs.jiratasks.teamscaleclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.conqat.lib.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A task is essentially a wrapper around a selection of findings and is
 * comparable to an issue/change request in a bug tracker. Many fields are only
 * used from JavaScript code, thus we suppress the unused warnings.
 */
@JsonIgnoreProperties({ "resolvedAuthor", "resolvedAssignee", "resolvedUpdatedBy", "openFindingsCount",
		"resolvedFindingsCount", "blacklistedFindingsCount", "comments" })
public class Task implements Serializable {

	/** Serial version UID. */
	private static final long serialVersionUID = 1;

	/** The name of the JSON property name for {@link #id}. */
	protected static final String ID_PROPERTY = "id";

	/** The name of the JSON property name for {@link #subject}. */
	protected static final String SUBJECT_PROPERTY = "subject";

	/** The name of the JSON property name for {@link #author}. */
	protected static final String AUTHOR_PROPERTY = "author";

	/** The name of the JSON property name for {@link #description}. */
	protected static final String DESCRIPTION_PROPERTY = "description";

	/** The name of the JSON property name for {@link #assignee}. */
	protected static final String ASSIGNEE_PROPERTY = "assignee";

	/** The name of the JSON property name for {@link #created}. */
	protected static final String CREATED_PROPERTY = "created";

	/** The name of the JSON property name for {@link #updated}. */
	protected static final String UPDATED_PROPERTY = "updated";

	/** The name of the JSON property name for {@link #status}. */
	protected static final String STATUS_PROPERTY = "status";

	/** The name of the JSON property name for {@link #resolution}. */
	protected static final String RESOLUTION_PROPERTY = "resolution";

	/** The name of the JSON property name for {@link #findings}. */
	protected static final String FINDINGS_PROPERTY = "findings";

	/** The name of the JSON property name for {@link #tags}. */
	protected static final String TAGS_PROPERTY = "tags";

	/** The name of the JSON property name for {@link #lastStatusUpdate}. */
	protected static final String LAST_STATUS_UPDATE_PROPERTY = "lastStatusUpdate";

	/** The name of the JSON property name for {@link #updatedBy}. */
	protected static final String UPDATED_BY_PROPERTY = "updatedBy";

	/** The unique id */
	@JsonProperty(ID_PROPERTY)
	private int id;

	/** The subject (typically a one-line summary) */
	@JsonProperty(SUBJECT_PROPERTY)
	private final String subject;

	/** The author (the user who created the issue) */
	@JsonProperty(AUTHOR_PROPERTY)
	private String author;

	/** The description (a textual description of the task) */
	@JsonProperty(DESCRIPTION_PROPERTY)
	private String description;

	/** The assignee (the Teamscale user defined as the assignee of the task) */
	@JsonProperty(ASSIGNEE_PROPERTY)
	private final String assignee;

	/** The timestamp when the task was created (milliseconds from 1970) */
	@JsonProperty(CREATED_PROPERTY)
	private long created;

	/** The timestamp when the task was last updated (milliseconds from 1970) */
	@JsonProperty(UPDATED_PROPERTY)
	private long updated;

	/** The timestamp when task status was last updated */
	@JsonProperty(LAST_STATUS_UPDATE_PROPERTY)
	private long lastStatusUpdate;

	/** The user who last updated the task */
	@JsonProperty(UPDATED_BY_PROPERTY)
	private String updatedBy;

	/** The status of this task */
	@JsonProperty(STATUS_PROPERTY)
	private String status;

	/** The resolution of this task */
	@JsonProperty(RESOLUTION_PROPERTY)
	private String resolution;

	/** The list of tags of this task. */
	@JsonProperty(TAGS_PROPERTY)
	private List<String> tags;

	/**
	 * The list of findings associated with this task.
	 */
	@JsonProperty(FINDINGS_PROPERTY)
	private ArrayList<Object> findings;

	@JsonCreator
	public Task(@JsonProperty(ID_PROPERTY) int id, @JsonProperty(SUBJECT_PROPERTY) String subject,
			@JsonProperty(AUTHOR_PROPERTY) String author, @JsonProperty(DESCRIPTION_PROPERTY) String description,
			@JsonProperty(ASSIGNEE_PROPERTY) String assignee, @JsonProperty(CREATED_PROPERTY) Long created,
			@JsonProperty(UPDATED_PROPERTY) Long updated, @JsonProperty(STATUS_PROPERTY) String status,
			@JsonProperty(RESOLUTION_PROPERTY) String resolution, @JsonProperty(TAGS_PROPERTY) Collection<String> tags,
			@JsonProperty(LAST_STATUS_UPDATE_PROPERTY) Long lastStatusUpdate) {

		this.id = id;
		this.subject = subject;
		this.author = author;
		this.description = description;
		this.assignee = assignee;
		if (lastStatusUpdate != null) {
			this.lastStatusUpdate = lastStatusUpdate;
		}
		if (created != null) {
			this.created = created;
		}
		if (updated != null) {
			this.updated = updated;
		}
		this.status = status;
		this.resolution = resolution;
		if (tags != null) {
			this.tags = new ArrayList<>(tags);
		} else {
			this.tags = new ArrayList<>();
		}
		this.findings = new ArrayList<>();
	}

	/** Returns the subject. */
	public String getSubject() {
		return subject;
	}

	/** Returns the description. */
	public String getDescription() {
		return description;
	}

	/** Sets the description */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Returns assignee. */
	public String getAssignee() {
		return assignee;
	}

	/** Returns status. */
	public String getStatus() {
		return status;
	}

	/** Returns resolution. */
	public String getResolution() {
		return resolution;
	}

	/** Sets resolution. */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	/** Sets created. */
	public void setCreated(long created) {
		this.created = created;
	}

	/** Returns created. */
	public long getCreated() {
		return created;
	}

	/** Returns updated. */
	public long getUpdated() {
		return updated;
	}

	/** Sets updated. */
	public void setUpdated(long updated) {
		this.updated = updated;
	}

	/** Gets last status update. */
	public long getLastStatusUpdate() {
		return lastStatusUpdate;
	}

	/** Sets last status update. */
	public void setLastStatusUpdate(long lastStatusUpdate) {
		this.lastStatusUpdate = lastStatusUpdate;
	}

	/** Sets author. */
	public void setAuthor(String author) {
		this.author = author;
	}

	/** Returns author. */
	public String getAuthor() {
		return author;
	}

	/** Sets updatedBy. */
	public void setUpdatedBy(String author) {
		this.updatedBy = author;
	}

	/** Returns updatedBy. */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/** Sets status. */
	public void setStatus(String status) {
		this.status = status;
	}

	/** Sets id. */
	public void setId(int id) {
		this.id = id;
	}

	/** Returns the id of this task. */
	public int getId() {
		return id;
	}

	/** Sets task tags. */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/** Returns the tags of this task. */
	public List<String> getTags() {
		if (tags == null) {
			return Collections.emptyList();
		}
		return CollectionUtils.asUnmodifiable(tags);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Task task = (Task) o;

		return id == task.id && created == task.created && updated == task.updated
				&& lastStatusUpdate == task.lastStatusUpdate && Objects.equals(subject, task.subject)
				&& Objects.equals(author, task.author) && Objects.equals(description, task.description)
				&& Objects.equals(assignee, task.assignee) && Objects.equals(updatedBy, task.updatedBy)
				&& status == task.status && resolution == task.resolution && Objects.equals(tags, task.tags);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, subject, author, description, assignee, created, updated, lastStatusUpdate, updatedBy,
				status, resolution, tags);
	}
}
