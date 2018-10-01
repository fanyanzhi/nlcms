package cn.gov.nlc.mapper;

import cn.gov.nlc.pojo.Userinfoqrcode;
import cn.gov.nlc.pojo.UserinfoqrcodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserinfoqrcodeMapper {
    int countByExample(UserinfoqrcodeExample example);

    int deleteByExample(UserinfoqrcodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Userinfoqrcode record);

    int insertSelective(Userinfoqrcode record);

    List<Userinfoqrcode> selectByExample(UserinfoqrcodeExample example);

    Userinfoqrcode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Userinfoqrcode record, @Param("example") UserinfoqrcodeExample example);

    int updateByExample(@Param("record") Userinfoqrcode record, @Param("example") UserinfoqrcodeExample example);

    int updateByPrimaryKeySelective(Userinfoqrcode record);

    int updateByPrimaryKey(Userinfoqrcode record);
}