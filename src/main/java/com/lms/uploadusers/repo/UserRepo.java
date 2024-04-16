package com.lms.uploadusers.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.enumerate.Roles;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmployeeId(Long employeeId);

	boolean existsByEmail(String email);

	User findByEmail(String email);
	
	User findByEmployeeId(Long employeeId);

	List<User> findByRole(Roles role);
	
	List<User> findByBusinessUnit(String businessUnit);

}
