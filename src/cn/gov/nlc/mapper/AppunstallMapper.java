package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Appunstall;
import cn.gov.nlc.pojo.AppunstallExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppunstallMapper {
    int countByExample(AppunstallExample example);

    int deleteByExample(AppunstallExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appunstall record);

    int insertSelective(Appunstall record);

    List<Appunstall> selectByExample(AppunstallExample example);

    Appunstall selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appunstall record, @Param("example") AppunstallExample example);

    int updateByExample(@Param("record") Appunstall record, @Param("example") AppunstallExample example);

    int updateByPrimaryKeySelective(Appunstall record);

    int updateByPrimaryKey(Appunstall record);
}