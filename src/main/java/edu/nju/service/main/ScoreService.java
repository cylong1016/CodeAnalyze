package edu.nju.service.main;

import java.util.List;

import edu.nju.Vo.common.GroupAllScore;

/**
 * Created by Administrator on 2017/4/18.
 */
public interface ScoreService {

    public GroupAllScore getGroupAllScore(long groupId);

	/**
	 * 获得所有组的所有分数
	 * @author cylong
	 * @version 2017年4月29日  下午10:32:09
	 */
	public List<GroupAllScore> getAllGroupScore();

}
