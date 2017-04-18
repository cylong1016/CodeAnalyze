package edu.nju.service.checkstyle;

import edu.nju.Vo.checkstyle.*;
import edu.nju.Vo.common.Check;
import edu.nju.dao.CheckDao;
import edu.nju.dao.GroupDao;
import edu.nju.dao.TeacherScoreDao;
import edu.nju.dao.checkstyle.CheckStyleDao;
import edu.nju.entities.*;
import edu.nju.entities.checkstyle.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Floyd on 2017/4/3.
 */
@Service
public class CheckstyleService {
    @Autowired
    private CheckStyleDao dao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private CheckDao checkDao;
    @Autowired
    private TeacherScoreDao teacherScoreDao;

    public List<GroupInfo> getAllResult() {
        List<GroupInfo> returnList = new ArrayList<>();
        List<StudentGroup> allGroup = groupDao.getAllGroup();
        List<CheckLog> allCheck = checkDao.getAllCheck();
        for (StudentGroup group : allGroup) {
            GroupInfo singleGroupInfo = new GroupInfo(group.getId());
            for (CheckLog check : allCheck) {
                if( !ifCheckdayPass(check.getCheckDate())){
                    continue;
                }
                Map<String, Object> gradeQuery = new HashMap<>();
                gradeQuery.put("checkId", check.getId());
                gradeQuery.put("groupId", group.getId());
                int grade = 0;
                List<TeacherScore> groupGrade = teacherScoreDao.getTeacherScoreByQuery(gradeQuery);
                if( groupGrade.size()==1 ){
                    grade = groupGrade.get(0).getScore();
                }
                ResultList singeCheckInfo = new ResultList(check.getCheckDate(), grade);
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
    }

    public List<GroupBriefInfo> getAllBriefResult() {
//        List<GroupBriefInfo> briefInfos = new ArrayList<>();
//        List<Group> allGroup = dao.getAllGroup();
//        List<CheckLog> allCheck = dao.getAllCheck();
//        List<String> activeTypes = getActiveTypes();
//        // 根据check时间排序
//        Collections.sort(allCheck, Comparator.comparing(CheckLog::getCheckDate));
//        for(Group group : allGroup){
//            GroupBriefInfo singleGroupBriefInfo = new GroupBriefInfo(group.getId());
//            for (CheckLog check : allCheck){
//                if( !ifCheckdayPass(check.getCheckDate())){
//                    continue;
//                }
////                if ( !ifCheckActive())
//                int[] singleCheckBriefInfo = new int[2];
//                Map<String, Object> query = new HashMap<>();
//                query.put("groupId", group.getId());
//                query.put("checkId", check.getId());
//
//                query.put("fatherType", Constant.FATHER_TYPE_WARN);
//                List<Result> warnList = dao.findResult(query);
//                Iterator<Result> warnListItr = warnList.iterator();
//                while(warnListItr.hasNext()){
//                    Result result = warnListItr.next();
//                    if(activeTypes.indexOf(result.getSubType())==-1){
//                        warnListItr.remove();
//                    }
//                }
//                singleCheckBriefInfo[0] = warnList.size();
//
//                query.put("fatherType", Constant.FATHER_TYPE_ERROR);
//                List<Result> errorList = dao.findResult(query);
//                Iterator<Result> errorListItr = errorList.iterator();
//                while(errorListItr.hasNext()){
//                    Result result = errorListItr.next();
//                    if(activeTypes.indexOf(result.getSubType())==-1){
//                        errorListItr.remove();
//                    }
//                }
//                singleCheckBriefInfo[1] = errorList.size();
//
//                singleGroupBriefInfo.addSingleCheckBriefInfo(check.getCheckDate(),singleCheckBriefInfo);
//            }
//            briefInfos.add(singleGroupBriefInfo);
//        }
//        return briefInfos;
        return null;
    }

    public GroupInfo getSingleGroupResult(long id){
//        GroupInfo groupInfo = new GroupInfo(id);
//        Map<String, Object> querys = new HashMap<>();
//        querys.put("groupId", id);
//        List<CheckLog> allCheckLog = dao.getAllCheck();
//        List<String> activeTypes = getActiveTypes();
//        for (CheckLog check : allCheckLog){
//            if( !ifCheckdayPass(check.getCheckDate())){
//                continue;
//            }
//            Map<String, Object> gradeQuery = new HashMap<>();
//            gradeQuery.put("checkId", check.getId());
//            gradeQuery.put("groupId", id);
//            int grade = 0;
//            List<Grade> groupGrade = dao.getGradeByQuery(gradeQuery);
//            if( groupGrade.size()==1 ){
//                grade = groupGrade.get(0).getGrade();
//            }
//            ResultList singleCheck = new ResultList(check.getCheckDate(), grade);
//            querys.put("checkId", check.getId());
//            List<Result> results = dao.findResult(querys);
//            Iterator<Result> resultsItr = results.iterator();
//            while(resultsItr.hasNext()){
//                Result result = resultsItr.next();
//                if(activeTypes.indexOf(result.getSubType())!=-1){
//                    singleCheck.addResultItem(new ResultItem(result.getFatherType(), result.getSubType(), result.getFile(), result.getRow(), result.getCol(), result.getDescription()));
//                }
//            }
////            for(Result result: results){
////                singleCheck.addResultItem(new ResultItem(result.getFatherType(), result.getSubType(), result.getFile(), result.getRow(), result.getCol(), result.getDescription()));
////            }
//            groupInfo.addSingleCheckInfo(singleCheck);
//        }
//        return groupInfo;
        return null;
    }

    public List<Check> getAllChecks(){
//        List<Check> results = new ArrayList<>();
//        List<CheckLog> allChecks = dao.getAllCheck();
//        Map<String, Object> querys = new HashMap<>();
//        List<String> activeTypes = getActiveTypes();
//        for(CheckLog singleCheck : allChecks){
//            Check singleCheckPo = new Check(singleCheck);
//            String hql = "SELECT distinct R.groupId FROM Result R WHERE R.checkId=" + String.valueOf(singleCheck.getId());
//            for(Object groupId: dao.findByHql(hql)){
//                querys.put("groupId", (long)groupId);
//                querys.put("checkId", singleCheck.getId());
//
//                querys.put("fatherType", Constant.FATHER_TYPE_WARN);
//                List<Result> warnList = dao.findResult(querys);
//                Iterator<Result> warnListItr = warnList.iterator();
//                while(warnListItr.hasNext()){
//                    Result result = warnListItr.next();
//                    if(activeTypes.indexOf(result.getSubType())==-1){
//                        warnListItr.remove();
//                    }
//                }
//                int warn_count = warnList.size();
//
//                querys.put("fatherType", Constant.FATHER_TYPE_ERROR);
//                List<Result> errorList = dao.findResult(querys);
//                Iterator<Result> errorListItr = errorList.iterator();
//                while(errorListItr.hasNext()){
//                    Result result = errorListItr.next();
//                    if(activeTypes.indexOf(result.getSubType())==-1){
//                        errorListItr.remove();
//                    }
//                }
//                int error_count = errorList.size();
//                singleCheckPo.addGroupInfo( new GroupForCheck((long)groupId, dao.findGroupById((long)groupId).getName(), warn_count, error_count));
//            }
//            results.add(singleCheckPo);
//        }
//        return results;
        return null;
    }

    public boolean addCheck(Date date, String description){
//        return dao.addCheck(date, description);
        return false;
    }

    public List<InternalType> getCheckConfig(){
//       List<InternalType> resultList = new ArrayList<>();
//       String hql = "SELECT distinct C.internalType FROM CheckType C";
//       for(Object internalTypeName : dao.findByHql(hql)){
//           InternalType internalType = new InternalType( (String) internalTypeName);
//           Map<String, Object> querys = new HashMap<>();
//           querys.put("internalType", internalTypeName);
//           for(CheckType subType : dao.findTypeByQuery(querys)){
//               boolean status = ( subType.getStatus()==1 ) ? true : false;
//               internalType.addSubType( new SubType(subType.getId(),subType.getSubType(), status));
//           }
//           resultList.add(internalType);
//       }
//       return resultList;
        return null;
    }

    public boolean updateCheckConfig(List<Long> checkedIds){
////        String hql = "SELECT C.id FROM CheckType C";
//        for(CheckType typeItem : dao.getAllCheckType()){
//            if( checkedIds.indexOf(typeItem.getId())==-1 ){
//                typeItem.setStatus(0);
//            }else {
//                typeItem.setStatus(1);
//            }
//            boolean ifSave = dao.updateCheckType(typeItem);
//            if(!ifSave){
//                return false;
//            }
//        }
        return true;
    }

    public List<SingleCheckStat> getCorrelationStat(String internalType){
//        List<SingleCheckStat> returnList = new ArrayList<>();
//        for(CheckstyleRegression item : dao.getRegressionByInternalType(internalType)){
//            SingleCheckStat singleCheckStat = new SingleCheckStat(item.getCffcA(), item.getCffcB(), internalType);
//
//            Map<String, Object> findCoordinateQuerys = new HashMap<>();
//            findCoordinateQuerys.put("checkId", item.getCheckId());
//            for(Grade grade : dao.getGradeByQuery(findCoordinateQuerys)){
//                findCoordinateQuerys.put("internalType", item.getInternalType());
//                findCoordinateQuerys.put("groupId", grade.getGroupId());
//                List<StatResult> stats = dao.getStatList(findCoordinateQuerys);
//                SampleCoordinate coordinate = new SampleCoordinate(stats.get(0).getCount(), grade.getGrade());
//                singleCheckStat.addCoordinate(coordinate);
//            }
//            returnList.add(singleCheckStat);
//        }
//        return returnList;
        return null;
    }

    public boolean addGrade(long checkId, long groupId, int grade, String comment){
//        Grade gradeEntity = new Grade(checkId, groupId, grade, comment);
//        return dao.addGrade(gradeEntity);
        return false;
    }

    private boolean ifCheckdayPass(Date date){
        if ( date.compareTo(new Date()) <= 0 ){
            return true;
        }else {
            return false;
        }
    }


//    /**
//     * 检查针对某个type的检查是否开启
//     * @param type
//     * @return boolean
//     */
//    private boolean ifCheckActive(String type){
//        List<CheckType> activeTypesEntity = dao.getActiveType();
//        activeTypes = new ArrayList<>();
//        for (CheckType typeEntity : activeTypesEntity){
//            activeTypes.add(typeEntity.getSubType());
//        }
//        if (activeTypes.indexOf(type)==-1){
//            return false;
//        } else{
//            return true;
//        }
//    }

//    private List<String> getActiveTypes(){
//        List<String> activeTypes = new ArrayList<>();
//        List<CheckType> activeTypesEntity = dao.getActiveType();
//        for(CheckType type: activeTypesEntity){
//            activeTypes.add(type.getSubType());
//        }
//        return activeTypes;
//    }
}
