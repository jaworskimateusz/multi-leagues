package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.Round;
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

}
