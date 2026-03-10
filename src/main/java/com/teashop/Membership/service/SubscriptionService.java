package com.teashop.Membership.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.teashop.Membership.dao.MemberDao;
import com.teashop.Membership.dao.SubscriptionDao;
import com.teashop.Membership.dto.response.SubscriptionResponseDTO;
import com.teashop.Membership.entity.Member;
import com.teashop.Membership.entity.Subscription;
import com.teashop.Membership.exception.MemberNotFoundException;
import com.teashop.Membership.util.ResponseStructure;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionDao sDao;
    private final MemberDao mDao;

    public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> useTea(String phone) {

        // 1. Find member
        Member member = mDao.findByPhone(phone)
                .orElseThrow(() -> new MemberNotFoundException("Member not found!"));

        // 2. Find active subscription
        Subscription sub = sDao.findByMemberAndStatus(member, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active subscription found!"));

        // 3. Check expiry by date
        if (LocalDateTime.now(ZoneId.of("Asia/Kolkata")).isAfter(sub.getEndDate())) {
            sub.setStatus("EXPIRED");
            sDao.saveSub(sub);
            throw new RuntimeException("Subscription expired! Please renew.");
        }

        // 4. Check remaining teas
        int remaining = sub.getPlan().getTotalTeas() - sub.getTeasUsed();
        if (remaining <= 0) {
            sub.setStatus("EXPIRED");
            sDao.saveSub(sub);
            throw new RuntimeException("Quota exhausted! Please renew.");
        }

        // 5. Increment teasUsed
        sub.setTeasUsed(sub.getTeasUsed() + 1);

        // 6. Auto expire if all teas used
        if (sub.getPlan().getTotalTeas() - sub.getTeasUsed() == 0) {
            sub.setStatus("EXPIRED");
        }

        // 7. Save
        Subscription saved = sDao.saveSub(sub);

        // 8. Convert to ResponseDTO
        SubscriptionResponseDTO response = convertToDTO(saved);

        ResponseStructure<SubscriptionResponseDTO> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Tea marked as used! Remaining: " 
            + (saved.getPlan().getTotalTeas() - saved.getTeasUsed()));
        rs.setData(response);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    public ResponseEntity<ResponseStructure<SubscriptionResponseDTO>> getActiveSubscription(String phone) {

        // 1. Find member
        Member member = mDao.findByPhone(phone)
                .orElseThrow(() -> new MemberNotFoundException("Member not found!"));

        // 2. Find active subscription
        Subscription sub = sDao.findByMemberAndStatus(member, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("No active subscription found!"));

        // 3. Convert to ResponseDTO
        SubscriptionResponseDTO response = convertToDTO(sub);

        ResponseStructure<SubscriptionResponseDTO> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Active Subscription Fetched!");
        rs.setData(response);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    public ResponseEntity<ResponseStructure<List<SubscriptionResponseDTO>>> getHistory(String phone) {

        // 1. Find member
        Member member = mDao.findByPhone(phone)
                .orElseThrow(() -> new MemberNotFoundException("Member not found!"));

        // 2. Find all subscriptions
        List<SubscriptionResponseDTO> history = sDao.findByMemberOrderByStartDateDesc(member)
                .stream()
                .map(this::convertToDTO)
                .toList();

        ResponseStructure<List<SubscriptionResponseDTO>> rs = new ResponseStructure<>();
        if (!history.isEmpty()) {
            rs.setStatusCode(HttpStatus.OK.value());
            rs.setMessage("Subscription History Fetched!");
            rs.setData(history);
            return ResponseEntity.status(HttpStatus.OK).body(rs);
        } else {
            rs.setStatusCode(HttpStatus.NOT_FOUND.value());
            rs.setMessage("No Subscription History Found!");
            rs.setData(history);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
        }
    }

    private SubscriptionResponseDTO convertToDTO(Subscription sub) {
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setId(sub.getId());
        dto.setPlanName(sub.getPlan().getPlanName());
        dto.setTotalTeas(sub.getPlan().getTotalTeas());
        dto.setTeasUsed(sub.getTeasUsed());
        dto.setRemainingTeas(sub.getPlan().getTotalTeas() - sub.getTeasUsed());
        dto.setAmountPaid(sub.getPlan().getAmount());
        dto.setStartDate(sub.getStartDate());
        dto.setEndDate(sub.getEndDate());
        dto.setStatus(sub.getStatus());
        return dto;
    }
}