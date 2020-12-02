package pl.jaworskimateuszm.myleagues.model;

public class Rank {

    private long rankId;
    private String name;
    private String surname;
    private int points;
    private String discipline;
    private long leagueId;

    public Rank() {
    }

    public long getRankId() {
        return rankId;
    }

    public void setRankId(long rankId) {
        this.rankId = rankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(long leagueId) {
        this.leagueId = leagueId;
    }
}
