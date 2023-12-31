package com.nikhil.TeamGathererBackend.repository;

import com.nikhil.TeamGathererBackend.model.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeamsRepo extends JpaRepository<Teams, Integer> {
    List<Teams> findAll();
    @Override
    void deleteById(Integer integer);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Teams t SET t.current = t.current + :count WHERE t.id = :teamId")
    void updateTeamsCurrentPlayers(Integer teamId, Integer count);



}
