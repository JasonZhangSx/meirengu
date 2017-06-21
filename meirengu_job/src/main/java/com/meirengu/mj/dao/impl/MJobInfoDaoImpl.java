package com.meirengu.mj.dao.impl;

import com.meirengu.mj.dao.IMJobInfoDao;
import com.meirengu.utils.scheduleUtil.MJobInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * job info
 * @author xuxueli 2016-1-12 18:03:45
 */
@Repository
public class MJobInfoDaoImpl implements IMJobInfoDao {
	
	@Resource
	public SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<MJobInfo> pageList(int offset, int pagesize, int jobGroup, String executorHandler, Integer finalized) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("jobGroup", jobGroup);
		params.put("executorHandler", executorHandler);
		params.put("finalized", finalized);
		
		return sqlSessionTemplate.selectList("MJobInfoMapper.pageList", params);
	}

	@Override
	public int pageListCount(int offset, int pagesize, int jobGroup, String executorHandler, Integer finalized) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("pagesize", pagesize);
		params.put("jobGroup", jobGroup);
		params.put("executorHandler", executorHandler);
		params.put("finalized", finalized);
		
		return sqlSessionTemplate.selectOne("MJobInfoMapper.pageListCount", params);
	}

	@Override
	public int save(MJobInfo info) {
		return sqlSessionTemplate.insert("MJobInfoMapper.save", info);
	}

	@Override
	public MJobInfo loadById(int id) {
		return sqlSessionTemplate.selectOne("MJobInfoMapper.loadById", id);
	}

	@Override
	public int update(MJobInfo item) {
		return sqlSessionTemplate.update("MJobInfoMapper.update", item);
	}

	@Override
	public int delete(int id) {
		return sqlSessionTemplate.update("MJobInfoMapper.delete", id);
	}

	@Override
	public List<MJobInfo> getJobsByGroup(String jobGroup) {
		return sqlSessionTemplate.selectList("MJobInfoMapper.getJobsByGroup", jobGroup);
	}

	@Override
	public int findAllCount() {
		return sqlSessionTemplate.selectOne("MJobInfoMapper.findAllCount");
	}

}
