package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Syswindow;
import cn.gov.nlc.pojo.SyswindowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyswindowMapper {
    int countByExample(SyswindowExample example);

    int deleteByExample(SyswindowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Syswindow record);

    int insertSelective(Syswindow record);

    List<Syswindow> selectByExample(SyswindowExample example);

    Syswindow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Syswindow record, @Param("example") SyswindowExample example);

    int updateByExample(@Param("record") Syswindow record, @Param("example") SyswindowExample example);

    int updateByPrimaryKeySelective(Syswindow record);

    int updateByPrimaryKey(Syswindow record);
}