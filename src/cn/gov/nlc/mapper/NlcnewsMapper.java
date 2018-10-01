package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnews;
import cn.gov.nlc.pojo.NlcnewsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnewsMapper {
    int countByExample(NlcnewsExample example);

    int deleteByExample(NlcnewsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnews record);

    int insertSelective(Nlcnews record);

    List<Nlcnews> selectByExampleWithBLOBs(NlcnewsExample example);

    List<Nlcnews> selectByExample(NlcnewsExample example);

    Nlcnews selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnews record, @Param("example") NlcnewsExample example);

    int updateByExampleWithBLOBs(@Param("record") Nlcnews record, @Param("example") NlcnewsExample example);

    int updateByExample(@Param("record") Nlcnews record, @Param("example") NlcnewsExample example);

    int updateByPrimaryKeySelective(Nlcnews record);

    int updateByPrimaryKeyWithBLOBs(Nlcnews record);

    int updateByPrimaryKey(Nlcnews record);
}