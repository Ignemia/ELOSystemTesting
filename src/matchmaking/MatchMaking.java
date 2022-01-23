package matchmaking;

import actor.player.Player;
import game.Game;
import game.Team;
import stats.Stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatchMaking {
    private ArrayList<Player> players = new ArrayList<>();

    public static MatchMaking Get() {
        return new MatchMaking();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Game createMatch() {
        ArrayList<Player> matchPlayers = new ArrayList<>(Arrays.asList(findPlayers(10)));
        Team[] teams = generateTeams(matchPlayers);
        return Game.MakeGameFromTeams(teams[0], teams[1]);
    }

    private Team[] generateTeams(ArrayList<Player> in_players) {
        Team[] teams = new Team[2];
        in_players.sort((Player p1, Player p2) -> (int) (p2.skill - p1.skill));
        teams[0] = Team.FromPlayerList(new ArrayList<>(List.of(in_players.get(0), in_players.get(3), in_players.get(4), in_players.get(7), in_players.get(9))));
        teams[1] = Team.FromPlayerList(new ArrayList<>(List.of(in_players.get(1), in_players.get(2), in_players.get(5), in_players.get(6), in_players.get(8))));

//        System.out.println("%s vs %s\r".formatted(teams[0].getAverageSkill(), teams[1].getAverageSkill()));

        return teams;
    }

    public void applyStats(Stats stats) {

    }

    private Player[] findPlayers(int count) {
        Player[] out_players = new Player[count];
        for (int i = 0; i < count; i++) out_players[i] = players.get((new Random()).nextInt(players.size() - 1));
        return out_players;
    }
}
