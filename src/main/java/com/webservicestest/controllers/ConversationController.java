package com.webservicestest.controllers;


import com.webservicestest.domain.Conversation;
import com.webservicestest.service.ConversationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ConversationController {

    private ConversationRepositoryImpl conversationRepository;

    @Autowired
    public ConversationController(ConversationRepositoryImpl conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    @RequestMapping(value = "findConversation/{userOne}&{userTwo}")
    public List<Conversation> findMessagesForUserOneAndUserTwo(@PathVariable String userOne,
                                                               @PathVariable String userTwo){
        List<Conversation> result = joinListst(conversationRepository.findMessagesByUserOneAndUserTwo(userOne,userTwo),
                                                conversationRepository.findMessagesByUserOneAndUserTwo(userTwo,userOne));
        return result;
    }

    public static List<Conversation>joinListst(final List<Conversation> listA,
                                               final List<Conversation> listB){
        boolean aEmpty = (listA == null) || listA.isEmpty();
        boolean bEmpty = (listB == null) || listB.isEmpty();

        if(aEmpty & bEmpty){
            return new ArrayList<Conversation>();
        } else if(aEmpty){
            return new ArrayList<Conversation>(listB);
        } else if(bEmpty){
            return new ArrayList<Conversation>(listA);
        } else {
            ArrayList<Conversation> result = new ArrayList<Conversation>(listA.size() + listB.size());
            result.addAll(listA);
            result.addAll(listB);
            return result;
        }
    }
}
