package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Downlistenbook;
import cn.gov.nlc.pojo.DownlistenbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DownlistenbookMapper {
    int countByExample(DownlistenbookExample example);

    int deleteByExample(DownlistenbookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Downlistenbook record);

    int insertSelective(Downlistenbook record);

    List<Downlistenbook> selectByExample(DownlistenbookExample example);

    Downlistenbook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Downlistenbook record, @Param("example") DownlistenbookExample example);

    int updateByExample(@Param("record") Downlistenbook record, @Param("example") DownlistenbookExample example);

    int updateByPrimaryKeySelective(Downlistenbook record);

    int updateByPrimaryKey(Downlistenbook record);
}