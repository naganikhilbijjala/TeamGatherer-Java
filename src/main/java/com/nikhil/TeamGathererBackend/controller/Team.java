package com.nikhil.TeamGathererBackend.controller;

import com.nikhil.TeamGathererBackend.customException.BusinessException;
import com.nikhil.TeamGathererBackend.model.Teams;
import com.nikhil.TeamGathererBackend.repository.TeamsRepo;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Team {

    private static final Logger logger = LoggerFactory.getLogger(Team.class);
    @Autowired
    TeamsRepo repo;

    @PostMapping("teams")
    public Teams addTeam(@RequestBody Teams team){
        logger.info(String.valueOf(team));
        return repo.save(team);
    }
    //TODO: Dashobord GetTeams is not working
    @GetMapping("teams")
    public List<Teams> getTeams(){
        try{
            List<Teams> teams = repo.findAll();
            if(teams.isEmpty()){
                throw new BusinessException("604", "Hey list is completely empty, we have nothing to return");
            }
            return teams;
        }catch (Exception e){
            throw new BusinessException("605", "Something went wrong in service layer while fetching all employees "+e.getMessage());
        }
    }

    @DeleteMapping("teams/{teamId}")
    public ResponseEntity<Object> deleteTeam(@PathVariable("teamId") int teamId){
        try {
            repo.deleteById(teamId);
            if (repo.existsById(teamId)) {
                throw new EntityNotFoundException("Team with ID " + teamId + " not found");
            }
            HashMap<String,Object> response = new HashMap<>();
            response.put("message", "Team deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (EntityNotFoundException ex){
            HashMap<String, Object> response = new HashMap<>();
            response.put("error",ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            HashMap<String, Object> response = new HashMap<>();
            response.put("error","An error occured: "+e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
