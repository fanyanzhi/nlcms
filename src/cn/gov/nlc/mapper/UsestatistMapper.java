package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Usestatist;
import cn.gov.nlc.pojo.UsestatistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsestatistMapper {
    int countByExample(UsestatistExample example);

    int deleteByExample(UsestatistExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Usestatist record);

    int insertSelective(Usestatist record);

    List<Usestatist> selectByExample(UsestatistExample example);

    Usestatist selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Usestatist record, @Param("example") UsestatistExample example);

    int updateByExample(@Param("record") Usestatist record, @Param("example") UsestatistExample example);

    int updateByPrimaryKeySelective(Usestatist record);

    int updateByPrimaryKey(Usestatist record);
}