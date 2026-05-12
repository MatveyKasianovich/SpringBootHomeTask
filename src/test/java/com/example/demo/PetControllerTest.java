package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPetSuccess() throws Exception {

        var user = new UserDto(
                null,
                "Test User",
                "test@mail.com",
                25
        );

        String userJson = objectMapper.writeValueAsString(user);
        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);


        var pet = new PetDto(
                null,
                "Buddy",
                userResponse.getId()
        );

        String petJson = objectMapper.writeValueAsString(pet);

        String createdPetJson = mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = objectMapper.readValue(createdPetJson, PetDto.class);

        Assertions.assertNotNull(petResponse.getId());
        Assertions.assertEquals(pet.getName(), petResponse.getName());
        Assertions.assertEquals(pet.getUserId(), petResponse.getUserId());
    }

    @Test
    void createPetFailsWhenOwnerNotFound() throws Exception {

        var pet = new PetDto(
                null,
                "Buddy",
                999L // несуществующий ID пользователя
        );

        String petJson = objectMapper.writeValueAsString(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPetByIdSuccess() throws Exception {

        var user = new UserDto(
                null,
                "Test User",
                "test@mail.com",
                25
        );

        String userJson = objectMapper.writeValueAsString(user);
        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);


        var pet = new PetDto(
                null,
                "Buddy",
                userResponse.getId()
        );

        String petJson = objectMapper.writeValueAsString(pet);
        String createdPetJson = mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = objectMapper.readValue(createdPetJson, PetDto.class);


        String foundPet = mockMvc.perform(get("/api/pets/{id}", petResponse.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petObject = objectMapper.readValue(foundPet, PetDto.class);

        Assertions.assertEquals(petResponse.getId(), petObject.getId());
        Assertions.assertEquals(petResponse.getName(), petObject.getName());
        Assertions.assertEquals(petResponse.getUserId(), petObject.getUserId());
    }

    @Test
    void deletePetByIdSuccess() throws Exception {

        var user = new UserDto(
                null,
                "Test User",
                "test@mail.com",
                25
        );

        String userJson = objectMapper.writeValueAsString(user);
        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);


        var pet = new PetDto(
                null,
                "Buddy",
                userResponse.getId()
        );

        String petJson = objectMapper.writeValueAsString(pet);
        String createdPetJson = mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = objectMapper.readValue(createdPetJson, PetDto.class);


        mockMvc.perform(delete("/api/pets/{id}", petResponse.getId()))
                .andExpect(status().isOk());

    }
}