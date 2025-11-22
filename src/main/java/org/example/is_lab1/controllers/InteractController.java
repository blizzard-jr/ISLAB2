package org.example.is_lab1.controllers;

import org.example.is_lab1.models.dto.BookCreatureDTO;
import org.example.is_lab1.services.InteractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class InteractController {
    private final InteractService service;

    public InteractController(InteractService service) {
        this.service = service;
    }

    @PostMapping("/interact")
    public ResponseEntity<BookCreatureDTO> create(@RequestBody BookCreatureDTO creature){
        return ResponseEntity.ok(service.create(creature));
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity<BookCreatureDTO> modify(@PathVariable int id, @RequestBody BookCreatureDTO creature){
        return ResponseEntity.ok(service.modify(id, creature));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view")
    public ResponseEntity<Page<BookCreatureDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(required = false) String filter,
                                                        @RequestParam(required = false) String sort,
                                                        @RequestParam(required = false) String direction){
        Pageable pageable = PageRequest.of(page, 10);

        if (sort != null && !sort.isEmpty()) {
            Sort.Direction sortDirection =
                    "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            pageable = PageRequest.of(page, 10, Sort.by(sortDirection, sort));
        }
        return ResponseEntity.ok(service.get(pageable, filter));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<BookCreatureDTO> getById(@PathVariable int id){
        return ResponseEntity.ok(service.getById(id));
    }

}
