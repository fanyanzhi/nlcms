package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlctrailerpraise;
import cn.gov.nlc.pojo.NlctrailerpraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlctrailerpraiseMapper {
    int countByExample(NlctrailerpraiseExample example);

    int deleteByExample(NlctrailerpraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlctrailerpraise record);

    int insertSelective(Nlctrailerpraise record);

    List<Nlctrailerpraise> selectByExample(NlctrailerpraiseExample example);

    Nlctrailerpraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlctrailerpraise record, @Param("example") NlctrailerpraiseExample example);

    int updateByExample(@Param("record") Nlctrailerpraise record, @Param("example") NlctrailerpraiseExample example);

    int updateByPrimaryKeySelective(Nlctrailerpraise record);

    int updateByPrimaryKey(Nlctrailerpraise record);
}