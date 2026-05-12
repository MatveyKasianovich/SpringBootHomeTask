package com.example.demo;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    public static final Logger log = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto pet){
        log.info("called method to create pet");
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(pet));
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> allPets(){
        log.info("called method to return all pets");
        return ResponseEntity.status(HttpStatus.OK).body(petService.allPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getById(@PathVariable Long id){
        log.info("called method to return petById");
        return ResponseEntity.status(HttpStatus.OK).body(petService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@RequestBody @Valid PetDto pet, @PathVariable Long id){
        log.info("called method to updatePet");
        return ResponseEntity.ok().body(petService.updatePet(pet, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id){
        log.info("called method to deletePet");
        petService.deletePet(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}