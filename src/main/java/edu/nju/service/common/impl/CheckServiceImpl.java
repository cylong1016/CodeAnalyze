package edu.nju.service.common.impl;

import edu.nju.Vo.common.Check;
import edu.nju.service.common.CheckService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class CheckServiceImpl implements CheckService{
    @Override
    public List<Check> getAllChecks() {
        return null;
    }

    @Override
    public boolean addCheck(Date date, String description) {
        return false;
    }
}
