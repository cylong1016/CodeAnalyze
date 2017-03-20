package edu.nju.controller.pmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download()
	{
		service.download();;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/analyze", method = RequestMethod.GET)
	public String analyze()
	{
		service.analyze();;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getIter", method = RequestMethod.GET)
	public String getIter()
	{
		service.getIter();
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(int iter)
	{
		service.getAllGroup(iter);;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAve", method = RequestMethod.GET)
	public String getAve()
	{
		service.getAve();;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOneGroup", method = RequestMethod.GET)
	public String getOneGroup(int iter,String type,String groupName)
	{
		service.getOneGroup(1, "basic","StockEy");
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(HttpServletRequest req, HttpServletResponse resp)
	{
		service.exportDetail(1, "basic","StockEy",resp);
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOneMeasure", method = RequestMethod.GET)
	public String getOneMeasure()
	{
		service.getMeasure(1, "StockEy");
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	public String getCurrent()
	{
		service.getCurrent("StockEy");
		return "{\"success\"}";
	}
	
}
