package cn.gov.nlc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gov.nlc.mapper.NlcnoticeannexMapper;
import cn.gov.nlc.pojo.Nlcnoticeannex;
import cn.gov.nlc.pojo.NlcnoticeannexExample;
import cn.gov.nlc.pojo.NlcnoticeannexExample.Criteria;
import cn.gov.nlc.service.NlcnoticeannexService;

@Service
public class NlcnoticeannexServiceImpl implements NlcnoticeannexService {

	@Autowired
	private NlcnoticeannexMapper nlcnoticeannexMapper;

	@Override
	public int deleteSingleById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertNoticeAnnex(Nlcnoticeannex nlcnoticeannex) {
		nlcnoticeannexMapper.insert(nlcnoticeannex);
	}

	@Override
	public List<Nlcnoticeannex> selectByNoticeId(String noticeId) {
		NlcnoticeannexExample example = new NlcnoticeannexExample();
		Criteria criteria = example.createCriteria();
		criteria.andNoticeidEqualTo(noticeId);
		return nlcnoticeannexMapper.selectByExample(example);
	}

	@Override
	public int deleteByNoteceId(String noticeId) {
		return 0;
	}
}
