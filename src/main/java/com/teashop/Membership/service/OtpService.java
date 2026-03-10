package com.teashop.Membership.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.teashop.Membership.entity.Otp;
import com.teashop.Membership.repository.OtpRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {

	private final OtpRepository repo;

	public String generateAndSendOtp(String phone) {
		
		List<Otp> existingOtps = repo.findByPhoneAndIsUsedFalse(phone);
	    existingOtps.forEach(otp -> {
	        otp.setUsed(true);
	        repo.save(otp);
	    });

		String otp = String.valueOf((int) (Math.random() * 9000) + 1000);

		Otp obj = new Otp();
		obj.setPhone(phone);
		obj.setOtpCode(otp);
		obj.setUsed(false);
		
		repo.save(obj);

		System.err.println("=============================");
		System.out.println("OTP for " + phone + " : " + otp);
		System.err.println("=============================");
		
		return "OTP Sent";
	}
	
	public boolean validateOtp(String phone,String enteredOtp) {
		Optional<Otp> obj=repo.findByPhoneAndIsUsedFalseOrderByGeneratedAtDesc(phone);
		if(obj.isEmpty()) return false;
		
		Otp otp=obj.get();
		if(LocalDateTime.now().isAfter(otp.getGeneratedAt().plusMinutes(5))) return false;
		
		if(!otp.getOtpCode().equals(enteredOtp)) return false;
		
		otp.setUsed(true);
		repo.save(otp);
		return true;
	}

}
