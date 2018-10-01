package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcsubject;
import cn.gov.nlc.pojo.NlcsubjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcsubjectMapper {
    int countByExample(NlcsubjectExample example);

    int deleteByExample(NlcsubjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcsubject record);

    int insertSelective(Nlcsubject record);

    List<Nlcsubject> selectByExampleWithBLOBs(NlcsubjectExample example);

    List<Nlcsubject> selectByExample(NlcsubjectExample example);

    Nlcsubject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcsubject record, @Param("example") NlcsubjectExample example);

    int updateByExampleWithBLOBs(@Param("record") Nlcsubject record, @Param("example") NlcsubjectExample example);

    int updateByExample(@Param("record") Nlcsubject record, @Param("example") NlcsubjectExample example);

    int updateByPrimaryKeySelective(Nlcsubject record);

    int updateByPrimaryKeyWithBLOBs(Nlcsubject record);

    int updateByPrimaryKey(Nlcsubject record);
}