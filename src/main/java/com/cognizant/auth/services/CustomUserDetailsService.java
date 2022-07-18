package com.cognizant.auth.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.auth.models.Agent;
import com.cognizant.auth.repos.AgentRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AgentRepo agentRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		Agent agent = this.agentRepo.findByUsername(username);

		if (agent == null) {
			throw new UsernameNotFoundException(username+ " does not exist. Make sure you type the correct username.");
		}

		User user = new User(agent.getUsername(), agent.getPassword(), new ArrayList<>());

		return user;
	}

}
