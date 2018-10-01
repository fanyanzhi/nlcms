package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Usernormalword;
import cn.gov.nlc.pojo.UsernormalwordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsernormalwordMapper {
    int countByExample(UsernormalwordExample example);

    int deleteByExample(UsernormalwordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Usernormalword record);

    int insertSelective(Usernormalword record);

    List<Usernormalword> selectByExample(UsernormalwordExample example);

    Usernormalword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Usernormalword record, @Param("example") UsernormalwordExample example);

    int updateByExample(@Param("record") Usernormalword record, @Param("example") UsernormalwordExample example);

    int updateByPrimaryKeySelective(Usernormalword record);

    int updateByPrimaryKey(Usernormalword record);
}