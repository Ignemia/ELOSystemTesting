import actor.player.Player;
import game.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class main {
    Player[] players;

    static ArrayList<String> FirstNames = new ArrayList<String>();
    static ArrayList<String> LastNames = new ArrayList<String>();

    public static void main(String args[]) {

        loadFirstNames();
        loadLastNames();

        System.out.println("FirstNames length: %s\r\nLastNames length: %s".formatted(FirstNames.size(), LastNames.size()));

        new main(args);
    }

    private static void loadFirstNames() {
        try {
            BufferedReader namesFile = new BufferedReader(new FileReader("data/first_names.txt"));
            String line;
            while((line = namesFile.readLine()) != null) FirstNames.add(line);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private static void loadLastNames() {
        try {
            BufferedReader namesFile = new BufferedReader(new FileReader("data/last_names.txt"));
            String line;
            while((line = namesFile.readLine()) != null) LastNames.add(line);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public main(String[] args) {
        int count = 10_000;
        players = GeneratePlayers(count);

        Game[] games = new Game[count / 10];
        for (int i = 0; i < (count / 10); i++) {
            games[i] = Game.MakeGame(Arrays.copyOfRange(players, i, i + 10));
        }
    }


    private static Player[] GeneratePlayers(int count) {
        Player[] output = new Player[count];
        for (int i = 0; i < count; i++) output[i] = new Player("");
        return output;
    }
}
