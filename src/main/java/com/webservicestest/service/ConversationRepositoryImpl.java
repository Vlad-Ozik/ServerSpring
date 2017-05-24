package com.webservicestest.service;

import com.webservicestest.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ConversationRepositoryImpl extends JpaRepository<Conversation,Integer> {

    List<Conversation> findMessagesByUserOneAndUserTwo(String userOne, String userTwo);
    List<Conversation> findConversationByUserOne(String userOne);
    List<Conversation> findMessagesByUserOneAndUserTwoAndStatus(String userOne, String userTwo, int status);
    List<Conversation> findConversationByUserTwo(String userTwo);
}
