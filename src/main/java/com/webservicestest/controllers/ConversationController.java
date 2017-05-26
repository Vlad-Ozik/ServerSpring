package com.webservicestest.controllers;

import com.webservicestest.domain.Conversation;
import com.webservicestest.service.ConversationRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
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
        List<Conversation> result =  conversationRepository.findMessagesByUserOneAndUserTwoAndStatus(userTwo,userOne,0);
        Conversation conversation;
        for(int i=0;i<result.size();i++){
            conversation = result.get(i);
            conversation.setStatus(1);
            conversationRepository.save(conversation);
        }
        result.sort(Comparator.comparingInt(Conversation::getC_id));
        return result;
    }

    @RequestMapping(value = "findConversationForUserOne/{userOne}", method = RequestMethod.GET)
    public List<Conversation> findConversationForOneUser(@PathVariable String userOne){

        List<Conversation> result = joinListst(conversationRepository.findConversationByUserOne(userOne),
                                                conversationRepository.findConversationByUserTwo(userOne));
        result.sort(Comparator.comparingInt(Conversation::getC_id));
        return result;
    }

    @RequestMapping(value = "/setConversation", method = RequestMethod.POST)
    public ResponseEntity<String> setConversation(@RequestBody Conversation conversation){
        if(conversation != null){
            conversationRepository.save(conversation);
            return new ResponseEntity(HttpStatus.CREATED);
        }else return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/deleteAllMessages/{userOne}&{userTwo}", method = RequestMethod.DELETE)
    public void deleteAllMessages(@PathVariable String userOne, @PathVariable String userTwo){
        List<Conversation> result = joinListst(conversationRepository.findMessagesByUserOneAndUserTwo(userOne,userTwo),
                conversationRepository.findMessagesByUserOneAndUserTwo(userTwo,userOne));
        for(int i=0;i<result.size();i++){
            conversationRepository.delete(result.get(i).getC_id());
        }
    }

    @RequestMapping(value = "/getAllMessages", method = RequestMethod.GET)
    public List<Conversation> getAllMessages(){
        List<Conversation> result = conversationRepository.findAll();
        result.sort(Comparator.comparingInt(Conversation::getC_id));
        return result;
    }


    private static List<Conversation>joinListst(final List<Conversation> listA,
                                               final List<Conversation> listB){
        boolean aEmpty = (listA == null) || listA.isEmpty();
        boolean bEmpty = (listB == null) || listB.isEmpty();

        if(aEmpty & bEmpty){
            return new ArrayList<>();
        } else if(aEmpty){
            return new ArrayList<>(listB);
        } else if(bEmpty){
            return new ArrayList<>(listA);
        } else {
            ArrayList<Conversation> result = new ArrayList<>(listA.size() + listB.size());
            result.addAll(listA);
            result.addAll(listB);
            return result;
        }
    }
}
