package com.webservicestest.controllers;

import com.webservicestest.domain.Users;
import com.webservicestest.service.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UsersRepositoryImpl usersRepository;

    @Autowired
    public UserController(UsersRepositoryImpl usersRepository){this.usersRepository = usersRepository;}

    @RequestMapping(value = "/users")
    public List<Users> getAllUsers() {return usersRepository.findAll();}

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public Users getUserbyId(@PathVariable Long id){
        return usersRepository.findOne(id);
    }

    @RequestMapping(value = "findUser/{username}&{password}")
    public Users getUserByUsernameAndPassword(@PathVariable String username,@PathVariable String password){
        return usersRepository.findUserByUsernameAndPassword(username,password);
    }
}
