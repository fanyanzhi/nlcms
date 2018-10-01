package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Delayorderinfodetail;
import cn.gov.nlc.pojo.DelayorderinfodetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DelayorderinfodetailMapper {
    int countByExample(DelayorderinfodetailExample example);

    int deleteByExample(DelayorderinfodetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Delayorderinfodetail record);

    int insertSelective(Delayorderinfodetail record);

    List<Delayorderinfodetail> selectByExample(DelayorderinfodetailExample example);

    Delayorderinfodetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Delayorderinfodetail record, @Param("example") DelayorderinfodetailExample example);

    int updateByExample(@Param("record") Delayorderinfodetail record, @Param("example") DelayorderinfodetailExample example);

    int updateByPrimaryKeySelective(Delayorderinfodetail record);

    int updateByPrimaryKey(Delayorderinfodetail record);
}