package old.info.campersites.jsonresponse;

import java.util.ArrayList;
import java.util.List;

public class StringJsonResponse {

    private String esito;
    private List<String> errors = new ArrayList<String>();

    public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
