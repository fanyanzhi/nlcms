package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Downloadrecord;
import cn.gov.nlc.pojo.DownloadrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DownloadrecordMapper {
    int countByExample(DownloadrecordExample example);

    int deleteByExample(DownloadrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Downloadrecord record);

    int insertSelective(Downloadrecord record);

    List<Downloadrecord> selectByExample(DownloadrecordExample example);

    Downloadrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Downloadrecord record, @Param("example") DownloadrecordExample example);

    int updateByExample(@Param("record") Downloadrecord record, @Param("example") DownloadrecordExample example);

    int updateByPrimaryKeySelective(Downloadrecord record);

    int updateByPrimaryKey(Downloadrecord record);
}