package com.teashop.Membership.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.teashop.Membership.dao.MemberDao;
import com.teashop.Membership.dto.MemberLoginDTO;
import com.teashop.Membership.dto.response.MemberResponseDTO;
import com.teashop.Membership.entity.Member;
import com.teashop.Membership.exception.MemberNotFoundException;
import com.teashop.Membership.util.ResponseStructure;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDao mDao;
	private final BCryptPasswordEncoder passwordEncoder;
	private final OtpService otpService;

	public ResponseEntity<ResponseStructure<MemberResponseDTO>> login( MemberLoginDTO mem) {
		Member member =mDao.findByPhone(mem.getPhone())
				.orElseThrow(()->new MemberNotFoundException("Member Not Found!"));
		
		if(!passwordEncoder.matches(mem.getPin(), member.getPin())) {
			throw new RuntimeException("Invalid Pin!");
		}
		
		MemberResponseDTO response=new MemberResponseDTO();
		response.setId(member.getId());
		response.setName(member.getName());
		response.setPhone(member.getPhone());
		
		ResponseStructure<MemberResponseDTO> rs=new ResponseStructure<MemberResponseDTO>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Login Successful");
		rs.setData(response);
		return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<String>> requestOtp(String phone) {
		mDao.findByPhone(phone)
				.orElseThrow(()-> new MemberNotFoundException("Member Not Found!"));
		
		otpService.generateAndSendOtp(phone);
		
		ResponseStructure<String> rs = new ResponseStructure<>();
	    rs.setStatusCode(HttpStatus.OK.value());
	    rs.setMessage("OTP Sent Successfully!");
	    rs.setData("OTP sent to: " + phone);
	    return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<String>> changePin(String phone, String otpCode, String newPin) {
		Member member=mDao.findByPhone(phone).orElseThrow(()-> new MemberNotFoundException("Member Not Found!"));
		
		boolean res=otpService.validateOtp(phone, otpCode);
		if(!res) {
			throw new RuntimeException("Invalid or Expired OTP");
		}
		
		member.setPin(passwordEncoder.encode(newPin));
		mDao.addMember(member);
		
		ResponseStructure<String> rs=new ResponseStructure<String>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Pin Changed");
		rs.setData("Pin Changed for: "+phone);
		return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<MemberResponseDTO>> changeName(String phone, String newName) {
		Member member=mDao.findByPhone(phone).orElseThrow(()->new MemberNotFoundException("Member Not Found!"));
		member.setName(newName);
		Member saved=mDao.addMember(member);
		
		MemberResponseDTO response=new MemberResponseDTO();
		response.setId(saved.getId());
		response.setName(saved.getName());
		response.setPhone(saved.getPhone());
		
		ResponseStructure<MemberResponseDTO>rs=new ResponseStructure<MemberResponseDTO>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Name Updated Successfully");
		rs.setData(response);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(rs);
	}

	public ResponseEntity<ResponseStructure<MemberResponseDTO>> viewProfile(String phone) {
		Member member=mDao.findByPhone(phone).orElseThrow(()->new MemberNotFoundException("Member Not Found!"));
		
		MemberResponseDTO response=new MemberResponseDTO();
		response.setId(member.getId());
		response.setName(member.getName());
		response.setPhone(member.getPhone());
		
		ResponseStructure<MemberResponseDTO>rs=new ResponseStructure<MemberResponseDTO>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Member Profile ");
		rs.setData(response);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(rs);
	}
	
	

}
