package edu.nju.controller.pmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.main.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.nju.service.pmd.PMDService;

import java.util.List;

@Controller
@RequestMapping("/pmd")
public class PMDController {

	@Autowired
	private PMDService service;
	@Autowired
	private ResultService resultService;
	
    @RequestMapping(method = RequestMethod.GET)
    public String totalInfo(Model model) {
        return "jsp/pmd/pmd_allgroup";
    }
    
    @GetMapping("/onegroup")
    public String groupTotal() {
        return "jsp/pmd/pmd_onegroup";
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

	@GetMapping("/api/result/{groupId}")
	public String getPmdResult(@PathVariable String groupId){
		List<SingleCheck> pmdResult = resultService.getGroupAllPmdChecks(Long.parseLong(groupId));
		return new Gson().toJson(pmdResult);
	}
	
}
