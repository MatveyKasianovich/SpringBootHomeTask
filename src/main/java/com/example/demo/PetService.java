package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PetService {
    private final UserService userService;
    private final Map<Long, PetDto> petMap;
    private Long idCounter;

    public PetService(UserService userService) {
        this.petMap = new HashMap<>();
        this.idCounter = 1L;
        this.userService=userService;
    }

    public PetDto createPet(PetDto pet) {

        userService.getById(pet.getUserId());
        var newPet = new PetDto(
                idCounter++, pet.getName(), pet.getUserId()
        );
        petMap.put(newPet.getId(), newPet);
        userService.getById(newPet.getUserId()).getPets().add(newPet);
        return newPet;
    }

    public List<PetDto> allPets() {
        return petMap.values().stream().toList();
    }

    public PetDto getById(Long id) {
        if (!petMap.containsKey(id)) {
            throw new NoSuchElementException(String.format("not found pet by id={%d}", id));
        }
        return petMap.get(id);
    }

    public PetDto updatePet(PetDto pet, Long id) {
        if (!petMap.containsKey(id)) {
            throw new NoSuchElementException(String.format("not found pet by id={%d}", id));
        }

        PetDto updatedPet = new PetDto(id, pet.getName(), pet.getUserId());
        petMap.put(id, updatedPet);

        return updatedPet;
    }

    public void deletePet(Long id) {
        if (!petMap.containsKey(id)) {
            throw new NoSuchElementException(String.format("not found pet by id={%d}", id));
        }
        PetDto pet=petMap.get(id);
        userService.getById(pet.getUserId()).getPets().remove(pet);
        petMap.remove(id);
    }

    public void deletePetsByUserId(Long userId){
        List<Long> idToDelete=petMap.values().stream()
                .filter(pet->pet.getUserId()==userId)
                .map(PetDto::getId)
                .toList();
        idToDelete.forEach(petMap::remove);
    }
}