package edu.nju.controller.common;

import com.google.gson.Gson;
import edu.nju.Vo.common.GroupAllScore;
import edu.nju.service.common.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/4/18.
 */
@RestController
@RequestMapping("/score/api")
public class ScoreRESTController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/{groupId}")
    public String getGroupGrade(@PathVariable String groupId){
        GroupAllScore groupScore = scoreService.getGroupAllScore(Long.parseLong(groupId));
        String response = new Gson().toJson(groupScore);
        return response;
    }
}
