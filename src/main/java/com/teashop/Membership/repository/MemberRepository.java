package com.teashop.Membership.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teashop.Membership.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByPhone(String phone);
}
