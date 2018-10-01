package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcuser;
import cn.gov.nlc.pojo.NlcuserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcuserMapper {
    int countByExample(NlcuserExample example);

    int deleteByExample(NlcuserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcuser record);

    int insertSelective(Nlcuser record);

    List<Nlcuser> selectByExample(NlcuserExample example);

    Nlcuser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcuser record, @Param("example") NlcuserExample example);

    int updateByExample(@Param("record") Nlcuser record, @Param("example") NlcuserExample example);

    int updateByPrimaryKeySelective(Nlcuser record);

    int updateByPrimaryKey(Nlcuser record);
}