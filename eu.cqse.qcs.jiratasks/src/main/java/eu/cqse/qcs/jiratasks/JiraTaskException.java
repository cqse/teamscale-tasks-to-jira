package eu.cqse.qcs.jiratasks;

/**
 * Exceptions of {@link JiraTaskCreator} 
 */
public class JiraTaskException extends Exception {

	private static final long serialVersionUID = 1L;

	public JiraTaskException(String message, Exception e) {
		super(message, e);
	}

}
