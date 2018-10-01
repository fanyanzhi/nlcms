package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcuseronline;
import cn.gov.nlc.pojo.NlcuseronlineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcuseronlineMapper {
    int countByExample(NlcuseronlineExample example);

    int deleteByExample(NlcuseronlineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Nlcuseronline record);

    int insertSelective(Nlcuseronline record);

    List<Nlcuseronline> selectByExample(NlcuseronlineExample example);

    Nlcuseronline selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Nlcuseronline record, @Param("example") NlcuseronlineExample example);

    int updateByExample(@Param("record") Nlcuseronline record, @Param("example") NlcuseronlineExample example);

    int updateByPrimaryKeySelective(Nlcuseronline record);

    int updateByPrimaryKey(Nlcuseronline record);
}