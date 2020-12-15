package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.PlayerDetail;
import pl.jaworskimateuszm.myleagues.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (username, password, enabled, role, imie, nazwisko, pesel, numer_telefonu) " +
            "VALUES (#{username}, #{password}, #{enabled}, #{role}, #{name}, #{surname}, #{pesel}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    void insert(User user);

    @Select("SELECT user_id, username, password, enabled, role, imie, nazwisko, pesel, numer_telefonu" +
            " FROM users WHERE username = #{username}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    User findByUsername(String username);

    @Select("SELECT user_id, username, password, enabled, role, imie, nazwisko, pesel, numer_telefonu" +
            " FROM users WHERE user_id=#{id}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    User findById(long id);


    @Select("SELECT user_id, username, password, enabled, role, imie, nazwisko, pesel, numer_telefonu" +
            " FROM users WHERE role=#{role}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    List<User> findAllByRole(String role);

    @Update("UPDATE users SET username=#{username}, password=#{password}, enabled=#{enabled}, role=#{role}, " +
            "imie=#{name}, nazwisko=#{surname}, pesel=#{pesel}, numer_telefonu=#{phoneNumber} " +
            "WHERE user_id=#{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    void update(User user);

    @Delete("DELETE FROM users WHERE user_id = #{id}")
    int deleteById(int id);

    @Delete("DELETE FROM authorities WHERE username = #{username}")
    int deleteByUsernameFromAuthorities(String username);

//    @Update("UPDATE zawodnicy_ligi SET user_id=#{userId}, id_ligi=#{leagueId} " +
//            "WHERE user_id=#{userId} AND id_ligi=#{leagueId}")
//    int updatePlayerLeague(long userId, long leagueId);

//    @Insert("INSERT INTO zawodnicy_ligi (user_id, id_ligi) " +
//            " VALUES (#{userId}, #{leagueId})")
//    int insertPlayerLeague(long userId, long leagueId);

    @Select("SELECT user_id, username, password, enabled, role, imie, nazwisko, pesel, numer_telefonu" +
            " FROM users" +
            " WHERE pesel = #{pesel}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "role", column = "role"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "pesel", column = "pesel"),
            @Result(property = "phoneNumber", column = "numer_telefonu")
    })
    List<User> searchBy(String pesel);

    @Select(" SELECT ligi.opis as opis_ligi, sezony.opis as opis_sezonu, kolejki.numer, " +
            "    mecze.id_meczu, mecze.termin, mecze.miejsce\n" +
            "    FROM users\n" +
            "    JOIN zawodnicy_ligi USING (user_id)\n" +
            "    JOIN ligi USING (id_ligi)\n" +
            "    JOIN sezony USING (id_ligi)\n" +
            "    JOIN kolejki USING (id_sezonu)\n" +
            "    JOIN mecze USING (id_kolejki)\n" +
            " WHERE users.user_id = #{playerId}")
    @Results({
            @Result(property = "leagueDescription", column = "opis_ligi"),
            @Result(property = "seasonDescription", column = "opis_sezonu"),
            @Result(property = "roundNumber", column = "numer"),
            @Result(property = "gameId", column = "id_meczu"),
            @Result(property = "gameDate", column = "termin"),
            @Result(property = "place", column = "miejsce")
    })
    List<PlayerDetail> findAllDetailById(int playerId);

    @Delete("DELETE FROM zawodnicy_ligi WHERE user_id = #{id}")
    int deletePlayerLeagueById(long id);

    @Insert("INSERT INTO authorities VALUES (#{username}, #{authority})")
    void insertAuthority(String username, String authority);

}
