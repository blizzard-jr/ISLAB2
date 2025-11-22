package org.example.is_lab1.controllers;

import org.example.is_lab1.models.dto.MagicCityDTO;
import org.example.is_lab1.services.CityService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService service;

    public CityController(CityService service) {
        this.service = service;
    }

    @PostMapping("/interact")
    public ResponseEntity<MagicCityDTO> create(@RequestBody MagicCityDTO creature){
        return ResponseEntity.ok(service.create(creature));
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<MagicCityDTO> modify(@PathVariable int id, @RequestBody MagicCityDTO creature){
        return ResponseEntity.ok(service.modify(id, creature));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view")
    public ResponseEntity<Page<MagicCityDTO>> get(@RequestParam int page){
        return ResponseEntity.ok(service.get(page));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<MagicCityDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }
}
