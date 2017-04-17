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

    @RequestMapping(method = RequestMethod.GET)
    public String totalInfo(Model model) {
        return "jsp/checkstyle/check";
    }

    @GetMapping("/group")
    public String groupTotal() {
//        ArrayList<>
        return "jsp/checkstyle/group";
    }

    @GetMapping("/group/{groupId}")
    public String groupInfo(@PathVariable String groupId,
                            @MatrixVariable(required = false, defaultValue = "1")int check,
                            @MatrixVariable(required = false, defaultValue = "warn")String type,
                            Model model) {
        model.addAttribute("check",check-1);
        model.addAttribute("type", type.toLowerCase());
        model.addAttribute("groupId", groupId);
        return "jsp/checkstyle/groupDetail";
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
