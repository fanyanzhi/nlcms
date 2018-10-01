package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Binduser;
import cn.gov.nlc.pojo.BinduserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BinduserMapper {
    int countByExample(BinduserExample example);

    int deleteByExample(BinduserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Binduser record);

    int insertSelective(Binduser record);

    List<Binduser> selectByExample(BinduserExample example);

    Binduser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Binduser record, @Param("example") BinduserExample example);

    int updateByExample(@Param("record") Binduser record, @Param("example") BinduserExample example);

    int updateByPrimaryKeySelective(Binduser record);

    int updateByPrimaryKey(Binduser record);
}