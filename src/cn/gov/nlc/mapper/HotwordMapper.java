package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Hotword;
import cn.gov.nlc.pojo.HotwordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HotwordMapper {
    int countByExample(HotwordExample example);

    int deleteByExample(HotwordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hotword record);

    int insertSelective(Hotword record);

    List<Hotword> selectByExample(HotwordExample example);

    Hotword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hotword record, @Param("example") HotwordExample example);

    int updateByExample(@Param("record") Hotword record, @Param("example") HotwordExample example);

    int updateByPrimaryKeySelective(Hotword record);

    int updateByPrimaryKey(Hotword record);
}