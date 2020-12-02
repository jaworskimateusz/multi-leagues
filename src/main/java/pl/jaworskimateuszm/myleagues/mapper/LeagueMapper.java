package pl.jaworskimateuszm.myleagues.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.League;
import pl.jaworskimateuszm.myleagues.model.Rank;

@Mapper
public interface LeagueMapper {

    @Select("SELECT id_ligi, id_dyscypliny, poziom, opis FROM ligi WHERE poziom=#{level}")
    @Results({
            @Result(property = "leagueId", column = "id_ligi"),
            @Result(property = "disciplineId", column = "id_dyscypliny"),
            @Result(property = "level", column = "poziom"),
            @Result(property = "description", column = "opis")
    })
    List<League> findAllByLevel(String level);

    @Select("SELECT id_ligi, id_dyscypliny,poziom, opis FROM ligi")
    @Results({
            @Result(property = "leagueId", column = "id_ligi"),
            @Result(property = "disciplineId", column = "id_dyscypliny"),
            @Result(property = "level", column = "poziom"),
            @Result(property = "description", column = "opis")
    })
    List<League> findAll();

    @Select("SELECT id_ligi, id_dyscypliny, poziom, opis FROM ligi WHERE id_ligi = #{id}")
    @Results({
            @Result(property = "leagueId", column = "id_ligi"),
            @Result(property = "disciplineId", column = "id_dyscypliny"),
            @Result(property = "level", column = "poziom"),
            @Result(property = "description", column = "opis")
    })
    League findById(int id);

    @Delete("DELETE FROM ligi WHERE id_ligi = #{id}")
    int deleteById(int id);

    @Insert("INSERT INTO ligi (id_dyscypliny, poziom, opis) " +
            " VALUES (NULL, #{level}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "leagueId")
    int insert(League league);

    @Update("UPDATE ligi SET poziom=#{level}, opis=#{description} where id_ligi=#{leagueId}")
    int update(League league);

    @Select("SELECT id_rankingu, imie, nazwisko, punkty, dyscyplina FROM rankingi WHERE id_ligi=#{leagueId}")
    @Results({
            @Result(property = "rankId", column = "id_rankingu"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "points", column = "punkty"),
            @Result(property = "discipline", column = "dyscyplina"),
            @Result(property = "leagueId", column = "id_ligi")
    })
    List<Rank> findLeagueRank(long leagueId);

    @Select("SELECT id_rankingu, imie, nazwisko, punkty, dyscyplina from rankingi WHERE nazwisko=#{surname}")
    @Results({
            @Result(property = "rankId", column = "id_rankingu"),
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "points", column = "punkty"),
            @Result(property = "discipline", column = "dyscyplina"),
            @Result(property = "leagueId", column = "id_ligi")
    })
    List<Rank> findRankBySurname(String surname);

}
