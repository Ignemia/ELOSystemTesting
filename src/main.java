import actor.player.Player;
import game.Game;
import matchmaking.MatchMaking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class main {
    static Player[] players;

    private static final int PlayerCount = 10_000;
    private static final int MaxMatches = 100_000;

    static ArrayList<String> FirstNames = new ArrayList<>();
    static ArrayList<String> LastNames = new ArrayList<>();

    public static void main(String[] args) {

        loadFirstNames();
        loadLastNames();

        players = GeneratePlayers();

        MatchMaking mm = MatchMaking.Get();
        Game.ConnectMatchmaking(mm);
        for (Player p : players) mm.addPlayer(p);

//        ArrayList<Game> games = new ArrayList<>();

        for (int i = 0; i < MaxMatches; i++) {
            Game match = mm.createMatch();
            match.play();
//            games.add(match);
        }
    }

    private static void loadFirstNames() {
        try {
            BufferedReader namesFile = new BufferedReader(new FileReader("data/first_names.txt"));
            String line;
            while ((line = namesFile.readLine()) != null) FirstNames.add(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadLastNames() {
        try {
            BufferedReader namesFile = new BufferedReader(new FileReader("data/last_names.txt"));
            String line;
            while ((line = namesFile.readLine()) != null) LastNames.add(line);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String GetRandomName() {
        return String.join("_", FirstNames.get((new Random()).nextInt(0, FirstNames.size())), LastNames.get((new Random()).nextInt(0, LastNames.size())));
    }

    private static Player[] GeneratePlayers() {
        Player[] output = new Player[main.PlayerCount];
        for (int i = 0; i < main.PlayerCount; i++) output[i] = new Player(GetRandomName());
        return output;
    }
}
