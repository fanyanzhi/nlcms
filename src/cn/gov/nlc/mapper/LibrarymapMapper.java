package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Librarymap;
import cn.gov.nlc.pojo.LibrarymapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LibrarymapMapper {
    int countByExample(LibrarymapExample example);

    int deleteByExample(LibrarymapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Librarymap record);

    int insertSelective(Librarymap record);

    List<Librarymap> selectByExample(LibrarymapExample example);

    Librarymap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Librarymap record, @Param("example") LibrarymapExample example);

    int updateByExample(@Param("record") Librarymap record, @Param("example") LibrarymapExample example);

    int updateByPrimaryKeySelective(Librarymap record);

    int updateByPrimaryKey(Librarymap record);
}