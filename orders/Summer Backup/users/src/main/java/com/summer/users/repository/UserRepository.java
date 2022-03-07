package com.summer.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.summer.users.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
