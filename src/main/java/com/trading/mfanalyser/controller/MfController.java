package com.trading.mfanalyser.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/testmf")
public class MfController {
	
	@RequestMapping(value = "hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String hello(ModelMap model) {
		
		return "hello";
	}
	
}
