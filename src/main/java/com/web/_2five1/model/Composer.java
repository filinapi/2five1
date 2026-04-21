package com.web._2five1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; //perchè mi andava in loop
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="composer")
public class Composer {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany (mappedBy = "composer", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("composer") // /*
    private List <JazzStandard> standards = new ArrayList<>();
    //*/
    public Composer () {}
    public Composer (String name){
        this.name = name;
    }

    public Long getId(){ return id;}
    public void setId(Long id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public List <JazzStandard> getStandards(){return standards;}
    public void setStandards(List<JazzStandard> standards) { this.standards = standards; }
}