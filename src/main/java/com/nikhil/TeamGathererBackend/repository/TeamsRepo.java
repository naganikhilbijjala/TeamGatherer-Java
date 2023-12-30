package com.nikhil.TeamGathererBackend.repository;

import com.nikhil.TeamGathererBackend.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamsRepo extends JpaRepository<Teams, Integer> {
    List<Teams> findAll();
    @Override
    void deleteById(Integer integer);

}
