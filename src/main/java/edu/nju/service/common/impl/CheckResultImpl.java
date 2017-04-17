package edu.nju.service.common.impl;

import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.common.CheckResult;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public class CheckResultImpl implements CheckResult {
    @Override
    public List<SingleCheck> getGroupAllCheckstyleChecks(String groupId) {
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllPmdChecks(String groupId) {
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllSonarChecks(String groupId) {
        return null;
    }
}
