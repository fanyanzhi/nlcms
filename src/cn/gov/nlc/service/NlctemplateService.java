package cn.gov.nlc.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.vo.EasyUiDataGridResult;

public interface NlctemplateService {
	
	/**
	 * 文津诵读模板页面
	 */
	public EasyUiDataGridResult getTemplateList(Integer page, Integer rows);

	/**
	 * 根据id删除模板
	 * @param id
	 * @return
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 插入模板
	 */
	public void insertTemplate(Nlctemplate nlctemplate);
	
	/**
	 * ftp上传图片
	 */
	public String uploadPicture(MultipartFile file);
	
	/**
	 * 通过主键查询
	 */
	public Nlctemplate selectByPrimaryKey(Integer id);
	
	/**
	 * 更新模板
	 */
	public void updateTemplate(Nlctemplate nlctemplate);
	
	/**
	 * 模板的发布与取消
	 * @param id
	 * @param status
	 */
	public void publish(Integer id, String status);
	
	public List<Nlctemplate> getNlctemplate();
}
