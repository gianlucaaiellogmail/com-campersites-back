package info.campersites.service;

import java.util.Locale;

import info.campersites.enumerator.EContactTypeId;

public interface ContactService {

	void create(String email, String nome, String messaggio, Locale locale, EContactTypeId contactType);

}
