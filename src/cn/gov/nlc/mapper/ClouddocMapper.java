package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Clouddoc;
import cn.gov.nlc.pojo.ClouddocExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClouddocMapper {
    int countByExample(ClouddocExample example);

    int deleteByExample(ClouddocExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Clouddoc record);

    int insertSelective(Clouddoc record);

    List<Clouddoc> selectByExampleWithBLOBs(ClouddocExample example);

    List<Clouddoc> selectByExample(ClouddocExample example);

    Clouddoc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Clouddoc record, @Param("example") ClouddocExample example);

    int updateByExampleWithBLOBs(@Param("record") Clouddoc record, @Param("example") ClouddocExample example);

    int updateByExample(@Param("record") Clouddoc record, @Param("example") ClouddocExample example);

    int updateByPrimaryKeySelective(Clouddoc record);

    int updateByPrimaryKeyWithBLOBs(Clouddoc record);

    int updateByPrimaryKey(Clouddoc record);
}