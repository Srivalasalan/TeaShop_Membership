package com.teashop.Membership.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teashop.Membership.dao.AdminDao;
import com.teashop.Membership.dao.MemberDao;
import com.teashop.Membership.dao.SubPlanDao;
import com.teashop.Membership.dao.SubscriptionDao;
import com.teashop.Membership.dto.request.AdminRequestDTO;
import com.teashop.Membership.dto.request.MemberRequestDTO;
import com.teashop.Membership.dto.request.SubPlanRequestDTO;
import com.teashop.Membership.dto.response.AdminResponseDTO;
import com.teashop.Membership.dto.response.MemberResponseDTO;
import com.teashop.Membership.dto.response.SubPlanResponseDTO;
import com.teashop.Membership.dto.response.SubscriptionResponseDTO;
import com.teashop.Membership.entity.Admin;
import com.teashop.Membership.entity.Member;
import com.teashop.Membership.entity.Subscription;
import com.teashop.Membership.entity.SubscriptionPlan;
import com.teashop.Membership.exception.MemberNotFoundException;
import com.teashop.Membership.util.ResponseStructure;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminDao aDao;
	private final MemberDao mDao;
	private final SubscriptionDao sDao;
	private final SubPlanDao planDao;
	private final BCryptPasswordEncoder passwordEncoder;

	public ResponseEntity<ResponseStructure<AdminResponseDTO>> addAdmin(AdminRequestDTO dto) {
		Admin admin = new Admin();
		admin.setUserName(dto.getUserName());
		admin.setPassword(passwordEncoder.encode(dto.getPassword()));

		Admin saved = aDao.addAdmin(admin);

		AdminResponseDTO response = new AdminResponseDTO();
		response.setId(saved.getId());
		response.setUserName(saved.getUserName());

		ResponseStructure<AdminResponseDTO> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Admin Added Successfully");
		rs.setData(response);
		return ResponseEntity.status(HttpStatus.CREATED).body(rs);
	}
	
	public ResponseEntity<ResponseStructure<AdminResponseDTO>> login(String username, String password) {

	    Admin admin = aDao.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("Invalid username or password!"));

	    if (!passwordEncoder.matches(password, admin.getPassword())) {
	        throw new RuntimeException("Invalid username or password!");
	    }

	    AdminResponseDTO response = new AdminResponseDTO();
	    response.setId(admin.getId());
	    response.setUserName(admin.getUserName());

	    ResponseStructure<AdminResponseDTO> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("Login Successful!");
	    rs.setData(response);
	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<List<AdminResponseDTO>>> findAllAdmin(int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<Admin> res = aDao.findAllAdmin(pageable);

		List<AdminResponseDTO> al = res.getContent().stream().map(admin -> {
			AdminResponseDTO dto = new AdminResponseDTO();
			dto.setId(admin.getId());
			dto.setUserName(admin.getUserName());
			return dto;
		}).toList();

		ResponseStructure<List<AdminResponseDTO>> rs = new ResponseStructure<>();
		if (!al.isEmpty()) {
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Page: " + (page + 1) + " of " + res.getTotalPages() + " | Total Admin Count: "
					+ res.getTotalElements());
			rs.setData(al);
			return ResponseEntity.status(HttpStatus.OK).body(rs);
		} else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("No Admin Found!");
			rs.setData(al);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
		}
	}

	public ResponseEntity<ResponseStructure<MemberResponseDTO>> addMember(MemberRequestDTO dto) {
	    Member member = new Member();
	    member.setName(dto.getName());
	    member.setPhone(dto.getPhone());
	    member.setPin(passwordEncoder.encode(dto.getPin()));

	    Member saved = mDao.addMember(member);

	    MemberResponseDTO response = new MemberResponseDTO();
	    response.setId(saved.getId());
	    response.setName(saved.getName());
	    response.setPhone(saved.getPhone());

	    ResponseStructure<MemberResponseDTO> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.CREATED.value());
	    rs.setMessage("Member Added Successfully");
	    rs.setData(response);
	    return ResponseEntity.status(HttpStatus.CREATED).body(rs);
	}

	public ResponseEntity<ResponseStructure<List<MemberResponseDTO>>> findAllMembers(int page, int size, String sortBy) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<Member> pageResult = mDao.findAllMembers(pageable);

	    List<MemberResponseDTO> ml = pageResult.getContent()
	            .stream()
	            .map(member -> {
	                MemberResponseDTO dto = new MemberResponseDTO();
	                dto.setId(member.getId());
	                dto.setName(member.getName());
	                dto.setPhone(member.getPhone());
	                return dto;
	            }).toList();

	    ResponseStructure<List<MemberResponseDTO>> rs = new ResponseStructure<>();
	    if (!ml.isEmpty()) {
	        rs.setStatusCode(HttpStatus.OK.value());
	        rs.setMessage("Page: " + (page + 1) + " of " + pageResult.getTotalPages() 
	            + " | Total Members Count: " + pageResult.getTotalElements());
	        rs.setData(ml);
	        return ResponseEntity.status(HttpStatus.OK).body(rs);
	    } else {
	        rs.setStatusCode(HttpStatus.NOT_FOUND.value());
	        rs.setMessage("No Members Found!");
	        rs.setData(ml);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
	    }
	}

	public ResponseEntity<ResponseStructure<String>> deleteMember(String phone) {
		boolean mem = mDao.deleteMember(phone);
		ResponseStructure<String> rs = new ResponseStructure<String>();
		if (mem) {
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Member Deleted Succesfully");
			rs.setData("Member Deleted With The Mobile Number: " + phone);
			return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
		} else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Member Not Found!");
			rs.setData(null);
			return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> assignSub(String phone, Long planId) {

	    Member mem = mDao.findByPhone(phone)
	            .orElseThrow(() -> new MemberNotFoundException("No Member Found With The Number: " + phone));

	    SubscriptionPlan plan = planDao.findById(planId)
	            .orElseThrow(() -> new RuntimeException("No Plan Found With The ID: " + planId));

	    Optional<Subscription> exists = sDao.findByMemberAndStatus(mem, "ACTIVE");
	    exists.ifPresent(sub -> {
	        sub.setStatus("EXPIRED");
	        sDao.saveSub(sub);
	    });

	    Subscription subscription = new Subscription();
	    subscription.setMember(mem);
	    subscription.setPlan(plan);
	    subscription.setEndDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")).plusDays(plan.getDurationDays()));
	    subscription.setTeasUsed(0);
	    subscription.setStatus("ACTIVE");

	    Subscription saved = sDao.saveSub(subscription);

	    SubscriptionResponseDTO response = new SubscriptionResponseDTO();
	    response.setId(saved.getId());
	    response.setPlanName(saved.getPlan().getPlanName());
	    response.setTotalTeas(saved.getPlan().getTotalTeas());
	    response.setTeasUsed(saved.getTeasUsed());
	    response.setRemainingTeas(saved.getPlan().getTotalTeas() - saved.getTeasUsed());
	    response.setAmountPaid(saved.getPlan().getAmount());
	    response.setStartDate(saved.getStartDate());
	    response.setEndDate(saved.getEndDate());
	    response.setStatus(saved.getStatus());

	    ResponseStructure<SubscriptionResponseDTO> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.CREATED.value());
	    rs.setMessage("Subscription Assigned Successfully!");
	    rs.setData(response);
	    return ResponseEntity.status(HttpStatus.CREATED).body(rs);
	}

	public ResponseEntity<ResponseStructure<SubPlanResponseDTO>> saveSubPlan(SubPlanRequestDTO subs) {
		SubscriptionPlan plan = new SubscriptionPlan();
		plan.setPlanName(subs.getPlanName());
		plan.setTotalTeas(subs.getTotalTeas());
		plan.setAmount(subs.getAmount());
		plan.setDurationDays(subs.getDurationDays());

		SubscriptionPlan saved = planDao.saveNewPlan(plan);

		SubPlanResponseDTO response = new SubPlanResponseDTO();
		ResponseStructure<SubPlanResponseDTO> rs = new ResponseStructure<SubPlanResponseDTO>();

		response.setId(saved.getId());
		response.setPlanName(saved.getPlanName());
		response.setTotalTeas(saved.getTotalTeas());
		response.setAmount(saved.getAmount());
		response.setDurationDays(saved.getDurationDays());

		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Plan Added");
		rs.setData(response);
		return ResponseEntity.status(HttpStatus.CREATED).body(rs);

	}

	public ResponseEntity<ResponseStructure<SubPlanResponseDTO>> updatePlan(Long id, SubPlanRequestDTO subs) {
		Optional<SubscriptionPlan> res = planDao.findById(id);
		SubPlanResponseDTO response = new SubPlanResponseDTO();

		ResponseStructure<SubPlanResponseDTO> rs = new ResponseStructure<SubPlanResponseDTO>();
		if (res.isPresent()) {
			SubscriptionPlan plan = res.get();
			plan.setPlanName(subs.getPlanName());
			plan.setTotalTeas(subs.getTotalTeas());
			plan.setAmount(subs.getAmount());
			plan.setDurationDays(subs.getDurationDays());

			SubscriptionPlan splan = planDao.saveNewPlan(plan);
			response.setPlanName(splan.getPlanName());
			response.setTotalTeas(splan.getTotalTeas());
			response.setAmount(splan.getAmount());
			response.setDurationDays(splan.getDurationDays());

			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Update Successful");
			rs.setData(response);
			return ResponseEntity.status(HttpStatus.OK).body(rs);
		} else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Something Went Wrong");
			rs.setData(response);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
		}
	}

	public ResponseEntity<ResponseStructure<List<SubPlanResponseDTO>>> findAllPlans(int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		Page<SubscriptionPlan> pages = planDao.fetchAllPlans(pageable);
		ResponseStructure<List<SubPlanResponseDTO>> rs = new ResponseStructure<List<SubPlanResponseDTO>>();
		List<SubPlanResponseDTO> spl = pages.getContent().stream().map(plan -> {
			SubPlanResponseDTO response = new SubPlanResponseDTO();
			response.setId(plan.getId());
			response.setPlanName(plan.getPlanName());
			response.setTotalTeas(plan.getTotalTeas());
			response.setAmount(plan.getAmount());
			response.setDurationDays(plan.getDurationDays());
			return response;

		}).toList();

		if (!spl.isEmpty()) {
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Page " + (page + 1) + " of " + pages.getTotalPages() + " | Total Plans Count: "
					+ pages.getTotalElements());
			rs.setData(spl);
			return ResponseEntity.status(HttpStatus.OK).body(rs);
		} else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("No Plans Available");
			rs.setData(spl);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
		}
	}

	public ResponseEntity<ResponseStructure<String>> deletePlan(Long id) {
		boolean res = planDao.deletePlan(id);
		ResponseStructure<String> rs = new ResponseStructure<String>();
		if (res) {
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Plan Deleted");
			rs.setData("Plan Deleted With the Id: " + id);
			return ResponseEntity.status(HttpStatus.OK).body(rs);
		} else {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Id Not Found!");
			rs.setData("No Plan Found With The ID: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
		}
	}

}
