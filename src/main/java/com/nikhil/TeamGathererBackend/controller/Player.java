package com.nikhil.TeamGathererBackend.controller;

import com.nikhil.TeamGathererBackend.customException.BusinessException;
import com.nikhil.TeamGathererBackend.model.Players;
import com.nikhil.TeamGathererBackend.repository.PlayersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Player {
    @Autowired
    PlayersRepo repo;

    @GetMapping("/players")
    List<Players> getPlayers(){
        try{
            List<Players> players = repo.findAll();
            if(players.isEmpty()){
                throw new BusinessException("604", "Hey list is completely empty, we have nothing to return");
            }
            return players;
        }catch (Exception e){
            throw new BusinessException("605", "Something went wrong in service layer while fetching all employees "+e.getMessage());
        }

    }

    @GetMapping("/getTeamsByUser")
    public ResponseEntity<Map<String,Object>> getTeamsByUser(@RequestParam int user_id){
        List<Players> players = repo.findByUserId(user_id);
        List<Integer> teamIds = new ArrayList<>();
        for(Players player : players){
            teamIds.add(player.getTeamId());
        }
        HashMap<String,Object> response = new HashMap<>();
        if(teamIds.isEmpty()){
            response.put("teams", null);
        }else{
            response.put("teams", teamIds);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/players")
    Players addPlayer(@RequestBody Players player){
        return repo.save(player);
    }



}
