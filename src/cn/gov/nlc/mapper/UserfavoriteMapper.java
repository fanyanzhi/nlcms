package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Userfavorite;
import cn.gov.nlc.pojo.UserfavoriteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserfavoriteMapper {
    int countByExample(UserfavoriteExample example);

    int deleteByExample(UserfavoriteExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Userfavorite record);

    int insertSelective(Userfavorite record);

    List<Userfavorite> selectByExample(UserfavoriteExample example);

    Userfavorite selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Userfavorite record, @Param("example") UserfavoriteExample example);

    int updateByExample(@Param("record") Userfavorite record, @Param("example") UserfavoriteExample example);

    int updateByPrimaryKeySelective(Userfavorite record);

    int updateByPrimaryKey(Userfavorite record);
}