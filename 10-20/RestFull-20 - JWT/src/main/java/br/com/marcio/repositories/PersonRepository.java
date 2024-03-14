package br.com.marcio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcio.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{}
