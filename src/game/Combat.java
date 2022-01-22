package game;

import actor.player.Player;
import enums.CombatStates;
import enums.CombatWinners;
import enums.PlayerStates;

import java.util.ArrayList;
import java.util.Random;

public class Combat {

    ArrayList<String> events = new ArrayList<>();

    ArrayList<Player> team1Players = new ArrayList<>();
    ArrayList<Player> team2Players = new ArrayList<>();

    CombatStates status = CombatStates.IN_PROGRESS;

    public CombatWinners winner = CombatWinners.IN_PROGRESS;

    public static Combat EMPTY = (new Combat()).setState(CombatStates.EMPTY);

    private Combat setState(CombatStates state) {
        status = state;
        return this;
    }

    public Combat assignPlayer(Player player, boolean toTeam1) {
        if (toTeam1) team1Players.add(player);
        else team2Players.add(player);

        events.add("%s joined team %s".formatted(player.getStatusString(), toTeam1 ? "team 1" : "team 2"));

        return this;
    }

    public ArrayList<Player> runRound() {
        for (Player p1 : team1Players) {
            for (Player p2 : team2Players) {
                Random hits = new Random();

                boolean player1Hit = Math.sin((Math.pow(p1.skill, 2) * Math.PI) / (Math.pow(Player.SkillMax, 2) * 2)) * 0.7 + 0.05 <= hits.nextDouble();
                boolean player2Hit = Math.sin((Math.pow(p2.skill, 2) * Math.PI) / (Math.pow(Player.SkillMax, 2) * 2)) * 0.7 + 0.05 <= hits.nextDouble();

                float p1Damage = p1.getDamageMultiplier() * p1.getStats().getAttackDamage();
                float p2Damage = p2.getDamageMultiplier() * p2.getStats().getAttackDamage();

                if (p1.skill > p2.skill) {
                    hitOpponent(p2, p1, player2Hit, player1Hit, p2Damage, p1Damage);
                } else {
                    hitOpponent(p1, p2, player1Hit, player2Hit, p1Damage, p2Damage);
                }
            }
            if (team1Dead() || team2Dead()) return end();
        }
        if (team1Dead() || team2Dead()) return end();
        return runRound();
    }

    private void hitOpponent(Player p1, Player p2, boolean player1Hit, boolean player2Hit, float p1Damage, float p2Damage) {
        if (player2Hit) {
            long dmg = p1.damage(p2Damage);
            events.add("%s took %s damage from %s".formatted(p1.getStatusString(), dmg, p2.getStatusString()));
        }
        if (player1Hit && p1.status != PlayerStates.DEAD) {
            long dmg = p2.damage(p1Damage);
            events.add("%s took %s damage from %s".formatted(p2.getStatusString(), dmg, p1.getStatusString()));
        }
    }

    ArrayList<Player> end() {
        setState(CombatStates.ENDED);

        if(team1Players.size() < 1 || team2Players.size() < 1) {
            events.add("%s combat never took place!".formatted(team1Players.size() < 1 ? team2Players.get(0) : team1Players.get(0)));
            winner = CombatWinners.NEVER_TOOK_PLACE;
            return team1Players.size() < 1 ? team2Players : team1Players;
        }

        events.add("%s won!".formatted(team1Dead() ? "Team 2" : "Team 1"));

        if(team1Dead()) {
            winner = CombatWinners.TEAM2;
            return team2Players;
        } else {
            winner = CombatWinners.TEAM1;
            return team1Players;
        }
    }

    private boolean team1Dead() {
        for (Player p : team1Players) if (p.status != PlayerStates.DEAD) return false;
        return true;
    }

    private boolean team2Dead() {
        for (Player p : team2Players) if (p.status != PlayerStates.DEAD) return false;
        return true;
    }

    public String generateCombatReport() {
        StringBuilder output = new StringBuilder();
        for (String ev : events) {
            output.append(ev + "\n");
        }
        return output + "\r\n";
    }
}
