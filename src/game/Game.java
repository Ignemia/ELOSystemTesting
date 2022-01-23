package game;

import actor.player.Player;
import matchmaking.MatchMaking;
import stats.GameStats;

import java.util.ArrayList;
import java.util.Collections;
//import enums.CombatStates;
//import enums.CombatWinners;
//import enums.PlayerStates;
//import java.security.InvalidParameterException;
//import java.util.Arrays;
//import java.util.Objects;
//import java.util.Random;


public class Game {
    private static MatchMaking matchmaking;
    ArrayList<Player> allPlayers = new ArrayList<>();
    private final Team team1;
    private final Team team2;

    private static final Integer RoundCount = 21;

    ArrayList<ArrayList<Combat>> roundCombats = new ArrayList<>();
    GameStats stats = new GameStats();

    private Game(Team t1, Team t2) {
        team1 = t1;
        team2 = t2;
    }

    public static void ConnectMatchmaking(MatchMaking mm) {
        matchmaking = mm;
    }

    private void mergePlayers() {
        Collections.addAll(allPlayers, team1.players);
        Collections.addAll(allPlayers, team2.players);
    }

    public static Game MakeGameFromTeams(Team t1, Team t2) {
        Game g = new Game(t1, t2);
        g.mergePlayers();
        return g;
    }

    public void play() {
        matchmaking.applyStats(stats);
    }

//
//    private Game(Player[] team1Players, Player[] team2Players) {
//        if (team1Players.length != 5)
//            throw new InvalidParameterException(team1Players.length < 5 ? "Too little players in Team 1 to create a game" : "Too many players in Team 1 to create a game");
//        if (team2Players.length != 5)
//            throw new InvalidParameterException(team2Players.length < 5 ? "Too little players in Team 2 to create a game" : "Too many players in Team 2 to create a game");
//        for(int i = 0; i < 10; i++) {
//            if( i < 5) addPlayer(team1Players[i], i);
//            else addPlayer(team2Players[i-5], i);
//        }
//        team1.assignPlayers(team1Players).lockTeam();
//        team2.assignPlayers(team2Players).lockTeam();
//    }
//
//    void addPlayer(Player p, int index) {
//        allPlayers[index] = p;
//    }
//
//    private static Player[] FilterPlayerBasedOnStates(Player[] players, PlayerStates state) {
//        ArrayList<Player> t_output = new ArrayList<>();
//
//        for (Player p : players) {
//            if (p.status != state) t_output.add(p);
//        }
//
//        Player[] out = new Player[t_output.size()];
//        int i = 0;
//        for (Player p : t_output) out[i++] = p;
//
//        return out;
//    }
//
//    private static Player[] GetNotDeadPlayers(Player[] team) {
//        return FilterPlayerBasedOnStates(team, PlayerStates.DEAD);
//    }
//
//    public Player[] getSearchingPlayers() {
//        return FilterPlayerBasedOnStates(allPlayers, PlayerStates.SEEKING_COMBAT);
//    }
//
//    private Combat pickRandomCombat(ArrayList<Combat> combats) {
//        if (combats.size() <= 0) return Combat.EMPTY;
//        int index = (new Random()).nextInt(combats.size());
//        Combat combat = combats.get(index);
//        if (combat.status != CombatStates.ENDED) return combat;
//        ArrayList<Combat> passingCombats = (ArrayList<Combat>) combats.clone();
//        passingCombats.remove(index);
//        return pickRandomCombat(passingCombats);
//    }
//
//    RoundStats nextRound() {
//        RoundStats rStats = new RoundStats();
//        boolean team1HasLessPlayers = team1.deadPlayers.size() < team2.deadPlayers.size();
//
//        ArrayList<Combat> combats = new ArrayList<>();
//        for (Player p : GetNotDeadPlayers((team1HasLessPlayers ? team1 : team2).players)) combats.add((new Combat()).assignPlayer(p, team1HasLessPlayers));
//        for (Player p : GetNotDeadPlayers((team1HasLessPlayers ? team2 : team1).players)) combats.get((new Random()).nextInt(0, combats.size())).assignPlayer(p, !team1HasLessPlayers);
//
//        for (Combat combat : combats) {
//            combat.runRound(rStats);
//            rStats.addCombat(combat.stats.lock());
//        }
//
//        Random seekersRandom = new Random();
//        for (Player p : getSearchingPlayers()) {
//            boolean findsCombat = p.getSearchingCoeficient() * seekersRandom.nextFloat() > 0.5;
//            if (findsCombat) {
//                Combat t_combat = pickRandomCombat(combats);
//                if (t_combat != Combat.EMPTY) t_combat.assignPlayer(p, team1.hasPlayer(p));
//            }
//        }
//
//        roundCombats.add(combats);
//
//        for (Player p : allPlayers) {
//            p.reset();
//        }
//
//        return rStats.lock();
//    }
//
//    public Team play() {
//        for (int i = 0; i < roundCount; i++) {
//            RoundStats rStats = nextRound();
//        }
//        return GetNotDeadPlayers(team1.players).length > 0 ? team1 : team2;
//    }
//
//    public static Game MakeGame(Player[] players) {
//        return new Game(Arrays.copyOfRange(players, 0, 5), Arrays.copyOfRange(players, 5, 10));
//    }
//
//    public Team getWinner() {
//        int t1wins = countWins(team1);
//        int t2wins = countWins(team2);
//        if(t1wins == t2wins) {
//            return null;
//        } else if(t1wins > t2wins) {
//            return team1;
//        }
//        return team2;
//    }
//
//    public int countWins(Team team) {
//        int counter = 0;
//        for(ArrayList<Combat> cmb : roundCombats) {
//            for (Combat combat : cmb) {
//                if (combat.winner == CombatWinners.TEAM1 && Objects.equals(team.name, "t1")) counter++;
//                else if (combat.winner == CombatWinners.TEAM2 && Objects.equals(team.name, "t2")) counter++;
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder t1 = new StringBuilder();
//        StringBuilder t2 = new StringBuilder();
//
//        for (Player p : team1.players) {
//            t1.append(p.name).append(" (%s), ".formatted(p.skill));
//        }
//        for (Player p : team2.players) {
//            t2.append(p.name).append(" (%s), ".formatted(p.skill));
//        }
//
//        return String.join("", "Team1: " + t1.substring(0, t1.length() - 2) + " - " + team1.averageSkill, "\r\nvs\r\n", "Team 2: " + t2.substring(0, t2.length() - 2) + " - " + team2.averageSkill, "\r\n\r\n");
//    }
//
//    public String generateRoundsReport() {
//        StringBuilder strbld = new StringBuilder();
//        int i = 0;
//        for(ArrayList<Combat> cmb : roundCombats) {
//            strbld.append("Round ").append(i++).append(":\n");
//            for(Combat combat : cmb) strbld.append(combat.generateCombatReport());
//        }
//        return strbld.toString();
//    }
}
