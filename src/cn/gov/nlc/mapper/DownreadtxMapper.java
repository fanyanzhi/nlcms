package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Downreadtx;
import cn.gov.nlc.pojo.DownreadtxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DownreadtxMapper {
    int countByExample(DownreadtxExample example);

    int deleteByExample(DownreadtxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Downreadtx record);

    int insertSelective(Downreadtx record);

    List<Downreadtx> selectByExample(DownreadtxExample example);

    Downreadtx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Downreadtx record, @Param("example") DownreadtxExample example);

    int updateByExample(@Param("record") Downreadtx record, @Param("example") DownreadtxExample example);

    int updateByPrimaryKeySelective(Downreadtx record);

    int updateByPrimaryKey(Downreadtx record);
}