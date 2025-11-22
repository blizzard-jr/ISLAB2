package org.example.is_lab1.controllers;

import org.example.is_lab1.models.dto.RingDTO;
import org.example.is_lab1.services.RingService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ring")
public class RingController {
    private final RingService service;

    public RingController(RingService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<RingDTO> create(@RequestBody RingDTO ring){
        return ResponseEntity.ok(service.create(ring));
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<RingDTO> modify(@PathVariable int id, @RequestBody RingDTO ring){
        return ResponseEntity.ok(service.modify(id, ring));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/view")
    public ResponseEntity<Page<RingDTO>> get(@RequestParam int page){
        return ResponseEntity.ok(service.get(page));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<RingDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }
}
