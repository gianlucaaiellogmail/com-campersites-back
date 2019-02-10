package info.campersites.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Errors implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String REQUIRED = "required";
	public static final String EMAIL = "email";
	public static final String PATTERN = "pattern";
	public static final String UNIQUE_EMAIL = "uniqueemail";
	public static final String UNIQUE_NICKNAME = "uniquenickname";
	public static final String NOT_MATCH = "same-as";
	public static final String MAXLENGTH = "maxlength";
	public static final String MINLENGTH = "minlength";
	public static final String URL = "url";
	
	private List<ErrorInfo> errorInfos = new ArrayList<ErrorInfo>();

	public List<ErrorInfo> getErrorInfos() {
		return errorInfos;
	}

	public void setErrorInfos(List<ErrorInfo> errorInfos) {
		this.errorInfos = errorInfos;
	}
	
    public void addErrorInfo(String code, String message) {
    	ErrorInfo errorInfo = new ErrorInfo(code, message);
    	errorInfos.add(errorInfo);
    }

}
