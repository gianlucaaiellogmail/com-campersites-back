package info.campersites.bo;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserReg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String nickname;

	@NotEmpty(message = Errors.REQUIRED)
	@Email(message = Errors.EMAIL) 
	private String email;
	
	@NotEmpty(message = Errors.REQUIRED)
	@Email(message = Errors.EMAIL) 
	private String confirmEmail;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String password;

	@NotEmpty(message = Errors.REQUIRED)
	private String confirmPassword;

	@AssertTrue(message = Errors.NOT_MATCH)
	private boolean isEmailValid() {
		return this.email.equals(this.confirmEmail);
	}
	
	@AssertTrue(message = Errors.NOT_MATCH)
	private boolean isPasswordValid() {
		return this.password.equals(this.confirmPassword);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
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
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
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
		UserReg other = (UserReg) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserReg [nickname=" + nickname + ", email=" + email + ", confirmEmail=" + confirmEmail + ", password="
				+ password + ", confirmPassword=" + confirmPassword + "]";
	}
	
}
