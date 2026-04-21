package com.web._2five1.service;
import java.util.List;
import com.web._2five1.model.JazzStandard;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.web._2five1.model.*;
import com.web._2five1.repository.*;

import java.util.*;
@Service
public class StandardService {

    private final JazzStandardRepository standardRepository;
    private final ComposerRepository composerRepository;

    public StandardService (JazzStandardRepository standardRepository,ComposerRepository composerRepository){
        this.standardRepository = standardRepository;
        this.composerRepository = composerRepository;
    }
    //approccio della prof per i dati iniziali
    @PostConstruct
    public void init() {
        if (composerRepository.count() == 0) {
            Composer kosma = composerRepository.save(new Composer("Joseph Kosma"));
            Composer gershwin = composerRepository.save(new Composer("George Gershwin"));
            Composer davis = composerRepository.save(new Composer("Miles Davis"));

            standardRepository.save(new JazzStandard("Autumn Leaves", "Gm", kosma,"autumn.png"));
            standardRepository.save(new JazzStandard("Summertime", "Am", gershwin,"summertime.png"));
            standardRepository.save(new JazzStandard("So What", "Dm", davis, "so-what.png"));
        }
    }





    public JazzStandard saveStandard(JazzStandard standard) {
        return standardRepository.save(standard);
    }
    // Metodo per ottenere tutti i brani (come getBooks della prof)
    public List<JazzStandard> getAllStandards() {
        return standardRepository.findAll();
    }

    // Ricerca per titolo (come searchBookByTitle della prof)
    public List<JazzStandard> searchByTitle(String title) {
        // Nota: dovrai aggiungere questo metodo nel Repository tra poco
        return standardRepository.findByTitleContainingIgnoreCase(title);
    }

    // Metodo "Robusto" con Optional (molto gradito dai prof)
    public Optional<JazzStandard> getStandardById(Long id) {
        return standardRepository.findById(id);
    }

    public List<JazzStandard> searchByComposer(String composerName) {
        return standardRepository.searchByComposer(composerName);
    }

    public List<JazzStandard> searchByComposerAndTitle(String composer, String title) {
        return standardRepository.searchByBoth(title, composer);
    }
}


