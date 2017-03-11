package edu.nju.controller.codacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.service.codacy.CodacyService;

@Controller
@RequestMapping("/codacy")
public class CodacyController {
	
	@Autowired
	private CodacyService service;
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	public String insert()
	{
		service.insertData();
		return "{\"success\"}";
	}

}
