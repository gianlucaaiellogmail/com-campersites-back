package info.campersites.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.campersites.entity.ConfigurationEntity;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, String> {

}
