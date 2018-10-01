package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Holdrecord;
import cn.gov.nlc.pojo.HoldrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HoldrecordMapper {
    int countByExample(HoldrecordExample example);

    int deleteByExample(HoldrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Holdrecord record);

    int insertSelective(Holdrecord record);

    List<Holdrecord> selectByExample(HoldrecordExample example);

    Holdrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Holdrecord record, @Param("example") HoldrecordExample example);

    int updateByExample(@Param("record") Holdrecord record, @Param("example") HoldrecordExample example);

    int updateByPrimaryKeySelective(Holdrecord record);

    int updateByPrimaryKey(Holdrecord record);
}