package vip.testops.account.mappers;

import org.apache.ibatis.annotations.*;
import vip.testops.account.entities.dto.UserDTO;

import java.util.Date;

@Mapper
public interface UserMapper {
    @Insert("insert into t_account values(null, #{accountName}, #{email}, #{password}, #{createTime}, #{lastLoginTime})")
    @SelectKey(statement = "select last_insert_id()", keyProperty = "accountId", before = false, resultType = long.class)
    int addUser(UserDTO user);

    @Select("select * from t_account where accountName=#{accountName}")
    UserDTO getUserByName(String accountName);

    @Update("update t_account set lastLoginTime=#{lastLoginTime} where accountId=#{accountId}")
    int updateLastLoginTime(Date lastLoginTime, long accountId);
}
