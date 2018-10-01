package cn.gov.nlc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.NlcuseronlineMapper;
import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.pojo.NlcuseronlineExample;
import cn.gov.nlc.pojo.NlcuseronlineExample.Criteria;
import cn.gov.nlc.service.NlcuseronlineService;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcuseronlineServiceImpl implements NlcuseronlineService {

	@Autowired
	private NlcuseronlineMapper nlcuseronlineMapper;

	@Override
	public void addNlcuseronline(Nlcuseronline nlcuseronline) {
		nlcuseronlineMapper.insert(nlcuseronline);
	}

	@Override
	public boolean isExistUserOnline(String loginAccount, String clientId) {
		NlcuseronlineExample example = new NlcuseronlineExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		criteria.andClientidEqualTo(clientId);
		if (nlcuseronlineMapper.countByExample(example) > 0)
			return true;
		else
			return false;
	}

	@Override
	public void updateNlcuseronline(Nlcuseronline nlcuseronline) {
		NlcuseronlineExample example = new NlcuseronlineExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(nlcuseronline.getLoginaccount());
		criteria.andClientidEqualTo(nlcuseronline.getClientid());
		nlcuseronlineMapper.updateByExampleSelective(nlcuseronline, example);
	}

	@Override
	public String[] getLoginaccount(String userToken) {
		String[] result = new String[] { "", "" };
		NlcuseronlineExample example = new NlcuseronlineExample();
		Criteria criteria = example.createCriteria();
		criteria.andTokenEqualTo(userToken);
		List<Nlcuseronline> lst = nlcuseronlineMapper.selectByExample(example);
		if (lst != null && lst.size() > 0) {
			result[0] = lst.get(0).getLoginaccount();
			result[1] = lst.get(0).getBorid();

		}
		return result;
	}
	
	public void deleteOnlineUser(String loginAccount, String clientid){
		NlcuseronlineExample example = new NlcuseronlineExample();
		Criteria criteria = example.createCriteria();
		criteria.andLoginaccountEqualTo(loginAccount);
		criteria.andClientidEqualTo(clientid);
		nlcuseronlineMapper.deleteByExample(example);
	}

	/**
	 * 在线用户的分页list
	 */
	@Override
	public EasyUiDataGridResult getNlcuseronlineList(int page, int rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlcuseronlineExample example = new NlcuseronlineExample();
		example.setOrderByClause("time desc");
		List<Nlcuseronline> list = nlcuseronlineMapper.selectByExample(example);
		PageInfo<Nlcuseronline> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 在线用户的所有数据
	 */
	@Override
	public List<Nlcuseronline> getAll() {
		NlcuseronlineExample example = new NlcuseronlineExample();
		example.setOrderByClause("time desc");
		List<Nlcuseronline> list = nlcuseronlineMapper.selectByExample(example);
		return list;
	}

}
