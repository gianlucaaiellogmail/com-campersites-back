package info.campersites.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.entity.ContactEntity;
import info.campersites.enumerator.EContactTypeId;
import info.campersites.repository.ContactRepository;

@Service
@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	ContactRepository contactRepository;

	@Override
	@Transactional(readOnly = false)
	public void create(String email, String nome, String messaggio, Locale locale, EContactTypeId contactType) {
    	ContactEntity contactEntity = new ContactEntity();
    	contactEntity.setEmail(email);
    	contactEntity.setNome(nome);
    	contactEntity.setMessaggio(messaggio);
    	contactEntity.setLocale(locale.getLanguage());
    	contactEntity.setTipo(contactType.getValue());
    	contactRepository.save(contactEntity);
	}

}
