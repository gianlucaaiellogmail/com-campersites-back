package info.campersites.bo;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.NotEmpty;

public class NewPwd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String restoreCode;

	@NotEmpty(message = Errors.REQUIRED)
	private String password;

	@NotEmpty(message = Errors.REQUIRED)
	private String confirmPassword;

	@AssertTrue(message = Errors.NOT_MATCH)
	private boolean isPasswordValid() {
		return this.password.equals(this.confirmPassword);
	}

	public String getRestoreCode() {
		return restoreCode;
	}

	public void setRestoreCode(String restoreCode) {
		this.restoreCode = restoreCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((restoreCode == null) ? 0 : restoreCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewPwd other = (NewPwd) obj;
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (restoreCode == null) {
			if (other.restoreCode != null)
				return false;
		} else if (!restoreCode.equals(other.restoreCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NewPwd [restoreCode=" + restoreCode + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ "]";
	}

}
