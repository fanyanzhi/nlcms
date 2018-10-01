package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Collectlistenbook;
import cn.gov.nlc.pojo.CollectlistenbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectlistenbookMapper {
    int countByExample(CollectlistenbookExample example);

    int deleteByExample(CollectlistenbookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collectlistenbook record);

    int insertSelective(Collectlistenbook record);

    List<Collectlistenbook> selectByExample(CollectlistenbookExample example);

    Collectlistenbook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collectlistenbook record, @Param("example") CollectlistenbookExample example);

    int updateByExample(@Param("record") Collectlistenbook record, @Param("example") CollectlistenbookExample example);

    int updateByPrimaryKeySelective(Collectlistenbook record);

    int updateByPrimaryKey(Collectlistenbook record);
}