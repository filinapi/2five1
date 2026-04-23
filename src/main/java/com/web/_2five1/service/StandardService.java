package com.web._2five1.service;
import java.util.List;
import com.web._2five1.model.JazzStandard;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.web._2five1.model.*;
import com.web._2five1.repository.*;

import java.util.*;
@Service
public class StandardService{

    private final JazzStandardRepository standardRepository;
    private final ComposerRepository composerRepository;

    private final List<String> cromatica = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");

    public StandardService (JazzStandardRepository standardRepository,ComposerRepository composerRepository){
        this.standardRepository = standardRepository;
        this.composerRepository = composerRepository;
    }
    //dati iniziali
    @PostConstruct
    public void init() {
        if (composerRepository.count() == 0) {
            Composer kosma = composerRepository.save(new Composer("Joseph Kosma"));
            Composer gershwin = composerRepository.save(new Composer("George Gershwin"));
            Composer davis = composerRepository.save(new Composer("Miles Davis"));

            standardRepository.save(new JazzStandard("Autumn Leaves", "Gm", kosma,"autumn.png", "Dm7 G7 CMaj7" ));
            standardRepository.save(new JazzStandard("Summertime", "Am", gershwin,"summertime.png", "E4 F7 Gdim"));
            standardRepository.save(new JazzStandard("So What", "Dm", davis, "so-what.png","A Bmaj4 Cb"));
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

    //LOGICA DI TRASPOSIZIONE
    public String transposeProgression(String progression, String oldKey, String newKey) {
        int oldKeyIdx = getNoteIndex(oldKey);
        int newKeyIdx = getNoteIndex(newKey);
        int diff = (newKeyIdx - oldKeyIdx + 12) % 12;

        String[] chords = progression.split(" ");
        StringBuilder result = new StringBuilder();

        for (String chord : chords) {
            result.append(transposeChord(chord, diff)).append(" ");
        }
        return result.toString().trim();
    }

    private int getNoteIndex(String key) {
        String note = key.toUpperCase().replace("M", "");
        // Gestione veloce dei bemolli per la ricerca nell'indice
        if (note.equals("EB")) note = "D#";
        if (note.equals("BB")) note = "A#";
        if (note.equals("AB")) note = "G#";
        if (note.equals("DB")) note = "C#";
        if (note.equals("GB")) note = "F#";

        return cromatica.indexOf(note);
    }

    private String transposeChord(String chord, int diff) {
        // Separa la nota (es. "F#") dal resto dell'accordo (es. "m7")
        String notePart;
        String suffix;
        if (chord.length() > 1 && (chord.charAt(1) == '#' || chord.charAt(1) == 'b')) {
            notePart = chord.substring(0, 2);
            suffix = chord.substring(2);
        } else {
            notePart = chord.substring(0, 1);
            suffix = chord.substring(1);
        }

        int oldIdx = getNoteIndex(notePart);
        int newIdx = (oldIdx + diff) % 12;
        return cromatica.get(newIdx) + suffix;
    }
}

// Altri metodi esistenti (getAll, searchByTitle, etc...)
