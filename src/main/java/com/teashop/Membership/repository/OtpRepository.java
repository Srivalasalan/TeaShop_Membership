package com.teashop.Membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teashop.Membership.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

	Optional<Otp> findByPhoneAndIsUsedFalseOrderByGeneratedAtDesc(String phone);
	List<Otp> findByPhoneAndIsUsedFalse(String phone);
}
