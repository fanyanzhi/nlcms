package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcsuggestion;
import cn.gov.nlc.pojo.NlcsuggestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcsuggestionMapper {
    int countByExample(NlcsuggestionExample example);

    int deleteByExample(NlcsuggestionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcsuggestion record);

    int insertSelective(Nlcsuggestion record);

    List<Nlcsuggestion> selectByExample(NlcsuggestionExample example);

    Nlcsuggestion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcsuggestion record, @Param("example") NlcsuggestionExample example);

    int updateByExample(@Param("record") Nlcsuggestion record, @Param("example") NlcsuggestionExample example);

    int updateByPrimaryKeySelective(Nlcsuggestion record);

    int updateByPrimaryKey(Nlcsuggestion record);
}