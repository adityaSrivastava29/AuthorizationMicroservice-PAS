package com.cognizant.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.auth.dto.JWTToken;
import com.cognizant.auth.dto.ResponseMessage;
import com.cognizant.auth.dto.ValidToken;
import com.cognizant.auth.models.Agent;
import com.cognizant.auth.services.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthorizationController {

	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody Agent agent) throws Exception {
		log.info("before authenticate generateToken method");
		JWTToken token = this.authService.createToken(agent);
		log.info("Inside auth controller generateToken method");
		return ResponseEntity.ok(token);
	}

	@RequestMapping(value = "/validToken", method = RequestMethod.GET)
	public ResponseEntity<?> isTokenValid(@RequestHeader("auth") String header) throws Exception {
		log.info("start isTokenValid method");
		// System.out.println(header);
		ValidToken valid = this.authService.validateToken(header);
		log.debug("validToken", valid);
		log.info("end isTokenValid method");
		return ResponseEntity.ok(valid);

	}

	@RequestMapping(value = "/usernameExist/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> usernameExist(@PathVariable("username") String username) {
		log.info("start usernameExist method");
		String message = this.authService.userNameExist(username);

		ResponseMessage response = new ResponseMessage();

		response.setMessage(message);
		log.info("end usernameExist method");
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public ResponseEntity<?> getUsername(@RequestHeader("auth") String header) {
		log.info("start getUsername method");
		String username = this.authService.getUsername(header);

		ResponseMessage message = new ResponseMessage();

		message.setMessage(username);
		log.info("end getUsername method");
		return ResponseEntity.ok(message);

	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@RequestHeader("auth") String header) {
		log.info("start getUser method");
		Agent agent = this.authService.getAgent(header);
		log.info("start getUser method");
		return ResponseEntity.ok(agent);

	}

}
