package com.webservicestest.domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conversation")
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int c_id;

    @Column(name = "user_one")
    private String userOne;

    @Column (name = "user_two")
    private String userTwo;

    @Column(name = "status")
    private int status;

    @Column(name = "message")
    private String message;

    public Conversation(){

    }

    public Conversation(String  userOne, String userTwo, int status, String message) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.status = status;
        this.message = message;
    }

    public int getC_id() {
        return c_id;
    }

    public String getUserOne() {
        return userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }

    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "c_id=" + getC_id() +
                ", user_one=" + getUserOne() +
                ", user_two=" + getUserTwo() +
                ", status=" + getStatus() +
                '}';
    }
}
