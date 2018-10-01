package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Readtx;
import cn.gov.nlc.pojo.ReadtxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReadtxMapper {
    int countByExample(ReadtxExample example);

    int deleteByExample(ReadtxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Readtx record);

    int insertSelective(Readtx record);

    List<Readtx> selectByExample(ReadtxExample example);

    Readtx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Readtx record, @Param("example") ReadtxExample example);

    int updateByExample(@Param("record") Readtx record, @Param("example") ReadtxExample example);

    int updateByPrimaryKeySelective(Readtx record);

    int updateByPrimaryKey(Readtx record);
}