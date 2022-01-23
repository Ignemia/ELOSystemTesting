package game;

import java.util.ArrayList;
import java.util.Date;

public class GameStats {
    boolean locked = false;
    int[] wonRounds = new int[2];

    ArrayList<RoundStats> rounds = new ArrayList<>();

    long startTime;
    long endTime;
    long totalTime;

    String MVP;

    public GameStats() {
        startTime = (new Date()).getTime();
    }

    public void addRound(RoundStats round) {
        rounds.add(round);
        addRoundWin(round.getWinner());
    }

    public int getTeam1RoundWins() {
        return wonRounds[0];
    }

    public int getTeam2RoundWins() {
        return wonRounds[1];
    }

    int addRoundWin(int team) throws RuntimeException{
        if (locked) throw new RuntimeException("Game is already over!");
        return wonRounds[team]++;
    }

    public int getWinner() {
        if (wonRounds[1] == wonRounds[0]) return -1;
        return wonRounds[1] > wonRounds[0] ? 1 : 0;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public boolean lock(String mvp) throws RuntimeException{
        if(!locked) return locked = true;
        endTime = (new Date()).getTime();
        totalTime = endTime - startTime;
        MVP = mvp;
        throw new RuntimeException("Game is already locked!");
    }
}
