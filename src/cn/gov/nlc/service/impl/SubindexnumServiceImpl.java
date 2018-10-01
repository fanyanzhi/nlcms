package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.gov.nlc.mapper.SubindexnumMapper;
import cn.gov.nlc.pojo.Subindexnum;
import cn.gov.nlc.pojo.SubindexnumExample;
import cn.gov.nlc.service.SubindexnumService;

@Service
public class SubindexnumServiceImpl implements SubindexnumService{

	@Autowired
	private SubindexnumMapper subindexnumMapper;

	@Override
	public Subindexnum getpo() {
		SubindexnumExample example = new SubindexnumExample();
		example.setOrderByClause("id asc");
		List<Subindexnum> list = subindexnumMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updatePo(Subindexnum subindexnum) {
		subindexnumMapper.updateByPrimaryKey(subindexnum);
	}
}
