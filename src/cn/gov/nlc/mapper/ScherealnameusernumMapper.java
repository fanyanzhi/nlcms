package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Scherealnameusernum;
import cn.gov.nlc.pojo.ScherealnameusernumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScherealnameusernumMapper {
    int countByExample(ScherealnameusernumExample example);

    int deleteByExample(ScherealnameusernumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Scherealnameusernum record);

    int insertSelective(Scherealnameusernum record);

    List<Scherealnameusernum> selectByExample(ScherealnameusernumExample example);

    Scherealnameusernum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Scherealnameusernum record, @Param("example") ScherealnameusernumExample example);

    int updateByExample(@Param("record") Scherealnameusernum record, @Param("example") ScherealnameusernumExample example);

    int updateByPrimaryKeySelective(Scherealnameusernum record);

    int updateByPrimaryKey(Scherealnameusernum record);
}