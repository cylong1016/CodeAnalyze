package edu.nju.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Administrator on 2017/4/18.
 */
@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage(){
        return "";
    }

    @GetMapping("/group/{groupId}")
    public String groupDetail(@PathVariable String groupId, Model model){
        model.addAttribute("groupId", groupId);
        return "jsp";
    }

    @GetMapping("/config")
    public String config(){
        return "";
    }

    /**
     * 相关性统计
     * @return page_location
     */
    @GetMapping("/stat")
    public String statistics(){
        return "";
    }
}
