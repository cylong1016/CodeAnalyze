package edu.nju.controller.checkstyle;

import com.google.gson.Gson;
import edu.nju.Po.checkstyle.GroupBriefInfo;
import edu.nju.Po.checkstyle.GroupInfo;
import edu.nju.service.checkstyle.CheckstyleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Floyd on 2017/3/27.
 */
@RestController
@RequestMapping("/checkstyle/api")
public class CheckStyleRESTController {
    @Autowired
    private CheckstyleService service;

    @GetMapping("/group")
    public String group() {
        List<GroupBriefInfo> groupsInfo = service.getAllBriefResult();
        String response = new Gson().toJson(groupsInfo);
        return response;
    }

    @GetMapping("/group/{groupId}")
    public String groupDetail(@PathVariable String groupId){
        GroupInfo groupInfo = service.getSingleGroupResult(Long.parseLong(groupId));
        String response = new Gson().toJson(groupInfo);
        return response;
    }
}
