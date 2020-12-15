package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.GameSet;


import java.util.List;

@Mapper
public interface SetMapper {

    @Select("SELECT id_setu, id_meczu, numer, punkty_pierwszego, punkty_drugiego FROM sety WHERE id_meczu = #{gameId}")
    @Results({
            @Result(property = "gameSetId", column = "id_setu"),
            @Result(property = "gameId", column = "id_meczu"),
            @Result(property = "number", column = "numer"),
            @Result(property = "firstPlayerScore", column = "punkty_pierwszego"),
            @Result(property = "secondPlayerScore", column = "punkty_drugiego")
    })
    List<GameSet> findAllByGameId(int gameId);


    @Select("SELECT id_setu, id_meczu, numer, punkty_pierwszego, punkty_drugiego FROM sety WHERE id_setu = #{id}")
    @Results({
            @Result(property = "gameSetId", column = "id_setu"),
            @Result(property = "gameId", column = "id_meczu"),
            @Result(property = "number", column = "numer"),
            @Result(property = "firstPlayerScore", column = "punkty_pierwszego"),
            @Result(property = "secondPlayerScore", column = "punkty_drugiego")
    })
    GameSet findById(int id);

}
