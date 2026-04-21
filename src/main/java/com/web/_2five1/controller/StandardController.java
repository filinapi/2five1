package com.web._2five1.controller;

import com.web._2five1.model.JazzStandard;
import com.web._2five1.service.StandardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RequestMapping("/api/standards")
@RestController
public class StandardController {
    private final StandardService standardService;
    StandardController(StandardService standardService){
        this.standardService = standardService;
    }
    @GetMapping("")
    public ResponseEntity<List<JazzStandard>> standard (@RequestParam(required = false) String composer,
                                                        @RequestParam(required = false) String title){
     if (composer != null && title != null){
         return ResponseEntity.ok(standardService.searchByComposerAndTitle(composer,title));
     }
     if (composer != null){
         return ResponseEntity.ok(standardService.searchByComposer(composer));
     }
     if(title != null){
         return ResponseEntity.ok(standardService.searchByTitle(title));
     }
     return ResponseEntity.ok(standardService.getAllStandards());
    }
    // Ricerca per ID (Copiato dal suo @GetMapping("/{id}"))
    @GetMapping("/{id}")
    public ResponseEntity<JazzStandard> standards(@PathVariable Long id) {
        Optional<JazzStandard> s = standardService.getStandardById(id);
        if (s.isPresent()) {
            return ResponseEntity.ok(s.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("")
    public ResponseEntity<JazzStandard> createStandard(@RequestBody JazzStandard standard) {
        return ResponseEntity.ok(standardService.saveStandard(standard));
    }
}
