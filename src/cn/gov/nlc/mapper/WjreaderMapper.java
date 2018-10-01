package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Wjreader;
import cn.gov.nlc.pojo.WjreaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WjreaderMapper {
    int countByExample(WjreaderExample example);

    int deleteByExample(WjreaderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Wjreader record);

    int insertSelective(Wjreader record);

    List<Wjreader> selectByExample(WjreaderExample example);

    Wjreader selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Wjreader record, @Param("example") WjreaderExample example);

    int updateByExample(@Param("record") Wjreader record, @Param("example") WjreaderExample example);

    int updateByPrimaryKeySelective(Wjreader record);

    int updateByPrimaryKey(Wjreader record);
}