package com.teashop.Membership.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teashop.Membership.entity.Member;
import com.teashop.Membership.entity.Subscription;
import com.teashop.Membership.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubscriptionDao {

    private final SubscriptionRepository subRepo;

    public Subscription saveSub(Subscription subscription) {
        return subRepo.save(subscription);
    }

    public Optional<Subscription> findByMemberAndStatus(Member member, String status) {
        return subRepo.findByMemberAndStatus(member, status);
    }

    public List<Subscription> findByMemberOrderByStartDateDesc(Member member) {
        return subRepo.findByMemberOrderByStartDateDesc(member);
    }
}

	
	
