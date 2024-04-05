package com.lms.uploadusers.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.uploadusers.entity.Roles;


@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

	@Query("SELECT R FROM Roles R WHERE roleName=:role")
	public Roles findRolesByroleName(@Param("role") String role);
}
