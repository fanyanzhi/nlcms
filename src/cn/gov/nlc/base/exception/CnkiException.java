package cn.gov.nlc.base.exception;

/**
 * cnki的自定义异常，用于service层回滚
 * @author DAYI
 *
 */
public class CnkiException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CnkiException() {
		super();
	}

	public CnkiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CnkiException(String message, Throwable cause) {
		super(message, cause);
	}

	public CnkiException(String message) {
		super(message);
	}

	public CnkiException(Throwable cause) {
		super(cause);
	}
	
	

}
