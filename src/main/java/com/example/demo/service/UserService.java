package com.example.demo.service;

import com.example.demo.model.UserDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final PetService petService;
    private final Map<Long, UserDto> userMap;
    private Long idCounter;

    public UserService(@Lazy PetService petService) {
        this.userMap = new HashMap<>();
        this.idCounter = 1L;
        this.petService=petService;
    }

    public UserDto createUser( UserDto user) {
        var newUser=new UserDto(
                idCounter++,user.getName(),user.getEmail(),user.getAge()
        );
        userMap.put(newUser.getId(),newUser);
        return newUser;
    }

    public  List<UserDto> getAllUsers() {
        return userMap.values().stream().toList();
    }

    public  UserDto getById(Long id) {
        if(!userMap.containsKey(id)){
            throw new NoSuchElementException("not found user by id={"+id+"}");
        }
        return userMap.get(id);
    }


    public  UserDto updateUser( UserDto user,Long id) {
        if(!userMap.containsKey(id)){
            throw new NoSuchElementException("not found user by id={"+id+"}");
        }

        UserDto updatedUser = new UserDto(id,user.getName(), user.getEmail(),user.getAge());
        userMap.put(id,updatedUser);

        return updatedUser;

    }

    public void deleteUser(Long id) {
        if(!userMap.containsKey(id)){
            throw new NoSuchElementException("not found user by id=%s".formatted(id));
        }

        UserDto deletedUser=userMap.get(id);
        petService.deletePetsByUserId(deletedUser.getId());
        userMap.remove(id);
    }
}
