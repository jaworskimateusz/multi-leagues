package pl.jaworskimateuszm.myleagues.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.League;

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

}
