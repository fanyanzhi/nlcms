package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Readercompasscatalog;
import cn.gov.nlc.pojo.ReadercompasscatalogExample;
import cn.gov.nlc.pojo.ReadercompasscatalogWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReadercompasscatalogMapper {
    int countByExample(ReadercompasscatalogExample example);

    int deleteByExample(ReadercompasscatalogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReadercompasscatalogWithBLOBs record);

    int insertSelective(ReadercompasscatalogWithBLOBs record);

    List<ReadercompasscatalogWithBLOBs> selectByExampleWithBLOBs(ReadercompasscatalogExample example);

    List<Readercompasscatalog> selectByExample(ReadercompasscatalogExample example);

    ReadercompasscatalogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReadercompasscatalogWithBLOBs record, @Param("example") ReadercompasscatalogExample example);

    int updateByExampleWithBLOBs(@Param("record") ReadercompasscatalogWithBLOBs record, @Param("example") ReadercompasscatalogExample example);

    int updateByExample(@Param("record") Readercompasscatalog record, @Param("example") ReadercompasscatalogExample example);

    int updateByPrimaryKeySelective(ReadercompasscatalogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ReadercompasscatalogWithBLOBs record);

    int updateByPrimaryKey(Readercompasscatalog record);
}