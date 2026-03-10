package com.teashop.Membership.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.teashop.Membership.entity.Member;
import com.teashop.Membership.repository.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDao {

	private final MemberRepository memberRepository;

	public Member addMember(@Valid Member member) {
		return memberRepository.save(member);
	}

	public Page<Member> findAllMembers(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}

	public Optional<Member> findByPhone(String phone) {
		return memberRepository.findByPhone(phone);
	}

	public boolean deleteMember(String phone) {
		Optional<Member> m = findByPhone(phone);
		if (m.isPresent()) {
			memberRepository.delete(m.get());
			return true;
		}
		return false;
	}
}
