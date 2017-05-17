package com.webservicestest.controllers;

import com.webservicestest.domain.Relationship;
import com.webservicestest.service.RelationshipRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        List<Relationship> result = joinListst(
                relationshipRepository.findRelationshipsByUserOne(userOne),
                relationshipRepository.findRelationshipsByUserTwo(userOne)
        );
        return result;
    }

    public static List<Relationship>joinListst(final List<Relationship> listA,
                                                final List<Relationship> listB){
        boolean aEmpty = (listA == null) || listA.isEmpty();
        boolean bEmpty = (listB == null) || listB.isEmpty();

        if(aEmpty & bEmpty){
            return new ArrayList<Relationship>();
        } else if(aEmpty){
            return new ArrayList<Relationship>(listB);
        } else if(bEmpty){
            return new ArrayList<Relationship>(listA);
        } else {
            ArrayList<Relationship> result = new ArrayList<Relationship>(listA.size() + listB.size());
            result.addAll(listA);
            result.addAll(listB);
            return result;
        }
    }
}
