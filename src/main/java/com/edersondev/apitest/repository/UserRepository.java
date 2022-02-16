package com.edersondev.apitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edersondev.apitest.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
