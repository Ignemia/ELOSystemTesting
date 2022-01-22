package game;

import actor.player.Player;
import enums.PlayerStates;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    Player[] allPlayers;
    Team team1 = new Team("t1");
    Team team2 = new Team("t2");

    Integer roundRound = 1;

    private Game(Player[] players) {
        if (players.length != 10) throw new InvalidParameterException(players.length < 10 ? "Too little players to create a game" : "Too many players to create a game");
        allPlayers = players;
        team1.assignPlayers(new Player[]{players[0], players[1], players[2], players[3], players[4]}).lockTeam();
        team2.assignPlayers(new Player[]{players[5], players[6], players[7], players[8], players[9]}).lockTeam();
    }

    private static Player[] FilterPlayerBasedOnStates(Player[] players, PlayerStates state) {
        ArrayList<Player> t_output = new ArrayList<>();

        for (Player p : players) {
            if (p.status != state) t_output.add(p);
        }

        Player[] out = new Player[t_output.size()];
        int i = 0;
        for (Player p : t_output) out[i++] = p;

        return out;
    }

    private static Player[] GetNotDeadPlayers(Player[] team) {
        return FilterPlayerBasedOnStates(team, PlayerStates.DEAD);
    }

    public Player[] getSearchingPlayers() {
        return FilterPlayerBasedOnStates(allPlayers, PlayerStates.SEEKING_COMBAT);
    }

    void nextRound() {
        for (Player p1 : GetNotDeadPlayers(team1.players)) {
            for (Player p2 : GetNotDeadPlayers(team2.players)) {
                Player winner = runCombat(p1, p2);
                Player loser = winner != p1 ? p2 : p1;
                if (loser.getStats().getCurrentHp() <= 0) {
                    loser.status = PlayerStates.DEAD;
                    winner.status = PlayerStates.SEEKING_COMBAT;
                }
            }
        }

        Random seekersRandom = new Random();
        for (Player p : getSearchingPlayers()) {
            boolean findsCombat = p.getSearchingCoeficient() * seekersRandom.nextFloat() > 0.5;
            if (findsCombat) {
                Player[] aliveFoes = GetNotDeadPlayers(team1.hasPlayer(p) ? team2.players : team1.players);

            }
        }

    }

    public Team play() {
        boolean t1IsDead = false;
        boolean t2IsDead = false;
        while (!t1IsDead && !t2IsDead) {
            nextRound();
            t1IsDead = GetNotDeadPlayers(team1.players).length > 0;
            t2IsDead = GetNotDeadPlayers(team2.players).length > 0;
        }
        return GetNotDeadPlayers(team1.players).length > 0 ? team1 : team2;
    }


    public static Game MakeGame(Player[] players) {
        return new Game(players);
    }

    @Override
    public String toString() {
        StringBuilder t1 = new StringBuilder();
        StringBuilder t2 = new StringBuilder();

        for (Player p : team1.players) {
            t1.append(p.name).append(" (%s), ".formatted(p.skill));
        }
        for (Player p : team2.players) {
            t2.append(p.name).append(" (%s), ".formatted(p.skill));
        }

        return String.join("", "Team1: " + t1.substring(0, t1.length() - 2) + " - " + team1.averageSkill, "\r\nvs\r\n", "Team 2: " + t2.substring(0, t2.length() - 2) + " - " + team2.averageSkill, "\r\n\r\n");
    }
}
