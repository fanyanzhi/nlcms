package cn.gov.nlc.service;

import java.util.List;
import cn.gov.nlc.pojo.Nlcadminlog;
import cn.gov.nlc.pojo.NlcadminlogExt;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlcadminlogService {

	/**
	 * 管理员日志内容的分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUiDataGridResult getAdminList(int page, int rows, NlcadminlogExt nlcadminlogExt);
	
	/**
	 * 删除日志信息，单个删除
	 */
	public int deleteSingleById(Integer id);
	
	/**
	 * 查询所有的日志信息
	 */
	public List<Nlcadminlog> getAll();
	
	/**
	 * 插入管理员日志
	 */
	public void insertNlcadminlog(Nlcadminlog nlcadminlog);
}
