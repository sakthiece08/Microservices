package com.teqmonic.microservices.mortgagerateservice.enitity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "mortgage_profile")
@Getter
@ToString
public class MortgageProfileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "amortization_period")
	private int amortizationPeriod;

	@Column(name = "profile_rating")
	private String profileRating;

	@Column(name = "mortgage_type")
	private String mortgageType;

	@ToString.Exclude
	@OneToOne(mappedBy = "mortgageProfileEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MortgageRateEntity MortgageRateEntity;

}
