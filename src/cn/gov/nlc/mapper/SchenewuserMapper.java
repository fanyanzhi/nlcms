package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Schenewuser;
import cn.gov.nlc.pojo.SchenewuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchenewuserMapper {
    int countByExample(SchenewuserExample example);

    int deleteByExample(SchenewuserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Schenewuser record);

    int insertSelective(Schenewuser record);

    List<Schenewuser> selectByExample(SchenewuserExample example);

    Schenewuser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Schenewuser record, @Param("example") SchenewuserExample example);

    int updateByExample(@Param("record") Schenewuser record, @Param("example") SchenewuserExample example);

    int updateByPrimaryKeySelective(Schenewuser record);

    int updateByPrimaryKey(Schenewuser record);
}