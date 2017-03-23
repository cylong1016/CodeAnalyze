package edu.nju.controller.pmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.service.pmd.PMDService;
import edu.nju.service.pmd.PMD_Analyze;

@Controller
@RequestMapping("/pmd")
public class PMDController {

	@Autowired
	private PMDService service;
	
	@Autowired
	private PMD_Analyze analyze;
	
	
	@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download()
	{
		analyze.download();;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/analyze", method = RequestMethod.GET)
	public String analyze()
	{
		analyze.analyze();;
		return "{\"success\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getIter", method = RequestMethod.GET)
	public String getIter()
	{
		return analyze.getIter();
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(int iter)
	{
		return service.getAllGroup(iter);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAve", method = RequestMethod.GET)
	public String getAve()
	{
		return service.getAve();
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOneGroup", method = RequestMethod.GET)
	public String getOneGroup(int iter,String type,String groupName)
	{
		return service.getOneGroup(iter,type,groupName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String export(int iter,String type,String groupName,HttpServletRequest req, HttpServletResponse resp)
	{
		return service.exportDetail(iter,type,groupName,resp);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOneMeasure", method = RequestMethod.GET)
	public String getOneMeasure(String groupName)
	{
		return service.getMeasure(groupName);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
	public String getCurrent(String groupName)
	{
		return 	service.getCurrent(groupName);
	}
	
}
