package com.webservicestest.soap;

import com.webservicestest.domain.Users;
import com.webservicestest.service.UsersRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://webservicestest.com/soap";

    private UsersRepositoryImpl usersRepository;

    @Autowired
    public UserEndpoint(UsersRepositoryImpl usersRepository){
        this.usersRepository = usersRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request){

        GetUserResponse response = new GetUserResponse();
        UserSoap us = new UserSoap();

        Users user = usersRepository.findUserByUsernameAndPassword(request.getUsername(),request.getPassword());
        if(user != null){
            us.setUsername(user.getUsername());
            us.setPassword(user.getPassword());
            us.setEmail(user.getEmail());
        }
        response.setUser(us);
        return response;
    }
}
