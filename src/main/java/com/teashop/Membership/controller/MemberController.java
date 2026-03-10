package com.teashop.Membership.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teashop.Membership.dto.MemberLoginDTO;
import com.teashop.Membership.dto.response.MemberResponseDTO;
import com.teashop.Membership.service.MemberService;
import com.teashop.Membership.util.ResponseStructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Tmembership")
public class MemberController {
	
	private final MemberService service;
	
	@PostMapping("/Member/login")
	public ResponseEntity<ResponseStructure<MemberResponseDTO>> login(@Valid @RequestBody MemberLoginDTO mem){
		return service.login(mem);
	}
	
	@PostMapping("/Member/request-otp")
	public ResponseEntity<ResponseStructure<String>> requestOtp(@RequestParam String phone){
		return service.requestOtp(phone);
	}
	
	@PutMapping("/Member/change-pin")
	public ResponseEntity<ResponseStructure<String>> changePin(
			@RequestParam String phone,@RequestParam String otpCode,@RequestParam String newPin
			)
	{
		return service.changePin(phone,otpCode,newPin);
	}
	
	@PutMapping("/Member/update-name")
	public ResponseEntity<ResponseStructure<MemberResponseDTO>> changeName(@RequestParam String phone,@RequestParam String newName){
		return service.changeName(phone,newName);
	}
	
	@GetMapping("/Member/profile")
	public ResponseEntity<ResponseStructure<MemberResponseDTO>> viewProfile(@RequestParam String phone){
		return service.viewProfile(phone);
	}


}
