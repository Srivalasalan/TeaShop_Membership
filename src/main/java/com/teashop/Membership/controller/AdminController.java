package com.teashop.Membership.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teashop.Membership.dto.AdminLoginDTO;
import com.teashop.Membership.dto.request.AdminRequestDTO;
import com.teashop.Membership.dto.request.MemberRequestDTO;
import com.teashop.Membership.dto.request.SubPlanRequestDTO;
import com.teashop.Membership.dto.response.AdminResponseDTO;
import com.teashop.Membership.dto.response.MemberResponseDTO;
import com.teashop.Membership.dto.response.SubPlanResponseDTO;
import com.teashop.Membership.dto.response.SubscriptionResponseDTO;
import com.teashop.Membership.service.AdminService;
import com.teashop.Membership.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Tmembership")
public class AdminController {

	private final AdminService service;

	@PostMapping("/Admin")
	public ResponseEntity<ResponseStructure<AdminResponseDTO>> addAdmin(@Valid @RequestBody AdminRequestDTO admin) {
		return service.addAdmin(admin);

	}
	
	@PostMapping("/Admin/login")
	public ResponseEntity<ResponseStructure<AdminResponseDTO>> login(@RequestBody AdminLoginDTO dto) {
	    return service.login(dto.getUsername(), dto.getPassword());
	}

	@GetMapping("/Admin")
	public ResponseEntity<ResponseStructure<List<AdminResponseDTO>>> fetchAllAdmin(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy) {

		return service.findAllAdmin(page, size, sortBy);
	}

	@PostMapping("/Member")
	public ResponseEntity<ResponseStructure<MemberResponseDTO>> addMember(@Valid @RequestBody MemberRequestDTO member) {
		return service.addMember(member);

	}

	@GetMapping("/Member")
	public ResponseEntity<ResponseStructure<List<MemberResponseDTO>>> fetchAllMember(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy) {
		return service.findAllMembers(page, size, sortBy);
	}

	@DeleteMapping("/Member")
	public ResponseEntity<ResponseStructure<String>> deleteMemberByPhoneNumber(@RequestParam String phone) {
		return service.deleteMember(phone);
	}

	@PostMapping("/Subscription")
	public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> assignSub(@RequestParam String phone,
			@RequestParam Long planId) {
		return service.assignSub(phone, planId);
	}

	@PostMapping("/SubPlan")
	public ResponseEntity<ResponseStructure<SubPlanResponseDTO>> addNewSubscriptionsPlan(
			@Valid @RequestBody SubPlanRequestDTO subs) {
		return service.saveSubPlan(subs);
	}

	@PutMapping("/SubPlan/{id}")
	public ResponseEntity<ResponseStructure<SubPlanResponseDTO>> updatePlanById(
			@Valid @RequestBody SubPlanRequestDTO subs, @PathVariable Long id) {
		return service.updatePlan(id, subs);
	}

	@GetMapping("/SubPlan")
	public ResponseEntity<ResponseStructure<List<SubPlanResponseDTO>>> listAllPlans(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy) {

		return service.findAllPlans(page, size, sortBy);
	}

	@DeleteMapping("/SubPlan/{id}")
	public ResponseEntity<ResponseStructure<String>> deletePlan(@PathVariable Long id) {
		return service.deletePlan(id);
	}

}
