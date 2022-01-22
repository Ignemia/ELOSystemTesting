package game;

import actor.player.Player;
import enums.PlayerStates;

import java.util.ArrayList;
import java.util.Random;

public class Combat {
    ArrayList<Player> team1Players = new ArrayList<>();
    ArrayList<Player> team2Players = new ArrayList<>();

    public Combat assignPlayer(Player player, boolean toTeam1) {
        if (toTeam1) team1Players.add(player);
        else team2Players.add(player);
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

                if (player1Hit) p2.damage(p1Damage);
                if (player2Hit) p1.damage(p2Damage);
            }
            if(team1Dead() || team2Dead()) return team1Dead() ? team2Players : team1Players;
        }
        if(team1Dead() || team2Dead()) return team1Dead() ? team2Players : team1Players;
        return runRound();
    }

    private boolean team1Dead() {
        for (Player p : team1Players) if (p.status != PlayerStates.DEAD) return false;
        return true;
    }

    private boolean team2Dead() {
        for (Player p : team2Players) if (p.status != PlayerStates.DEAD) return false;
        return true;
    }
}
