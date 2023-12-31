package com.nikhil.TeamGathererBackend.repository;

import com.nikhil.TeamGathererBackend.model.Teams;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamsRepo extends JpaRepository<Teams, Integer> {
    List<Teams> findAll();
    @Override
    void deleteById(Integer integer);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Teams t SET t.current = t.current + :num WHERE t.id = :team_id")
    void updateTeamsCurrentPlayers(@Param("team_id") Integer teamId, @Param("num") Integer count);



}
