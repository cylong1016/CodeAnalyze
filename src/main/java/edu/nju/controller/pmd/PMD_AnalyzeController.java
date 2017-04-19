package edu.nju.controller.pmd;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.service.pmd.PMD_Analyze;

@Controller
@RequestMapping("/pmdAna")
public class PMD_AnalyzeController {

	
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
	public String analyze(HttpServletRequest req)
	{
		analyze.analyze(req);
		return "{\"success\"}";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getIter", method = RequestMethod.GET)
	public String getIter()
	{
		return analyze.getIter();
	}
}
