package pl.jaworskimateuszm.myleagues.model;

import java.util.Date;

public class GameWithPlayersView extends Game {

    private String firstPlayerName;
    private String firstPlayerSurname;
    private String secondPlayerName;
    private String secondPlayerSurname;

    public GameWithPlayersView() {

    }

    public GameWithPlayersView(int gameId,
                               int roundId,
                               int firstPlayerScore,
                               int secondPlayerScore,
                               int firstPlayerId,
                               int secondPlayerId,
                               Date gameDate,
                               String place,
                               String firstPlayerName,
                               String firstPlayerSurname,
                               String secondPlayerName,
                               String secondPlayerSurname) {
        super(gameId, roundId, firstPlayerScore, secondPlayerScore, firstPlayerId, secondPlayerId, gameDate, place);
        this.firstPlayerName = firstPlayerName;
        this.firstPlayerSurname = firstPlayerSurname;
        this.secondPlayerName = secondPlayerName;
        this.secondPlayerSurname = secondPlayerSurname;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getFirstPlayerSurname() {
        return firstPlayerSurname;
    }

    public void setFirstPlayerSurname(String firstPlayerSurname) {
        this.firstPlayerSurname = firstPlayerSurname;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public String getSecondPlayerSurname() {
        return secondPlayerSurname;
    }

    public void setSecondPlayerSurname(String secondPlayerSurname) {
        this.secondPlayerSurname = secondPlayerSurname;
    }
}
