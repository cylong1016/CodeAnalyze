package edu.nju.service.common;

import edu.nju.Vo.common.SingleCheck;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface CheckResultService {

    public List<SingleCheck> getGroupAllCheckstyleChecks(long groupId);

    public List<SingleCheck> getGroupAllPmdChecks(long groupId);

    public List<SingleCheck> getGroupAllSonarChecks(long groupId);

}
