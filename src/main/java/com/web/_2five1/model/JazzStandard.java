package com.web._2five1.model;

import jakarta.persistence.*;

@Entity
@Table(name = "standards")
public class JazzStandard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String originalKey; // Es: "F", "Bb", "Eb"


    // Molti standard sono scritti da un solo autore
    @ManyToOne
    @JoinColumn(name = "composer_id") // Questa sarà la colonna (FK) nel database
    private Composer composer;
    private String sheetFileName;
    private String progression;
    // Costruttore vuoto obbligatorio
    public JazzStandard() {}

    // Costruttore comodo per popolare il DB
    public JazzStandard(String title, String originalKey, Composer composer, String sheetFileName, String progression) {
        this.title = title;
        this.originalKey = originalKey;
        this.composer = composer;
        this.sheetFileName = sheetFileName;
        this.progression = progression;
    }

    // --- GETTER E SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginalKey() { return originalKey; }
    public void setOriginalKey(String originalKey) { this.originalKey = originalKey; }

    public Composer getComposer() { return composer; }
    public void setComposer(Composer composer) { this.composer = composer; }

    public String getSheetFileName(){ return sheetFileName;}

    public void setSheetFileName(String sheetFileName) {
        this.sheetFileName = sheetFileName;
    }
    public String getProgression() { return progression; }
    public void setProgression(String progression) {
        this.progression = progression;
    }
}