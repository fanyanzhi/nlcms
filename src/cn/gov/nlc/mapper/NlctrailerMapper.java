package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlctrailer;
import cn.gov.nlc.pojo.NlctrailerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlctrailerMapper {
    int countByExample(NlctrailerExample example);

    int deleteByExample(NlctrailerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlctrailer record);

    int insertSelective(Nlctrailer record);

    List<Nlctrailer> selectByExample(NlctrailerExample example);

    Nlctrailer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlctrailer record, @Param("example") NlctrailerExample example);

    int updateByExample(@Param("record") Nlctrailer record, @Param("example") NlctrailerExample example);

    int updateByPrimaryKeySelective(Nlctrailer record);

    int updateByPrimaryKey(Nlctrailer record);
}