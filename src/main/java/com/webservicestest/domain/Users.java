package com.webservicestest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Users  implements Serializable{

    public static final long serialVersionUID =1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    @JsonIgnore
    private long user_id;

    @Column(name = "username", unique = true)
    private String username;

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    protected Users() {
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public long getUser_id() {return user_id;}

    @Override
    public String toString() {
        return "Users{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
