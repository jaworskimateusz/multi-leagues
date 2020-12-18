package pl.jaworskimateuszm.myleagues.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.Discipline;
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

    @Select("SELECT users.imie AS imie, users.nazwisko AS nazwisko, " +
            "zawodnicy_ligi.punkty AS punkty " +
            "FROM users " +
            "JOIN zawodnicy_ligi USING (user_id) " +
            "JOIN ligi USING (id_ligi) " +
            "WHERE id_ligi=#{leagueId} " +
            "ORDER BY zawodnicy_ligi.punkty DESC")
    @Results({
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "points", column = "punkty"),
            @Result(property = "leagueId", column = "id_ligi")
    })
    List<Rank> findLeagueRank(long leagueId);

    @Select("SELECT users.imie AS imie, users.nazwisko AS nazwisko, " +
            "zawodnicy_ligi.punkty AS punkty " +
            "FROM users " +
            "JOIN zawodnicy_ligi USING (user_id) " +
            "JOIN ligi USING (id_ligi) " +
            "WHERE nazwisko=#{surname} " +
            "ORDER BY zawodnicy_ligi.punkty DESC")
    @Results({
            @Result(property = "name", column = "imie"),
            @Result(property = "surname", column = "nazwisko"),
            @Result(property = "points", column = "punkty"),
            @Result(property = "leagueId", column = "id_ligi")
    })
    List<Rank> findRankBySurname(String surname);

    @Select("SELECT id_dyscypliny, typ FROM dyscypliny")
    @Results({
            @Result(property = "disciplineId", column = "id_dyscypliny"),
            @Result(property = "type", column = "typ")
    })
    List<Discipline> findAllDisciplines();

}
