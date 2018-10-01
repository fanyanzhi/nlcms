package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcuserloginlog;
import cn.gov.nlc.pojo.NlcuserloginlogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcuserloginlogMapper {
    int countByExample(NlcuserloginlogExample example);

    int deleteByExample(NlcuserloginlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Nlcuserloginlog record);

    int insertSelective(Nlcuserloginlog record);

    List<Nlcuserloginlog> selectByExample(NlcuserloginlogExample example);

    Nlcuserloginlog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Nlcuserloginlog record, @Param("example") NlcuserloginlogExample example);

    int updateByExample(@Param("record") Nlcuserloginlog record, @Param("example") NlcuserloginlogExample example);

    int updateByPrimaryKeySelective(Nlcuserloginlog record);

    int updateByPrimaryKey(Nlcuserloginlog record);
}