package com.teashop.Membership.dao;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.teashop.Membership.entity.Admin;
import com.teashop.Membership.repository.AdminRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminDao {

	private final AdminRepository adminRepository;

	public Admin addAdmin(@Valid Admin admin) {
		return adminRepository.save(admin);
	}
	
	public Optional<Admin> findByUsername(String username) {
	    return adminRepository.findByUserName(username);
	}

	public Page<Admin> findAllAdmin(Pageable pageable) {
		return adminRepository.findAll(pageable);
	}



}
