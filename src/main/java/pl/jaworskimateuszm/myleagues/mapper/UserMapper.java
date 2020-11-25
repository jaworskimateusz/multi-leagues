package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (username, password, enabled) " +
            "VALUES(#{username}, #{password}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("SELECT username, password, enabled, role FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT username, password, enabled, role FROM users")
    List<User> findAll();
}
