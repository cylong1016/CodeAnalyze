package edu.nju.controller.checkstyle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by floyd on 2017/3/21.
 */
@Controller
@RequestMapping("/checkstyle")
public class CheckStyleController {

    @GetMapping("/total")
//    @ResponseBody
    public String totalInfo(){
        return "jsp/checkstyle/main";
    }
}
