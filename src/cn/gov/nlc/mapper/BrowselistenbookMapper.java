package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Browselistenbook;
import cn.gov.nlc.pojo.BrowselistenbookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrowselistenbookMapper {
    int countByExample(BrowselistenbookExample example);

    int deleteByExample(BrowselistenbookExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Browselistenbook record);

    int insertSelective(Browselistenbook record);

    List<Browselistenbook> selectByExample(BrowselistenbookExample example);

    Browselistenbook selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Browselistenbook record, @Param("example") BrowselistenbookExample example);

    int updateByExample(@Param("record") Browselistenbook record, @Param("example") BrowselistenbookExample example);

    int updateByPrimaryKeySelective(Browselistenbook record);

    int updateByPrimaryKey(Browselistenbook record);
}