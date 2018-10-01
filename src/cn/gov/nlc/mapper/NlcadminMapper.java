package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcadmin;
import cn.gov.nlc.pojo.NlcadminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcadminMapper {
    int countByExample(NlcadminExample example);

    int deleteByExample(NlcadminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcadmin record);

    int insertSelective(Nlcadmin record);

    List<Nlcadmin> selectByExample(NlcadminExample example);

    Nlcadmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcadmin record, @Param("example") NlcadminExample example);

    int updateByExample(@Param("record") Nlcadmin record, @Param("example") NlcadminExample example);

    int updateByPrimaryKeySelective(Nlcadmin record);

    int updateByPrimaryKey(Nlcadmin record);
}