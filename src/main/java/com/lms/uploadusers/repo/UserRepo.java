package com.lms.uploadusers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.uploadusers.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

}
