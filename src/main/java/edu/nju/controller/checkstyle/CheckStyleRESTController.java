package edu.nju.controller.checkstyle;

import com.google.gson.Gson;
import edu.nju.Vo.checkstyle.GroupBriefInfo;
import edu.nju.Vo.checkstyle.GroupInfo;
import edu.nju.Vo.common.GroupAllScore;
import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.checkstyle.CheckstyleService;
import edu.nju.service.common.CheckResultService;
import edu.nju.service.common.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CheckResultService resultService;

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

    @GetMapping("/grade/{groupId}")
    public String getGroupGrade(@PathVariable String groupId){
        GroupAllScore groupScore = scoreService.getGroupAllScore(Long.parseLong(groupId));
        String response = new Gson().toJson(groupScore);
        return response;
    }

    @GetMapping("/checkstyle/result/{groupId}")
    public String getCheckstyleResult(@PathVariable String groupId){
        List<SingleCheck> checkstyleResult = resultService.getGroupAllCheckstyleChecks(Long.parseLong(groupId));
        return new Gson().toJson(checkstyleResult);
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

    @GetMapping("/config")
    public String getChecksConfig(){
        String response = new Gson().toJson(service.getCheckConfig());
        return response;
    }

    @PostMapping("/config")
    public String updateChecksConfig(@RequestParam("checkIds[]") String[] checkedId){
        List<Long> checked = new ArrayList<>();
        for(String item : checkedId){
            Long id;
            try{
                id = Long.parseLong(item);
            }catch (Exception e){
                continue;
            }
            checked.add(id);
        }
        if (service.updateCheckConfig(checked)){
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/stat/{internalType}")
    public String getOneStatistics(@PathVariable String internalType){
        return new Gson().toJson( service.getCorrelationStat(internalType) );
    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("check_id")String checkId){
        Map<String, Object> json = new HashMap<>();
        if(!file.isEmpty()){
            try {
                InputStream in = file.getInputStream();
                BufferedReader reader = new BufferedReader( new InputStreamReader(in) );
                String line;
                while((line=reader.readLine())!=null){
                    String[] attrs = line.split(",");
                    boolean ifSave = service.addGrade(Long.parseLong(checkId), Long.parseLong(attrs[0]), Integer.parseInt(attrs[1]), attrs[2]);
                    if(!ifSave){
                        json.put("success","Insert Database Error");
                        return json;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                json.put("success","File Reader Error");
                return json;
            }
        }
        System.out.println(checkId);
        json.put("success", "0");
        return json;
    }
}
