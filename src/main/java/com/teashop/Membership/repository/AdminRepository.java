package com.teashop.Membership.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teashop.Membership.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUserName(String username);
}
