package com.web._2five1.controller;

import com.web._2five1.model.JazzStandard;
import com.web._2five1.service.StandardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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
    public List<JazzStandard> getStandards(
            @RequestParam(required = false) String composer,
            @RequestParam(required = false) String title) {
        return standardService.searchByComposerAndTitle(composer, title);
    }

    // 2. NUOVO: Endpoint per la "Scheda" del brano con trasposizione
    @GetMapping("/{id}/details")
    public ResponseEntity<Map<String, Object>> getDetails(
            @PathVariable Long id,
            @RequestParam(required = false) String targetKey) {

        return standardService.getStandardById(id).map(s -> {
            Map<String, Object> details = new HashMap<>();
            details.put("id", s.getId());
            details.put("title", s.getTitle());
            details.put("composer", s.getComposer().getName());
            details.put("originalKey", s.getOriginalKey());
            details.put("sheetFileName", s.getSheetFileName());

            // Gestione della progressione
            String resultProgression = s.getProgression();

            // Se l'utente specifica una targetKey, calcoliamo la trasposizione
            if (targetKey != null && !targetKey.isEmpty()) {
                resultProgression = standardService.transposeProgression(
                        s.getProgression(),
                        s.getOriginalKey(),
                        targetKey
                );
                details.put("currentKey", targetKey);
            } else {
                details.put("currentKey", s.getOriginalKey());
            }

            details.put("progression", resultProgression);

            return ResponseEntity.ok(details);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<JazzStandard> createStandard(@RequestBody JazzStandard standard) {
        return ResponseEntity.ok(standardService.saveStandard(standard));
    }
}
