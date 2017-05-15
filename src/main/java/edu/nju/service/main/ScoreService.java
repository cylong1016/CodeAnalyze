package edu.nju.service.main;

import java.util.List;

import edu.nju.Vo.common.GroupAllScore;
import edu.nju.entities.Regression;

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

	/**
	 * 获得全部的线性相关性统计数据。
	 * @param iter 某次迭代
	 * @return
	 * @author cylong
	 * @version 2017年5月15日  下午11:10:47
	 */
	public List<Regression> getRegression(String iter);

}
