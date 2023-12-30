package com.nikhil.TeamGathererBackend.controller;

import com.nikhil.TeamGathererBackend.customException.BusinessException;
import com.nikhil.TeamGathererBackend.customException.EmptyInputException;
import com.nikhil.TeamGathererBackend.model.Users;
import com.nikhil.TeamGathererBackend.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class User {
    @Autowired
    UsersRepo repo;

    @PostMapping("check")
    public ResponseEntity<Map<String, String>> checkUser(@RequestBody Users user){
        Users result = repo.findByEmail(user.getEmail());

        if(result != null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "User exists");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("message", "User does not exist");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam String email) {
        Users user = repo.findByEmail(email);
        if(user != null){
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("name", user.getName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Users user){

        if(user.getEmail().isEmpty()){
            throw new EmptyInputException("601", "Please send proper email, It blank");
        }
        if(repo.findByEmail(user.getEmail())!=null){
            Map<String, Object> response = new HashMap<>();
            response.put("error","User with the given email already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else{
            try{
                Users savedUser = repo.save(user);
                Map<String, Object> response = new HashMap<>();
                response.put("id",user.getId());
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }catch (IllegalArgumentException e){
            // If whole user object is null
                throw new BusinessException("602","given user is null "+e.getMessage());
            }catch (Exception e){
                throw new BusinessException("603", "Something went wrong in Service layer "+e.getMessage());
            }
        }
    }
}
