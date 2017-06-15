package com.meirengu.mj.dao;

import com.meirengu.mj.core.model.MJobLog;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job log
 * @author xuxueli 2016-1-12 18:03:06
 */
public interface IMJobLogDao {
	
	public List<MJobLog> pageList(int offset, int pagesize, int jobGroup, int jobId, Date triggerTimeStart, Date triggerTimeEnd);
	public int pageListCount(int offset, int pagesize, int jobGroup, int jobId, Date triggerTimeStart, Date triggerTimeEnd);
	
	public MJobLog load(int id);

	public int save(MJobLog xxlJobLog);

	public int updateTriggerInfo(MJobLog xxlJobLog);

	public int updateHandleInfo(MJobLog xxlJobLog);
	
	public int delete(int jobId);

	public int triggerCountByHandleCode(int handleCode);

	public List<Map<String, Object>> triggerCountByDay(Date from, Date to, int handleCode);

	public int clearLog(int jobGroup, int jobId, Date clearBeforeTime, int clearBeforeNum);

}
