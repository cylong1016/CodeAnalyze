package edu.nju.controller.checkstyle;

import com.google.gson.Gson;
import edu.nju.Vo.checkstyle.GroupBriefInfo;
import edu.nju.Vo.checkstyle.GroupInfo;
import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.checkstyle.CheckstyleService;
import edu.nju.service.main.ResultService;
import edu.nju.service.main.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private ResultService resultService;

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

    @GetMapping("/result/{groupId}")
    public String getCheckstyleResult(@PathVariable String groupId){
        List<SingleCheck> checkstyleResult = resultService.getGroupAllCheckstyleChecks(Long.parseLong(groupId));
        return new Gson().toJson(checkstyleResult);
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
                BufferedReader reader = new BufferedReader( new InputStreamReader(in,"UTF-8") );
                String line;
                while((line=reader.readLine())!=null){
                    System.out.println(line);
                    String[] attrs = line.split(",");

                    boolean ifSave = service.addTeacherScore(Long.parseLong(checkId), Long.parseLong(attrs[0].trim()), Integer.parseInt(attrs[1].trim()), attrs[2].trim());
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
        json.put("success", "Upload Success");
        return json;
    }
}
