package edu.nju.controller.checkstyle;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Floyd on 2017/3/27.
 */
@RestController
@RequestMapping("/checkstyle/api")
public class CheckStyleRESTController {

    @GetMapping("/group")
    public String group() {
        ArrayList<Map> groupInfo = new ArrayList<Map>();
        for (int i = 0; i < 20; i++) {
            Map singleGroup = new HashMap();
            singleGroup.put("id", i);
            singleGroup.put("name", "test_" + (i + ""));
            singleGroup.put("timeline", new String[]{"2017-03-27", "2017-04-27", "2017-05-27"});
            singleGroup.put("warn", new int[]{32, 48, 21});
            singleGroup.put("error", new int[]{3, 18, 4});
            groupInfo.add(singleGroup);
        }
        String response = new Gson().toJson(groupInfo);
        System.out.println(response);
        return response;
    }

}
