package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Appinstall;
import cn.gov.nlc.pojo.AppinstallExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppinstallMapper {
    int countByExample(AppinstallExample example);

    int deleteByExample(AppinstallExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Appinstall record);

    int insertSelective(Appinstall record);

    List<Appinstall> selectByExample(AppinstallExample example);

    Appinstall selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Appinstall record, @Param("example") AppinstallExample example);

    int updateByExample(@Param("record") Appinstall record, @Param("example") AppinstallExample example);

    int updateByPrimaryKeySelective(Appinstall record);

    int updateByPrimaryKey(Appinstall record);
}