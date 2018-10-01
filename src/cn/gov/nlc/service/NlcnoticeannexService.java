package cn.gov.nlc.service;

import java.util.List;

import cn.gov.nlc.pojo.Nlcnoticeannex;

public interface NlcnoticeannexService {
	/**
	 * 删除公告，单个删除
	 */
	public int deleteSingleById(Integer id);

	/**
	 * 
	 * @param noticeId
	 * @return
	 */
	public int deleteByNoteceId(String noticeId);

	/**
	 * 添加公告
	 */
	public void insertNoticeAnnex(Nlcnoticeannex nlcnoticeannex);

	/**
	 * 通过主键查询
	 */
	public List<Nlcnoticeannex> selectByNoticeId(String noticeId);

}
