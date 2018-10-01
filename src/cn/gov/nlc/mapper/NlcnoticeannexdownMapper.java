package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnoticeannexdown;
import cn.gov.nlc.pojo.NlcnoticeannexdownExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnoticeannexdownMapper {
    int countByExample(NlcnoticeannexdownExample example);

    int deleteByExample(NlcnoticeannexdownExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnoticeannexdown record);

    int insertSelective(Nlcnoticeannexdown record);

    List<Nlcnoticeannexdown> selectByExample(NlcnoticeannexdownExample example);

    Nlcnoticeannexdown selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnoticeannexdown record, @Param("example") NlcnoticeannexdownExample example);

    int updateByExample(@Param("record") Nlcnoticeannexdown record, @Param("example") NlcnoticeannexdownExample example);

    int updateByPrimaryKeySelective(Nlcnoticeannexdown record);

    int updateByPrimaryKey(Nlcnoticeannexdown record);
}