package edu.nju.controller.checkstyle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by floyd on 2017/3/21.
 */
@Controller
@RequestMapping("/checkstyle")
public class CheckStyleController {

    @GetMapping("/group/{groupId}")
    public String groupInfo(@PathVariable String groupId,
                            @MatrixVariable(required = false, defaultValue = "1")int check,
                            Model model) {
        model.addAttribute("check",check);
        model.addAttribute("groupId", groupId);
        return "jsp/checkstyle/groupDetail";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String totalInfo(Model model) {
        return "jsp/checkstyle/group";
    }


    @GetMapping("/config")
    public String config(Model model){
        return "jsp/checkstyle/config";
    }

    @GetMapping("/stats")
    public String stats(Model model){
        return "jsp/checkstyle/stats";
    }

//    @PostMapping("/upload")


}
