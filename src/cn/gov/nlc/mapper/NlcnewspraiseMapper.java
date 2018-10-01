package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Nlcnewspraise;
import cn.gov.nlc.pojo.NlcnewspraiseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NlcnewspraiseMapper {
    int countByExample(NlcnewspraiseExample example);

    int deleteByExample(NlcnewspraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Nlcnewspraise record);

    int insertSelective(Nlcnewspraise record);

    List<Nlcnewspraise> selectByExample(NlcnewspraiseExample example);

    Nlcnewspraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Nlcnewspraise record, @Param("example") NlcnewspraiseExample example);

    int updateByExample(@Param("record") Nlcnewspraise record, @Param("example") NlcnewspraiseExample example);

    int updateByPrimaryKeySelective(Nlcnewspraise record);

    int updateByPrimaryKey(Nlcnewspraise record);
}