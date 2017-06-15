package com.meirengu.mj.dao.impl;

import com.meirengu.mj.dao.IMJobLogDao;
import com.meirengu.mj.core.model.MJobLog;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * job log
 * @author xuxueli 2016-1-12 18:03:06
 */
@Repository
public class MJobLogDaoImpl implements IMJobLogDao {
	
	@Resource
	public SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<MJobLog> pageList(int offset, int pagesize, int jobGroup, int jobId, Date triggerTimeStart, Date triggerTimeEnd) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("jobGroup", jobGroup);
		params.put("jobId", jobId);
		params.put("triggerTimeStart", triggerTimeStart);
		params.put("triggerTimeEnd", triggerTimeEnd);
		
		return sqlSessionTemplate.selectList("MJobLogMapper.pageList", params);
	}

	@Override
	public int pageListCount(int offset, int pagesize, int jobGroup, int jobId, Date triggerTimeStart, Date triggerTimeEnd) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("jobGroup", jobGroup);
		params.put("jobId", jobId);
		params.put("triggerTimeStart", triggerTimeStart);
		params.put("triggerTimeEnd", triggerTimeEnd);
		
		return sqlSessionTemplate.selectOne("MJobLogMapper.pageListCount", params);
	}

	@Override
	public MJobLog load(int id) {
		return sqlSessionTemplate.selectOne("MJobLogMapper.load", id);
	}

	@Override
	public int save(MJobLog xxlJobLog) {
		return sqlSessionTemplate.insert("MJobLogMapper.save", xxlJobLog);
	}

	@Override
	public int updateTriggerInfo(MJobLog xxlJobLog) {
		if (xxlJobLog.getTriggerMsg()!=null && xxlJobLog.getTriggerMsg().length()>2000) {
			xxlJobLog.setTriggerMsg(xxlJobLog.getTriggerMsg().substring(0, 2000));
		}
		return sqlSessionTemplate.update("MJobLogMapper.updateTriggerInfo", xxlJobLog);
	}

	@Override
	public int updateHandleInfo(MJobLog xxlJobLog) {
		if (xxlJobLog.getHandleMsg()!=null && xxlJobLog.getHandleMsg().length()>2000) {
			xxlJobLog.setHandleMsg(xxlJobLog.getHandleMsg().substring(0, 2000));
		}
		return sqlSessionTemplate.update("MJobLogMapper.updateHandleInfo", xxlJobLog);
	}

	@Override
	public int delete(int jobId) {
		return sqlSessionTemplate.delete("MJobLogMapper.delete", jobId);
	}

	@Override
	public int triggerCountByHandleCode(int handleCode) {
		return sqlSessionTemplate.selectOne("MJobLogMapper.triggerCountByHandleCode", handleCode);
	}

	@Override
	public List<Map<String, Object>> triggerCountByDay(Date from, Date to, int handleCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("from", from);
		params.put("to", to);
		params.put("handleCode", handleCode);
		return sqlSessionTemplate.selectList("MJobLogMapper.triggerCountByDay", params);
	}

	@Override
	public int clearLog(int jobGroup, int jobId, Date clearBeforeTime, int clearBeforeNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jobGroup", jobGroup);
		params.put("jobId", jobId);
		params.put("clearBeforeTime", clearBeforeTime);
		params.put("clearBeforeNum", clearBeforeNum);
		return sqlSessionTemplate.delete("MJobLogMapper.clearLog", params);
	}

}
