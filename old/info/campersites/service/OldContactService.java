package old.info.campersites.service;

import info.campersites.enumerator.EContactTypeId;

import java.util.Locale;

public interface OldContactService {
	 
    void createContatto(String email, String nome, String message, Locale userLocale, EContactTypeId tipoContatto);

}
