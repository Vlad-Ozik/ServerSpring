package com.webservicestest.service;

import com.webservicestest.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepositoryImpl extends JpaRepository<Users,Long> {

    //List<Users> findByUsernameAndPassword(String username,String password);
    Users findUserByUsernameAndPassword(String username, String password);

}

