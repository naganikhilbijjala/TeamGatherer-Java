package com.nikhil.TeamGathererBackend.repository;

import com.nikhil.TeamGathererBackend.model.Players;
import com.nikhil.TeamGathererBackend.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayersRepo extends JpaRepository<Players,Integer> {
    List<Players> findAll();
    List<Players> findByUserId(Integer userId);
}
