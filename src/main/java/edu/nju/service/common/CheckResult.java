package edu.nju.service.common;

import edu.nju.Vo.common.SingleCheck;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface CheckResult {

    public List<SingleCheck> getGroupAllCheckstyleChecks(String groupId);

    public List<SingleCheck> getGroupAllPmdChecks(String groupId);

    public List<SingleCheck> getGroupAllSonarChecks(String groupId);

}
