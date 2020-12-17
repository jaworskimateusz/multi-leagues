package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.Round;
import pl.jaworskimateuszm.myleagues.model.Season;

import java.util.List;

@Mapper
public interface RoundMapper {

    @Select("SELECT id_kolejki, id_sezonu, numer, dyscyplina FROM kolejki")
    @Results({
            @Result(property = "seasonId", column = "id_sezonu"),
            @Result(property = "roundId", column = "id_kolejki"),
            @Result(property = "number", column = "numer"),
            @Result(property = "discipline", column = "dyscyplina")
    })
    List<Round> findAll();

    @Select("SELECT id_kolejki, id_sezonu, numer, dyscyplina FROM kolejki WHERE numer = #{number}")
    @Results({
            @Result(property = "seasonId", column = "id_sezonu"),
            @Result(property = "roundId", column = "id_kolejki"),
            @Result(property = "number", column = "numer"),
            @Result(property = "discipline", column = "dyscyplina")
    })
    List<Round> findAllByNumber(int number);

    @Select("SELECT id_kolejki, id_sezonu, numer, dyscyplina FROM kolejki WHERE id_kolejki = #{id}")
    @Results({
            @Result(property = "seasonId", column = "id_sezonu"),
            @Result(property = "roundId", column = "id_kolejki"),
            @Result(property = "number", column = "numer"),
            @Result(property = "discipline", column = "dyscyplina")
    })
    Round findById(int id);

    @Select("SELECT id_sezonu, id_ligi, numer, opis FROM sezony")
    @Results({
            @Result(property = "seasonId", column = "id_sezonu"),
            @Result(property = "leagueId", column = "id_ligi"),
            @Result(property = "number", column = "numer"),
            @Result(property = "description", column = "opis")
    })
    List<Season> findAllSeasons();

    @Insert("INSERT INTO kolejki (id_sezonu, numer, dyscyplina) " +
            " VALUES (#{seasonId}, #{number}, #{discipline})")
    @Options(useGeneratedKeys = true, keyProperty = "roundId")
    int insert(Round round);

    @Update("UPDATE kolejki SET id_kolejki=#{roundId}, numer=#{number}, dyscyplina=#{discipline} WHERE id_kolejki=#{roundId}")
    int update(Round round);

    @Delete("DELETE FROM kolejki WHERE id_kolejki = #{id}")
    int deleteById(int id);

}
