package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Sysmessageflag;
import cn.gov.nlc.pojo.SysmessageflagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysmessageflagMapper {
    int countByExample(SysmessageflagExample example);

    int deleteByExample(SysmessageflagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sysmessageflag record);

    int insertSelective(Sysmessageflag record);

    List<Sysmessageflag> selectByExample(SysmessageflagExample example);

    Sysmessageflag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sysmessageflag record, @Param("example") SysmessageflagExample example);

    int updateByExample(@Param("record") Sysmessageflag record, @Param("example") SysmessageflagExample example);

    int updateByPrimaryKeySelective(Sysmessageflag record);

    int updateByPrimaryKey(Sysmessageflag record);
}