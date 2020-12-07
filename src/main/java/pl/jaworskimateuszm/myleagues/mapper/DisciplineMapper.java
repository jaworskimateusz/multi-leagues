package pl.jaworskimateuszm.myleagues.mapper;

import org.apache.ibatis.annotations.*;
import pl.jaworskimateuszm.myleagues.model.Discipline;
import java.util.List;

@Mapper
public interface DisciplineMapper {

    @Select("SELECT id_dyscypliny, typ FROM dyscypliny")
    @Results({
            @Result(property = "disciplineId", column = "id_dyscypliny"),
            @Result(property = "type", column = "typ")
    })
    List<Discipline> findAll();

}
