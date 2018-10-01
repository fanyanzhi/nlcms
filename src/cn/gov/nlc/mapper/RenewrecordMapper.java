package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Renewrecord;
import cn.gov.nlc.pojo.RenewrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RenewrecordMapper {
    int countByExample(RenewrecordExample example);

    int deleteByExample(RenewrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Renewrecord record);

    int insertSelective(Renewrecord record);

    List<Renewrecord> selectByExample(RenewrecordExample example);

    Renewrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Renewrecord record, @Param("example") RenewrecordExample example);

    int updateByExample(@Param("record") Renewrecord record, @Param("example") RenewrecordExample example);

    int updateByPrimaryKeySelective(Renewrecord record);

    int updateByPrimaryKey(Renewrecord record);
}