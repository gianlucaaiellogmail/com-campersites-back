package old.info.campersites.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.campersites.entity.ContactEntity;
import info.campersites.enumerator.EContactTypeId;
import info.campersites.repository.ContactRepository;
import old.info.campersites.service.OldContactService;

@Service
@Transactional(readOnly = true)
public class OldContactServiceImpl implements OldContactService {
	 
    @Autowired
	private ContactRepository contactRepository;
	
	@Override
    public void createContatto(String email, String nome, String message, Locale userLocale, EContactTypeId tipoContatto) {
    	ContactEntity contactEntity = new ContactEntity();
    	contactEntity.setEmail(email);
    	contactEntity.setNome(nome);
    	contactEntity.setMessaggio(message);
    	contactEntity.setLocale(userLocale.getLanguage());
    	contactEntity.setTipo(tipoContatto.getValue());
    	contactRepository.save(contactEntity);
    }

}
