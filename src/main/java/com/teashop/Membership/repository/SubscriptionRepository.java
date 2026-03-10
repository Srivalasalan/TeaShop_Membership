package com.teashop.Membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teashop.Membership.entity.Member;
import com.teashop.Membership.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	Optional<Subscription> findByMemberAndStatus(Member member,String Status);
	List<Subscription> findByMemberOrderByStartDateDesc(Member member);

}
