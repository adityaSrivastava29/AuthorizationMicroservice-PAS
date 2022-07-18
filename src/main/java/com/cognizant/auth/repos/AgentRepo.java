package com.cognizant.auth.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognizant.auth.models.Agent;

public interface AgentRepo extends JpaRepository<Agent, Long> {
	
     Agent findByUsername(String username);

}
