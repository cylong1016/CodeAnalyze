package edu.nju.controller.pmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.service.pmd.PMDService;

@Controller
@RequestMapping("/pmd")
public class PMDController {

	@Autowired
	private PMDService service;
	
	@ResponseBody
	@RequestMapping(value = "/analyze", method = RequestMethod.GET)
	public String analyze()
	{
		service.analyze(1);;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll()
	{
		service.getAllGroup(1);;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOneGroup", method = RequestMethod.GET)
	public String getOneGroup()
	{
		service.getOneGroup(1, "basic","StockEy");
		return "{\"success\"}";
	}
}
