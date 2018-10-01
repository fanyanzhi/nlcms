package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcsubjectcatalog;
import cn.gov.nlc.pojo.NlcsubjectcatalogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcsubjectcatalogMapper {
    int countByExample(NlcsubjectcatalogExample example);

    int deleteByExample(NlcsubjectcatalogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcsubjectcatalog record);

    int insertSelective(Nlcsubjectcatalog record);

    List<Nlcsubjectcatalog> selectByExampleWithBLOBs(NlcsubjectcatalogExample example);

    List<Nlcsubjectcatalog> selectByExample(NlcsubjectcatalogExample example);

    Nlcsubjectcatalog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcsubjectcatalog record, @Param("example") NlcsubjectcatalogExample example);

    int updateByExampleWithBLOBs(@Param("record") Nlcsubjectcatalog record, @Param("example") NlcsubjectcatalogExample example);

    int updateByExample(@Param("record") Nlcsubjectcatalog record, @Param("example") NlcsubjectcatalogExample example);

    int updateByPrimaryKeySelective(Nlcsubjectcatalog record);

    int updateByPrimaryKeyWithBLOBs(Nlcsubjectcatalog record);

    int updateByPrimaryKey(Nlcsubjectcatalog record);
}