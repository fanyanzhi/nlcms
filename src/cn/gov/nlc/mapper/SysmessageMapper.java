package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Sysmessage;
import cn.gov.nlc.pojo.SysmessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysmessageMapper {
    int countByExample(SysmessageExample example);

    int deleteByExample(SysmessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sysmessage record);

    int insertSelective(Sysmessage record);

    List<Sysmessage> selectByExample(SysmessageExample example);

    Sysmessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sysmessage record, @Param("example") SysmessageExample example);

    int updateByExample(@Param("record") Sysmessage record, @Param("example") SysmessageExample example);

    int updateByPrimaryKeySelective(Sysmessage record);

    int updateByPrimaryKey(Sysmessage record);
}