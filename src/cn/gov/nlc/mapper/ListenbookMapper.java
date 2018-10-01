package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Listenbook;
import cn.gov.nlc.pojo.ListenbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ListenbookMapper {
    int countByExample(ListenbookExample example);

    int deleteByExample(ListenbookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Listenbook record);

    int insertSelective(Listenbook record);

    List<Listenbook> selectByExample(ListenbookExample example);

    Listenbook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Listenbook record, @Param("example") ListenbookExample example);

    int updateByExample(@Param("record") Listenbook record, @Param("example") ListenbookExample example);

    int updateByPrimaryKeySelective(Listenbook record);

    int updateByPrimaryKey(Listenbook record);
}