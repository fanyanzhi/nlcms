package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Subindexnum;
import cn.gov.nlc.pojo.SubindexnumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubindexnumMapper {
    int countByExample(SubindexnumExample example);

    int deleteByExample(SubindexnumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Subindexnum record);

    int insertSelective(Subindexnum record);

    List<Subindexnum> selectByExample(SubindexnumExample example);

    Subindexnum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Subindexnum record, @Param("example") SubindexnumExample example);

    int updateByExample(@Param("record") Subindexnum record, @Param("example") SubindexnumExample example);

    int updateByPrimaryKeySelective(Subindexnum record);

    int updateByPrimaryKey(Subindexnum record);
}