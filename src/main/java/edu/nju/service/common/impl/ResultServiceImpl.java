package edu.nju.service.common.impl;

import edu.nju.Vo.common.SingleCheck;
import edu.nju.service.common.ResultService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */
@Service
public class ResultServiceImpl implements ResultService {
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
