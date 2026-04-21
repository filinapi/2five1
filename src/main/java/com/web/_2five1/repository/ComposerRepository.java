package com.web._2five1.repository;

import com.web._2five1.model.Composer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposerRepository extends JpaRepository<Composer, Long> {
    // Qui Spring "magicamente" aggiunge metodi come save(), findAll(), findById()
}
