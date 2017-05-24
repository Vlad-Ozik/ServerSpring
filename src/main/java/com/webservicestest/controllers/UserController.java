package com.webservicestest.controllers;

import com.webservicestest.domain.Users;
import com.webservicestest.service.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UsersRepositoryImpl usersRepository;

    @Autowired
    public UserController(UsersRepositoryImpl usersRepository){this.usersRepository = usersRepository;}

    @RequestMapping(value = "/allUsers")
    public List<Users> getAllUsers() {return usersRepository.findAll();}

    @RequestMapping(value = "user/{id}", method = RequestMethod.GET)
    public Users getUserbyId(@PathVariable Long id){
        return usersRepository.findOne(id);
    }

    @RequestMapping(value = "findUser/{username}&{password}")
    public Users getUserByUsernameAndPassword(@PathVariable String username,@PathVariable String password){
        return usersRepository.findUserByUsernameAndPassword(username,password);
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public ResponseEntity<String> setNewUser(@RequestBody Users users){
        if(users != null){
            usersRepository.save(users);
            return new ResponseEntity(HttpStatus.CREATED);
        }else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/usersSearch/{search}", method = RequestMethod.GET)
    public List<String> getUsers(@PathVariable String search){
        List<String> list = new ArrayList<>();
        List<Users> usersList = usersRepository.findAll();
        for(int i=0;i<usersList.size();i++){
            list.add(usersList.get(i).getUsername());
        }
        if(search.equals("all")){
            return list;
        }else {
            List<String> searchList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                if(levenshteinDistanse(search,list.get(i)) == (list.get(i).length()- search.length()))
                    searchList.add(list.get(i));
            }
            return searchList;
        }
    }

    int levenshteinDistanse(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for(int i = 0; i <= n; i ++)
            D2[i] = i;

        for(int i = 1; i <= m; i ++) {
            D1 = D2;
            D2 = new int[n + 1];
            for(int j = 0; j <= n; j ++) {
                if(j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if(D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if(D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }
}
