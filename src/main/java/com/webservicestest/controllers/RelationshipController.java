package com.webservicestest.controllers;

import com.webservicestest.domain.Relationship;
import com.webservicestest.service.RelationshipRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RelationshipController {

    private RelationshipRepositoryImpl relationshipRepository;

    @Autowired
    public RelationshipController (RelationshipRepositoryImpl relationshipRepository){
        this.relationshipRepository = relationshipRepository;
    }

    @RequestMapping(value = "/relationship/{userOne}", method = RequestMethod.GET)
    public List<Relationship> findRelationshipsById(@PathVariable String userOne){
        List<Relationship> rel = joinListst(
                relationshipRepository.findRelationshipsByUserOne(userOne),
                relationshipRepository.findRelationshipsByUserTwo(userOne)
        );

        List<Relationship> result = new ArrayList<>();
        for (int i = 0;i<rel.size();i++){
            if(rel.get(i).getStatus() == 1)
                result.add(rel.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/relRequest/{userTwo}", method = RequestMethod.GET)
    public List<Relationship> findRequestRel(@PathVariable String userTwo){
        List<Relationship> rel = relationshipRepository.findRelationshipsByUserTwo(userTwo);
        List<Relationship> result = new ArrayList<>();
        for(int i=0; i<rel.size();i++){
            if(rel.get(i).getStatus() == 0)
                result.add(rel.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/deleteFriend/{userOne}&{userTwo}",method = RequestMethod.DELETE)
    public void deleteFriendByName(@PathVariable String userOne,@PathVariable String userTwo){
        Relationship rel1 = relationshipRepository.findRelationshipByUserOneAndUserTwo(userOne, userTwo);
        Relationship rel2 = relationshipRepository.findRelationshipByUserOneAndUserTwo(userTwo, userOne);
        if(rel1 == null)
            relationshipRepository.delete(rel2);
        else
            relationshipRepository.delete(rel1);
    }

    @RequestMapping(value = "/acceptRequest/{userOne}&{userTwo}", method = RequestMethod.PUT)
    public void acceptRequest(@PathVariable String userOne, @PathVariable String userTwo){

            Relationship r = relationshipRepository.findRelationshipByUserOneAndUserTwo(userOne, userTwo);
            r.setStatus(0);
            relationshipRepository.save(r);
    }

    @RequestMapping(value = "/setRequest/{userOne}&{userTwo}", method = RequestMethod.POST)
    public void setRequestRelationship(@RequestBody Relationship relationship){
        relationshipRepository.save(relationship);
    }

    private static List<Relationship>joinListst(final List<Relationship> listA,
                                                final List<Relationship> listB){
        boolean aEmpty = (listA == null) || listA.isEmpty();
        boolean bEmpty = (listB == null) || listB.isEmpty();

        if(aEmpty & bEmpty){
            return new ArrayList<>();
        } else if(aEmpty){
            return new ArrayList<>(listB);
        } else if(bEmpty){
            return new ArrayList<>(listA);
        } else {
            ArrayList<Relationship> result = new ArrayList<>(listA.size() + listB.size());
            result.addAll(listA);
            result.addAll(listB);
            return result;
        }
    }
}
