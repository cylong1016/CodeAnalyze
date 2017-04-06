package edu.nju.controller.checkstyle;

import com.google.gson.Gson;
import edu.nju.Po.checkstyle.GroupBriefInfo;
import edu.nju.Po.checkstyle.GroupInfo;
import edu.nju.service.checkstyle.CheckstyleService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @GetMapping("/check")
    public String getChecks() {
        return new Gson().toJson(service.getAllChecks());
    }

    @PostMapping("/check")
    public String addCheck(
            @RequestParam("year") int year, @RequestParam("month")int month, @RequestParam("day")int day,
            @RequestParam(name="description", required = false)String description){
        Date date = new Date(year-1900, month-1, day);
        if(service.addCheck( date, description)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
        return null;
    }
}
