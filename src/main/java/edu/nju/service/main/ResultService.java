package edu.nju.service.main;

import edu.nju.Vo.common.SingleCheck;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface ResultService {

    public List<SingleCheck> getGroupAllCheckstyleChecks(long groupId);

    public List<SingleCheck> getGroupAllPmdChecks(long groupId);

    public List<SingleCheck> getGroupAllSonarChecks(long groupId);

}