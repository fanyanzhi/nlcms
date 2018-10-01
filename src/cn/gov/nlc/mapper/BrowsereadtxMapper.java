package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Browsereadtx;
import cn.gov.nlc.pojo.BrowsereadtxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrowsereadtxMapper {
    int countByExample(BrowsereadtxExample example);

    int deleteByExample(BrowsereadtxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Browsereadtx record);

    int insertSelective(Browsereadtx record);

    List<Browsereadtx> selectByExample(BrowsereadtxExample example);

    Browsereadtx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Browsereadtx record, @Param("example") BrowsereadtxExample example);

    int updateByExample(@Param("record") Browsereadtx record, @Param("example") BrowsereadtxExample example);

    int updateByPrimaryKeySelective(Browsereadtx record);

    int updateByPrimaryKey(Browsereadtx record);
}