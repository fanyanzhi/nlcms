package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnoticepraise;
import cn.gov.nlc.pojo.NlcnoticepraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnoticepraiseMapper {
    int countByExample(NlcnoticepraiseExample example);

    int deleteByExample(NlcnoticepraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnoticepraise record);

    int insertSelective(Nlcnoticepraise record);

    List<Nlcnoticepraise> selectByExample(NlcnoticepraiseExample example);

    Nlcnoticepraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnoticepraise record, @Param("example") NlcnoticepraiseExample example);

    int updateByExample(@Param("record") Nlcnoticepraise record, @Param("example") NlcnoticepraiseExample example);

    int updateByPrimaryKeySelective(Nlcnoticepraise record);

    int updateByPrimaryKey(Nlcnoticepraise record);
}