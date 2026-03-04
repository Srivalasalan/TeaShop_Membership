package com.teashop.Membership.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@Column(nullable = false)
	private LocalDateTime starDate;
	@Column(nullable = false)
	private LocalDateTime enDate;
	@Column(nullable = false)
	private int totalTeas;
	@Column(nullable = false)
	private int teasUsed;
	@Column(nullable = false)
	private double amountPaid;
	@Column(nullable = false)
	private String status;

}
