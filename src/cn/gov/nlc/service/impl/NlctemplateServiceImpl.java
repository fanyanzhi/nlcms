package cn.gov.nlc.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.gov.nlc.mapper.NlctemplateMapper;
import cn.gov.nlc.pojo.Nlctemplate;
import cn.gov.nlc.pojo.NlctemplateExample;
import cn.gov.nlc.pojo.NlctemplateExample.Criteria;
import cn.gov.nlc.service.NlctemplateService;
import cn.gov.nlc.util.Common;
import cn.gov.nlc.util.FtpUtil;
import cn.gov.nlc.util.UUIDGenerator;
import cn.gov.nlc.vo.EasyUiDataGridResult;

@Service
public class NlctemplateServiceImpl implements NlctemplateService {
	
	private static final Logger logger = Logger.getLogger(cn.gov.nlc.service.impl.NlctemplateServiceImpl.class);

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USER_NAME}")
	private String FTP_USER_NAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_IMG_BASE_PATH}")
	private String FTP_IMG_BASE_PATH;

	@Autowired
	private NlctemplateMapper nlctemplateMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 文津诵读模板页面
	 */
	@Override
	public EasyUiDataGridResult getTemplateList(Integer page, Integer rows) {
		// 分页
		PageHelper.startPage(page, rows);
		NlctemplateExample example = new NlctemplateExample();
		List<Nlctemplate> list = nlctemplateMapper.selectByExample(example);
		PageInfo<Nlctemplate> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUiDataGridResult result = new EasyUiDataGridResult(total, list);
		return result;
	}

	/**
	 * 根据id删除模板
	 */
	@Override
	public int deleteSingleById(Integer id) {
		int result = nlctemplateMapper.deleteByPrimaryKey(id);
		return result;
	}

	/**
	 * 插入模板
	 */
	@Override
	public void insertTemplate(Nlctemplate nlctemplate) {
		Byte isdefault = nlctemplate.getIsdefault();
		if(1 == isdefault) {
			String sql = "update nlctemplate set isdefault = 0 ";
			jdbcTemplate.execute(sql);
		}
		nlctemplateMapper.insert(nlctemplate);
	}
	
	/**
	 * ftp上传图片
	 */
	@Override
	public String uploadPicture(MultipartFile file) {
		String originalUpFilename = file.getOriginalFilename();
		if (originalUpFilename != null && !originalUpFilename.equals("")) {
			String newUpFilename = UUIDGenerator.getUUID()
					+ originalUpFilename.substring(originalUpFilename.lastIndexOf("."));
			boolean resBoolean = false;
			try {
				resBoolean = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME, FTP_PASSWORD, FTP_IMG_BASE_PATH, "",
						newUpFilename, file.getInputStream());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			}
			if(resBoolean) {
				return newUpFilename;
			}
		}

		return null;
	}
	
	public void getFtpFile(String newUpFilename){
		
	}

	/**
	 * 通过主键查询
	 */
	@Override
	public Nlctemplate selectByPrimaryKey(Integer id) {
		Nlctemplate nlctemplate = nlctemplateMapper.selectByPrimaryKey(id);
		return nlctemplate;
	}

	/**
	 * 更新模板
	 */
	@Override
	public void updateTemplate(Nlctemplate nlctemplate) {
		Byte isdefault = nlctemplate.getIsdefault();
		if(1 == isdefault) {
			String sql = "update nlctemplate set isdefault = 0 ";
			jdbcTemplate.execute(sql);
		}
		nlctemplateMapper.updateByPrimaryKeySelective(nlctemplate);
	}

	/**
	 * 文档的发布与取消
	 */
	@Override
	public void publish(Integer id, String status) {
		Nlctemplate nlctemplate = nlctemplateMapper.selectByPrimaryKey(id);
		nlctemplate.setStatus(status);
		nlctemplateMapper.updateByPrimaryKey(nlctemplate);
	}

	@Override
	public List<Nlctemplate> getNlctemplate(){
		NlctemplateExample example = new NlctemplateExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");
		criteria.andStarttimeLessThanOrEqualTo(Common.ConvertToDate(Common.GetDateTime(), "yyyy-MM-dd"));
		criteria.andEndtimeGreaterThanOrEqualTo(Common.ConvertToDate(Common.GetDateTime(), "yyyy-MM-dd"));
		example.setOrderByClause("time asc");
		List<Nlctemplate> list = nlctemplateMapper.selectByExample(example);
		return list;
	}
	
}
