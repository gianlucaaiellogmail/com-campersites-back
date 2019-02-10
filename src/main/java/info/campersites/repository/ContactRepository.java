package info.campersites.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.ContactEntity;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

}
