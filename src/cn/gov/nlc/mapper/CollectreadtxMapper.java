package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Collectreadtx;
import cn.gov.nlc.pojo.CollectreadtxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectreadtxMapper {
    int countByExample(CollectreadtxExample example);

    int deleteByExample(CollectreadtxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collectreadtx record);

    int insertSelective(Collectreadtx record);

    List<Collectreadtx> selectByExample(CollectreadtxExample example);

    Collectreadtx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collectreadtx record, @Param("example") CollectreadtxExample example);

    int updateByExample(@Param("record") Collectreadtx record, @Param("example") CollectreadtxExample example);

    int updateByPrimaryKeySelective(Collectreadtx record);

    int updateByPrimaryKey(Collectreadtx record);
}