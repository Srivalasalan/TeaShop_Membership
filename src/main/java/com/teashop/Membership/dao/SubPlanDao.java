package com.teashop.Membership.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.teashop.Membership.entity.SubscriptionPlan;
import com.teashop.Membership.repository.PlanRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SubPlanDao {
	
	private final PlanRepository planRepo;
	
	public SubscriptionPlan saveNewPlan(SubscriptionPlan plan) {
		return planRepo.save(plan);
	}

	public Optional<SubscriptionPlan> findById(Long id) {
		Optional<SubscriptionPlan> res=planRepo.findById(id);
		
		return res;
	}

	public Page<SubscriptionPlan> fetchAllPlans(Pageable pageable) {
		
		return planRepo.findAll(pageable);
	}

	public boolean deletePlan(Long id) {
		
		if(planRepo.existsById(id)) {
			planRepo.deleteById(id);
			return true;
		}
		return false;
	}


}
