package com.webservicestest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "relationship")
@JsonIgnoreProperties
public class Relationship implements Serializable {

    public static final long serialVersionUID =1;


    public void setStatus(int status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_one",unique = true)
    private String userOne;

    @Column(name = "status")
    private int status;

    @Column (name = "user_two", unique = true)
    private String userTwo;


    public Relationship(){

    }

    public Relationship(String userOne, String userTwo, int status) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.status = status;
    }

    public String getUserOne() {
        return userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public int getStatus() { return status;}


    @Override
    public String toString() {
        return "Relationship{" +
                ", user_one=" + getUserOne() +
                ", user_two=" + getUserTwo() +
                '}';
    }
}
