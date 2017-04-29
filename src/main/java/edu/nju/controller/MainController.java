package edu.nju.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2017/4/18.
 */
@Controller
public class MainController {

	@GetMapping("/index")
	public String indexPage() {
		return "jsp/main/index";
	}

	@GetMapping("/group/{groupId}")
	public String groupDetail(@PathVariable String groupId, Model model) {
		model.addAttribute("groupId", groupId);
		return "jsp/main/groupDetail";
	}

	@GetMapping("/config")
	public String config() {
		return "jsp/main/config";
	}

	/**
	 * 相关性统计
	 * @return page_location
	 */
	@GetMapping("/statistics")
	public String statistics() {
		return "jsp/main/statistics";
	}

}
