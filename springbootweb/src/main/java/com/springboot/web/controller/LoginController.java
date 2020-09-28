package com.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author balekundrim
 *
 */
@Controller
public class LoginController {
	
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}

}
