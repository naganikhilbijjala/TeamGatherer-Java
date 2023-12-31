package com.nikhil.TeamGathererBackend.controller;

import com.nikhil.TeamGathererBackend.customException.BusinessException;
import com.nikhil.TeamGathererBackend.model.Players;
import com.nikhil.TeamGathererBackend.repository.PlayersRepo;
import com.nikhil.TeamGathererBackend.repository.TeamsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Player {
    @Autowired
    PlayersRepo repo;

    @Autowired
    TeamsRepo teamsRepo;

    @GetMapping("/players")
    List<Players> getPlayers() {
        try {
            List<Players> players = repo.findAll();
            if (players.isEmpty()) {
                throw new BusinessException("604", "Hey list is completely empty, we have nothing to return");
            }
            return players;
        } catch (Exception e) {
            throw new BusinessException("605", "Something went wrong in service layer while fetching all employees " + e.getMessage());
        }

    }

    @GetMapping("/getTeamsByUser")
    public ResponseEntity<Map<String, Object>> getTeamsByUser(@RequestParam int user_id) {
        List<Players> players = repo.findByUserId(user_id);
        List<Integer> teamIds = new ArrayList<>();
        for (Players player : players) {
            teamIds.add(player.getTeamId());
        }
        HashMap<String, Object> response = new HashMap<>();
        if (teamIds.isEmpty()) {
            response.put("teams", null);
        } else {
            response.put("teams", teamIds);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/players")
    ResponseEntity<Object> addPlayer(@RequestBody Players player) {
        try{
            System.out.println(player);
            Players result = repo.save(player);
            teamsRepo.updateTeamsCurrentPlayers(player.getTeamId(), 1);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leave-game")
    ResponseEntity<Object> leaveGame(@RequestBody Players player){
        Optional<Players> currPlayerOpt = Optional.ofNullable(repo.findByTeamIdAndUserId(player.getTeamId(), player.getUserId()));
        HashMap<String, Object> response = new HashMap<>();
        try{
            if(currPlayerOpt.isPresent()) {
                repo.deleteById(currPlayerOpt.get().getId());
                System.out.println("Team id is: "+player.getTeamId());
                teamsRepo.updateTeamsCurrentPlayers(player.getTeamId(), -1);
                response.put("message", "Player left the team");
            }else{
                response.put("error", "Player not found in team");
            }
        }catch (Exception e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
