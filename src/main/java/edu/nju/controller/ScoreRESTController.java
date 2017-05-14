package edu.nju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import edu.nju.Vo.common.GroupAllScore;
import edu.nju.service.main.ScoreService;

/**
 * Created by Administrator on 2017/4/18.
 */
@RestController
@RequestMapping("/score/api")
public class ScoreRESTController {

	@Autowired
	private ScoreService scoreService;

	@GetMapping("/{groupId}")
	public String getGroupGrade(@PathVariable String groupId) {
		GroupAllScore groupScore = scoreService.getGroupAllScore(Long.parseLong(groupId));
		String response = new Gson().toJson(groupScore);
		return response;
	}

	/**
	 * 所有组的所有分数
	 * @author cylong
	 * @version 2017年4月29日 下午10:31:24
	 */
	@GetMapping("/allGroupScore")
	public String getAllGroupScore() {
		List<GroupAllScore> allGroupScore = scoreService.getAllGroupScore();
		String response = new Gson().toJson(allGroupScore);
		return response;
	}

}
