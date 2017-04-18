package edu.nju.controller.common;

import com.google.gson.Gson;
import edu.nju.service.common.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/18.
 */
@RestController
@RequestMapping("/check/api")
public class CheckController {

    @Autowired
    private CheckService checkService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllChecks(){
        return new Gson().toJson(checkService.getAllChecks());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addCheck(@RequestParam("year") int year, @RequestParam("month")int month, @RequestParam("day")int day,
                           @RequestParam(name="description", required = false)String description){
        Date date = new Date(year-1900, month-1, day);
        if(checkService.addCheck( date, description)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
        return null;
    }
}
