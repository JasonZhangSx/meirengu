package com.meirengu.mj.dao;

import com.meirengu.utils.scheduleUtil.MJobInfo;
import java.util.List;


/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
public interface IMJobInfoDao {

	public List<MJobInfo> pageList(int offset, int pagesize, int jobGroup, String executorHandler, Integer finalized);
	public int pageListCount(int offset, int pagesize, int jobGroup, String executorHandler, Integer finalized);
	
	public int save(MJobInfo info);

	public MJobInfo loadById(int id);
	
	public int update(MJobInfo item);
	
	public int delete(int id);

	public List<MJobInfo> getJobsByGroup(String jobGroup);

	public int findAllCount();

}
