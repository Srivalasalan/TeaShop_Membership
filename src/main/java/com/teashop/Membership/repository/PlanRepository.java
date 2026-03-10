package com.teashop.Membership.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teashop.Membership.entity.SubscriptionPlan;

public interface PlanRepository extends JpaRepository<SubscriptionPlan, Long> {
	

}
