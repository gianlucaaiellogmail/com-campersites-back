package info.campersites.bo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ContactBo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String nome;

	@NotEmpty(message = Errors.REQUIRED)
	@Email(message = Errors.EMAIL) 
	private String email;
	
	@NotEmpty(message = Errors.REQUIRED)
	private String messaggio;

	@NotEmpty(message = Errors.REQUIRED)
	private String realPerson;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public String getRealPerson() {
		return realPerson;
	}

	public void setRealPerson(String realPerson) {
		this.realPerson = realPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((messaggio == null) ? 0 : messaggio.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((realPerson == null) ? 0 : realPerson.hashCode());
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
		ContactBo other = (ContactBo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (messaggio == null) {
			if (other.messaggio != null)
				return false;
		} else if (!messaggio.equals(other.messaggio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (realPerson == null) {
			if (other.realPerson != null)
				return false;
		} else if (!realPerson.equals(other.realPerson))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ContactBo [nome=" + nome + ", email=" + email + ", messaggio=" + messaggio + ", realPerson="
				+ realPerson + "]";
	}

}
