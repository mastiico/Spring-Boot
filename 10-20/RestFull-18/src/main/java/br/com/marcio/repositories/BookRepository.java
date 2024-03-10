package br.com.marcio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marcio.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{}
