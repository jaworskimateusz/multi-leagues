package pl.jaworskimateuszm.myleagues.model;

public class Discipline {

    private long disciplineId;
    private String type;

    public Discipline() {
    }

    public long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
