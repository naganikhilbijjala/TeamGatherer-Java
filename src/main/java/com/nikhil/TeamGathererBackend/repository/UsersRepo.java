package com.nikhil.TeamGathererBackend.repository;

import com.nikhil.TeamGathererBackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, String> {
    Users findByEmail(String email);
}
