package com.teqmonic.microservices.mortgagerateservice.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teqmonic.microservices.mortgagerateservice.enitity.MortgageProfileEntity;

@Repository
public interface MortgageProfileRespository extends JpaRepository<MortgageProfileEntity, Integer> {

	public List<MortgageProfileEntity> findByProfileRating(String profileRating);
}
