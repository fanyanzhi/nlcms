package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Faq;
import cn.gov.nlc.pojo.FaqExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FaqMapper {
    int countByExample(FaqExample example);

    int deleteByExample(FaqExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Faq record);

    int insertSelective(Faq record);

    List<Faq> selectByExample(FaqExample example);

    Faq selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Faq record, @Param("example") FaqExample example);

    int updateByExample(@Param("record") Faq record, @Param("example") FaqExample example);

    int updateByPrimaryKeySelective(Faq record);

    int updateByPrimaryKey(Faq record);
}