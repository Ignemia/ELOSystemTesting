package game;

import actor.Actor;

import java.security.InvalidParameterException;

public class Game {
    Actor[] team1;
    Actor[] team2;

    private Game(Actor[] players) {
        if (players.length != 10) throw new InvalidParameterException(players.length < 10 ? "Too little players to create a game" : "Too many players to create a game");
        team1 = new Actor[]{players[0], players[1], players[2], players[3], players[4]};
        team2 = new Actor[]{players[5], players[6], players[7], players[8], players[9]};

//        System.out.println(Arrays.toString(team1));
//        System.out.println(Arrays.toString(team2));

    }

    public static Game MakeGame(Actor[] players) {
        return new Game(players);
    }
}
