import actor.player.Player;
import enums.PlayerStates;
import game.Game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Time;
import java.util.*;

public class main {
    Player[] players;

    static ArrayList<String> FirstNames = new ArrayList<>();
    static ArrayList<String> LastNames = new ArrayList<>();

    public static void main(String args[]) {

        loadFirstNames();
        loadLastNames();

//        System.out.printf("FirstNames length: %s\r\nLastNames length: %s%n", FirstNames.size(), LastNames.size());

        new main(args);
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

    public main(String[] args) {
        long startTime = (new Date()).getTime();
        int count = 100_000;
        players = GeneratePlayers(count);

        Game[] games = new Game[count / 10];
        for (int i = 0; i < (count/10); i += 1) {
            Player[] playersForGame =Arrays.copyOfRange(players, i*10, (i*10) + 10);

            // assign players
            for(Player p : playersForGame) {
                p.status = PlayerStates.IN_GAME;
            }

            games[i] = Game.MakeGame(playersForGame);
        }

        Player[][] winners = new Player[count/10][];
        int i = 0;
        for (Game g : games) {
            winners[i++] = g.play();
        }

//        for (Player[] w : winners) System.out.println(Arrays.toString(w));


        long endTime = (new Date()).getTime();

        System.out.printf("Execution took: %s", (((float)(endTime - startTime))/1000f)+"s");

    }

    private static String GetRandomName() {
        return String.join("_", FirstNames.get((new Random()).nextInt(0, FirstNames.size())), LastNames.get((new Random()).nextInt(0, LastNames.size())));
    }

    private static Player[] GeneratePlayers(int count) {
        Player[] output = new Player[count];
        for (int i = 0; i < count; i++) output[i] = new Player(GetRandomName());
        return output;
    }
}
