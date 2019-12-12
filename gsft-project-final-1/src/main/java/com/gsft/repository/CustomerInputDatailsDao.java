package com.gsft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;

public interface CustomerInputDatailsDao extends JpaRepository<CustomerInputDetails, Long> {
	
	
	CustomerInputSummary findBySummaryId(Long id);

}
