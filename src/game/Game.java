package game;

import actor.Actor;
import actor.player.Player;
import enums.PlayerStates;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    Player[] team1;
    Player[] team2;

    Integer roundRound = 1;

    private Game(Player[] players) {
        if (players.length != 10) throw new InvalidParameterException(players.length < 10 ? "Too little players to create a game" : "Too many players to create a game");
        team1 = new Player[]{players[0], players[1], players[2], players[3], players[4]};
        team2 = new Player[]{players[5], players[6], players[7], players[8], players[9]};

//        System.out.println(Arrays.toString(team1));
//        System.out.println(Arrays.toString(team2));

    }

    private Player runCombat(Player p1, Player p2) {
        if(p1.skill > p2.skill) return p1;
        else if (p1.skill < p2.skill) return p2;
        return (new Random()).nextFloat(0,1) <= 0.5 ? p1 : p2;
    }

    private static Player[] GetNotDeadPlayers(Player[] team) {
        ArrayList<Player> t_output = new ArrayList<>();

        for( Player p : team) {
            if(p.status != PlayerStates.DEAD) t_output.add(p);
        }

        Player[] out = new Player[t_output.size()];
        int i = 0;
        for(Player p : t_output) out[i++] = p;

        return out;

//        return (Player[]) Arrays.stream(team).filter(p -> p.status != PlayerStates.DEAD).toArray();
    }

    void nextRound() {
        for(Player p1 : GetNotDeadPlayers(team1)) {
            for (Player p2 : GetNotDeadPlayers(team2)) {
                if(runCombat(p1, p2) == p1) p2.status = PlayerStates.DEAD;
                else p1.status = PlayerStates.DEAD;
            }
        }
    }

    public Player[] play() {
        boolean t1IsDead = false;
        boolean t2IsDead = false;
        while(!t1IsDead && !t2IsDead) {
            nextRound();
            t1IsDead = GetNotDeadPlayers(team1).length > 0;
            t2IsDead = GetNotDeadPlayers(team2).length > 0;
        }
        return GetNotDeadPlayers(team1).length > 0 ? team1 : team2;
    }


    public static Game MakeGame(Player[] players) {
        return new Game(players);
    }

    @Override
    public String toString() {
        String t1 = "";
        String t2 = "";

        Float t1_skill = 0F;
        Float t2_skill = 0F;

        for (Player p : team1) {
            t1_skill += p.skill;
            t1 += p.name + " (%s), ".formatted(p.skill);
        }
        for (Player p : team2) {
            t2_skill += p.skill;
            t2 += p.name + " (%s), ".formatted(p.skill);
        }

        t1_skill /= team1.length;
        t2_skill /= team2.length;

        return String.join("", "Team1: " + t1.substring(0, t1.length() - 2) + " - " + t1_skill, "\r\nvs\r\n", "Team 2: " + t2.substring(0, t2.length() - 2) + " - " + t2_skill, "\r\n\r\n");
    }
}
