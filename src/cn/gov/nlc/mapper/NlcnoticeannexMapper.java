package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnoticeannex;
import cn.gov.nlc.pojo.NlcnoticeannexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnoticeannexMapper {
    int countByExample(NlcnoticeannexExample example);

    int deleteByExample(NlcnoticeannexExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnoticeannex record);

    int insertSelective(Nlcnoticeannex record);

    List<Nlcnoticeannex> selectByExample(NlcnoticeannexExample example);

    Nlcnoticeannex selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnoticeannex record, @Param("example") NlcnoticeannexExample example);

    int updateByExample(@Param("record") Nlcnoticeannex record, @Param("example") NlcnoticeannexExample example);

    int updateByPrimaryKeySelective(Nlcnoticeannex record);

    int updateByPrimaryKey(Nlcnoticeannex record);
}