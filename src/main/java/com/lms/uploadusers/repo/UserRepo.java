package com.lms.uploadusers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.uploadusers.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	boolean existsByEmpId(int empId);

	boolean existsByEmail(String email);

	User findByEmail(String email);

	

}
