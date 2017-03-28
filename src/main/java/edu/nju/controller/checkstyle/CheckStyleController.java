package edu.nju.controller.checkstyle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by floyd on 2017/3/21.
 */
@Controller
@RequestMapping("/checkstyle")
public class CheckStyleController {

    @RequestMapping(method = RequestMethod.GET)
    public String totalInfo(Model model) {
        return "jsp/checkstyle/main";
    }

    @GetMapping("/group")
    public String groupTotal() {
//        ArrayList<>
        return "jsp/checkstyle/group";
    }

    @GetMapping("/group/{groupId}")
    public String groupInfo(@PathVariable String groupId, Model model) {
        model.addAttribute("groupId", groupId);
        model.addAttribute("groupName", "wenzi");
        model.addAttribute("submit", new String[]{"2017-03-27", "2017-04-27", "2017-05-27"});
        model.addAttribute("warn", new int[]{32, 48, 21});
        model.addAttribute("error", new int[]{3, 18, 4});
        return "jsp/checkstyle/groupDetail";
    }

    @GetMapping("/timeline")
    public String timelineInfo(Model model) {
        String[] timeline = new String[]{"2017-03-27", "2017-04-27", "2017-05-27"};
        model.addAttribute("timeline", timeline);
        return "jsp/checkstyle/timeline";
    }
}
