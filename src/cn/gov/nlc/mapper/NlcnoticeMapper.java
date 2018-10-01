package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnotice;
import cn.gov.nlc.pojo.NlcnoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnoticeMapper {
    int countByExample(NlcnoticeExample example);

    int deleteByExample(NlcnoticeExample example);

    int deleteByPrimaryKey(String noticeid);

    int insert(Nlcnotice record);

    int insertSelective(Nlcnotice record);

    List<Nlcnotice> selectByExampleWithBLOBs(NlcnoticeExample example);

    List<Nlcnotice> selectByExample(NlcnoticeExample example);

    Nlcnotice selectByPrimaryKey(String noticeid);

    int updateByExampleSelective(@Param("record") Nlcnotice record, @Param("example") NlcnoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") Nlcnotice record, @Param("example") NlcnoticeExample example);

    int updateByExample(@Param("record") Nlcnotice record, @Param("example") NlcnoticeExample example);

    int updateByPrimaryKeySelective(Nlcnotice record);

    int updateByPrimaryKeyWithBLOBs(Nlcnotice record);

    int updateByPrimaryKey(Nlcnotice record);
}