package cn.gov.nlc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gov.nlc.mapper.NlcadminMapper;
import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.NlcadminExample;
import cn.gov.nlc.pojo.NlcadminExample.Criteria;
import cn.gov.nlc.service.NlcadminService;
import cn.gov.nlc.util.MD5Util;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlcadminServiceImpl implements NlcadminService{

	@Autowired
	private NlcadminMapper nlcadminMapper;
	
	/**
	 * 通过用户名查询用户信息
	 */
	@Override
	public Nlcadmin GetAdminByName(String username) {
		NlcadminExample example = new NlcadminExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Nlcadmin> list = nlcadminMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * 管理员列表内容的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@Override
	public EasyUiDataGridResult getAdminList(int page, int rows, Nlcadmin nlcadmin) {
		String username = nlcadmin.getUsername();
		String staffname = nlcadmin.getStaffname();
		String staffdept = nlcadmin.getStaffdept();
		String staffphone = nlcadmin.getStaffphone();
		Byte role = nlcadmin.getRole();
		
		//分页
		PageHelper.startPage(page, rows);
		NlcadminExample example = new NlcadminExample();
		Criteria criteria = example.createCriteria();
		
		if(StringUtils.isNotBlank(username)) {
			criteria.andUsernameLike("%"+username+"%");
		}
		if(StringUtils.isNotBlank(staffname)) {
			criteria.andStaffnameLike("%"+staffname+"%");
		}
		
		if(StringUtils.isNotBlank(staffdept)) {
			criteria.andStaffdeptLike("%"+staffdept+"%");
		}
		
		if(StringUtils.isNotBlank(staffphone)) {
			criteria.andStaffphoneLike("%"+staffphone+"%");
		}
		
		if(null != role) {
			criteria.andRoleEqualTo(role);
		}
		
		List<Nlcadmin> list = nlcadminMapper.selectByExample(example);
		PageInfo<Nlcadmin> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 删除管理员信息，单个删除
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlcadminMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 通过主键查询管理员信息
	 * @param id
	 * @return
	 */
	@Override
	public Nlcadmin selectByPrimaryKey(Integer id) {
		Nlcadmin nlcadmin = nlcadminMapper.selectByPrimaryKey(id);
		return nlcadmin;
	}

	/**
	 * 更新管理员信息
	 */
	@Override
	public void updateAdmin(Nlcadmin nlcadmin) {
		nlcadminMapper.updateByPrimaryKeySelective(nlcadmin);
	}

	/**
	 * 修改密码
	 */
	@Override
	public void changePassword(Nlcadmin nlcadmin) {
		nlcadminMapper.updateByPrimaryKeySelective(nlcadmin);
	}

	/**
	 * 添加管理员
	 */
	@Override
	public void insertAdmin(Nlcadmin nlcadmin) {
		String password = nlcadmin.getPassword();
		nlcadmin.setPassword(MD5Util.md5(password));
		nlcadmin.setTime(new Date());
		nlcadminMapper.insert(nlcadmin);
	}

	@Override
	public void ttt(Nlcadmin nlcadmin) {
		String username = nlcadmin.getUsername();
		Nlcadmin dbAdmin = GetAdminByName(username);
		if(null == dbAdmin) {
			insertAdmin(nlcadmin);
		}
	}

}
