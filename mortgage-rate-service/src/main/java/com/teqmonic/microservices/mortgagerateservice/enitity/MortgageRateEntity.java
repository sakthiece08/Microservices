package com.teqmonic.microservices.mortgagerateservice.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "mortgage_rate")
@Getter
public class MortgageRateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "mortgage_rate")
	private double mortgageRate;
	
	@OneToOne
	@JoinColumn(name = "mortgage_profile_id")
	private MortgageProfileEntity mortgageProfileEntity;
}
