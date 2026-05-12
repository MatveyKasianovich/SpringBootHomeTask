package com.example.demo;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUserSuccess() throws Exception {
        var user = new UserDto(
                null,
                "Mateusz",
                "kasyanovichmatvey@mail.com",
                19
        );

        String userJson = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertEquals(user.getName(), userResponse.getName());
        Assertions.assertEquals(user.getEmail(), userResponse.getEmail());
        Assertions.assertEquals(user.getAge(), userResponse.getAge());

    }

    @Test
    void getUserByIdSuccess() throws Exception {

        var user = new UserDto(
                null,
                "Mateusz",
                "kasyanovichmatvey@mail.com",
                19
        );

        user = userService.createUser(user);


        String foundUserJson = mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(foundUserJson, UserDto.class);


        Assertions.assertEquals(user.getId(), userResponse.getId());
        Assertions.assertEquals(user.getName(), userResponse.getName());
        Assertions.assertEquals(user.getEmail(), userResponse.getEmail());
        Assertions.assertEquals(user.getAge(), userResponse.getAge());
    }
}
