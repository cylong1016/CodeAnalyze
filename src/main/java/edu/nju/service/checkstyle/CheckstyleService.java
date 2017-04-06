package edu.nju.service.checkstyle;

import edu.nju.Po.checkstyle.GroupBriefInfo;
import edu.nju.Po.checkstyle.GroupInfo;
import edu.nju.Po.checkstyle.ResultItem;
import edu.nju.Po.checkstyle.ResultList;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.checkstyle.CheckLog;
import edu.nju.entities.checkstyle.Group;
import edu.nju.entities.checkstyle.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/4/3.
 */
@Service
public class CheckstyleService {
    @Autowired
    private CheckStyleDao dao;

    public List<GroupInfo> getAllResult() {
        List<GroupInfo> returnList = new ArrayList<>();
        List<Group> allGroup = dao.getAllGroup();
        List<CheckLog> allCheck = dao.getAllCheck();
        for (Group group : allGroup) {
            GroupInfo singleGroupInfo = new GroupInfo(group.getId());
            for (CheckLog check : allCheck) {
                ResultList singeCheckInfo = new ResultList(check.getCheckDate());
                Map<String, Object> query = new HashMap<>();

                query.put("group_id", group.getId());
                query.put("check_id", check.getId());
                List<Result> results = dao.findResult(query);
                singeCheckInfo.transToPo(results);

                singleGroupInfo.addSingleCheckInfo(singeCheckInfo);
            }
            returnList.add(singleGroupInfo);
        }
        return returnList;
        //        for(Result item : allResults){
//            GroupInfo singleGroup;
//            if (groupsInfo.keySet().contains(item.getGroupId())){
//                singleGroup = groupsInfo.get(item.getGroupId());
//            } else {
//                singleGroup = new GroupInfo(item.getGroupId());
//                groupsInfo.put(item.getGroupId(), singleGroup);
//            }
//            ResultList singleCheckResult;
//            if (singleGroup.getResults().keySet().contains(item.getCheckId())){
//                singleCheckResult = singleGroup.getResults().get(item.getCheckId());
//            } else {
//                Map<String, Object> checkDateFindQuery = new HashMap<>();
//                checkDateFindQuery.put("id", item.getCheckId());
//                Date checkDate = baseDao.find(CheckLog.class, checkDateFindQuery).get(0).getCheckDate();
//                singleCheckResult = new ResultList(checkDate);
//                singleGroup.addResult(item.getCheckId(), singleCheckResult);
//            }
//            ResultItem resultItem = new ResultItem(item.getFatherType(), item.getSubType(), item.getFile(), item.getRow(), item.getCol(), item.getDescription());
//            if (item.getFatherType()=="warn"){
//                singleCheckResult.addWarn(resultItem);
//            }else if (item.getFatherType()=="error"){
//                singleCheckResult.addError(resultItem);
//            }
//        }
//        return new ArrayList<GroupInfo>(groupsInfo.values());
    }

    public GroupInfo getSingleGroupResult(long id){
        GroupInfo groupInfo = new GroupInfo(id);
        Map<String, Object> querys = new HashMap<>();
        querys.put("groupId", id);
        List<CheckLog> allCheckLog = dao.getAllCheck();
        for (CheckLog check : allCheckLog){
            ResultList singleCheck = new ResultList(check.getCheckDate());
            querys.put("checkId", check.getId());
            List<Result> results = dao.findResult(querys);
            for(Result result: results){
                singleCheck.addResultItem(new ResultItem(result.getFatherType(), result.getSubType(), result.getFile(), result.getRow(), result.getCol(), result.getDescription()));
            }
            groupInfo.addSingleCheckInfo(singleCheck);
        }
    return groupInfo;
    }

    public List<GroupBriefInfo> getAllBriefResult() {
        List<GroupBriefInfo> briefInfos = new ArrayList<>();
        List<Group> allGroup = dao.getAllGroup();
        List<CheckLog> allCheck = dao.getAllCheck();
        // 根据check时间排序
        Collections.sort(allCheck, Comparator.comparing(CheckLog::getCheckDate));
        for(Group group : allGroup){
            GroupBriefInfo singleGroupBriefInfo = new GroupBriefInfo(group.getId());
            for (CheckLog check : allCheck){
                int[] singleCheckBriefInfo = new int[2];
                Map<String, Object> query = new HashMap<>();
                query.put("groupId", group.getId());
                query.put("checkId", check.getId());

                query.put("fatherType", "WARN");
                singleCheckBriefInfo[0] = dao.findResult(query).size();

                query.put("fatherType","ERROR");
                singleCheckBriefInfo[1] = dao.findResult(query).size();

                singleGroupBriefInfo.addSingleCheckBriefInfo(check.getCheckDate(),singleCheckBriefInfo);
            }
            briefInfos.add(singleGroupBriefInfo);
        }
        return briefInfos;
    }
}
