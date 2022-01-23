package game;

import actor.player.Player;
import enums.PlayerStates;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Team {
    int playerCount = 0;
    public Player[] players = new Player[5];
    public ArrayList<Player> deadPlayers = new ArrayList<>();
    public String name;
    public float averageSkill;
    public boolean locked = false;

    public Team(String in_name) {
        name = in_name;
    }

    public void killPlayer(String id) {
        for (Player p : players) if (Objects.equals(p.getId(), id) && p.status == PlayerStates.DEAD) deadPlayers.add(p);
    }

    public boolean assignPlayer(Player p) throws ExceptionInInitializerError {
        if (locked) throw new ExceptionInInitializerError("Cannot assign players to a locked team!");
        players[playerCount++] = p;
        p.lockPlayer();
        return true;
    }

    public Team assignPlayers(ArrayList<Player> players) {
        if (locked) throw new ExceptionInInitializerError("Cannot assign players to a locked team!");
        for (Player p : players) assignPlayer(p);
        return this;
    }

    public float getAverageSkill() {
        float skill = 0;
        for ( Player p : players) {
            skill += p.skill;
        }
        return skill / players.length;
    }

    public void lockTeam() {
        locked = true;
        float a_skill = 0.0f;
        for (Player p : players) {
            a_skill += p.skill;
        }
        averageSkill = a_skill / playerCount;
    }

    public boolean hasPlayer(Player p) {
        for (Player player : players) if (Objects.equals(p.getId(), player.getId())) return true;
        return false;
    }

    public static Team FromPlayerList(ArrayList<Player> players) {
        Team t = new Team(UUID.randomUUID().toString());
        t.assignPlayers(players);
        return t;
    }
}
