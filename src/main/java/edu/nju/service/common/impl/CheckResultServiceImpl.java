package edu.nju.service.common.impl;

import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.common.CheckResultService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class CheckResultServiceImpl implements CheckResultService {
    @Override
    public List<SingleCheck> getGroupAllCheckstyleChecks(long groupId) {
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllPmdChecks(long groupId) {
        return null;
    }

    @Override
    public List<SingleCheck> getGroupAllSonarChecks(long groupId) {
        return null;
    }
}
