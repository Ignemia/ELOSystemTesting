package game;

import actor.player.Player;

import java.util.Objects;

public class Team {
    int playerCount = 0;
    public Player[] players = new Player[5];
    public Player[] deadPlayers;
    public String name;
    public float averageSkill;
    public boolean locked = false;

    public Team(String in_name) {
        name = in_name;
    }

    public boolean assignPlayer(Player p) throws ExceptionInInitializerError {
        if(locked) throw new ExceptionInInitializerError("Cannot assign players to a locked team!");
        players[playerCount++] = p;
        return true;
    }

    public Team assignPlayers(Player[] players) {
        if(locked) throw new ExceptionInInitializerError("Cannot assign players to a locked team!");
        for (Player p : players) assignPlayer(p);
        return this;
    }

    public void lockTeam() {
        locked = true;
        float a_skill = 0.0f;
        for(Player p : players) {
            a_skill += p.skill;
        }
        averageSkill = a_skill / playerCount;
    }

    public boolean hasPlayer(Player p) {
        for (Player player : players) if(Objects.equals(p.getId(), player.getId())) return true;
        return false;
    }
}
