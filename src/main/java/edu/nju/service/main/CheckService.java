package edu.nju.service.main;

import edu.nju.Vo.common.Check;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface CheckService {

    public List<Check> getAllChecks();

    public boolean addCheck(Date date, String description);
}
