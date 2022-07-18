package com.cognizant.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.cognizant.auth.dto.JWTToken;
import com.cognizant.auth.dto.ValidToken;
import com.cognizant.auth.models.Agent;
import com.cognizant.auth.repos.AgentRepo;
import com.cognizant.auth.utils.JwtUtil;

import io.jsonwebtoken.SignatureException;

@Service
public class AuthService {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtutil;

	@Autowired
	private AgentRepo agentRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JWTToken createToken(Agent agent) throws Exception {
		// TODO Auto-generated method stub

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				agent.getUsername(), agent.getPassword());

		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		UserDetails user = this.customUserDetailsService.loadUserByUsername(agent.getUsername());

		String tkn = this.jwtutil.generateToken(user);

		JWTToken token = new JWTToken();

		token.setToken(tkn);

		return token;

	}

	public ValidToken validateToken(String header) throws Exception {

		ValidToken valid = new ValidToken();

		try {

			String token = header.substring(7);

			UserDetails userDetails = this.customUserDetailsService
					.loadUserByUsername(this.jwtutil.extractUsername(token));
			if (this.jwtutil.validateToken(token, userDetails)) {

				valid.setValid(true);
				return valid;
			}

			valid.setValid(false);
			return valid;
		} catch (SignatureException exception) {

			valid.setValid(false);
			return valid;

		}

	}

	public String userNameExist(String username) {

		Agent agent = this.agentRepo.findByUsername(username);

		if (agent == null)
			return username + " does not exist. Make sure you type the correct username.";

		return "";
	}

	public String getUsername(String header) {
		 
		String token = header.substring(7);
		  String username = this.jwtutil.extractUsername(token);
		  return username;
	}

	public Agent getAgent(String header) {
		// TODO Auto-generated method stub
		String token = header.substring(7);
		  String username = this.jwtutil.extractUsername(token);
		  Agent agent = this.agentRepo.findByUsername(username);
		return agent;
	}

}
