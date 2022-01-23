package game;

import java.util.ArrayList;

public class CombatStats {

    ArrayList<String> players = new ArrayList<>();
    ArrayList<ArrayList<Boolean>> hitRates = new ArrayList<>();
    ArrayList<ArrayList<Float>> damage = new ArrayList<>();
    ArrayList<Integer> roundsInCombat = new ArrayList<>();
    String winner;


    public int getIndex(String player) {
        return players.indexOf(player);
    }

    public void addToHitRate(int index, Boolean hit) {
        hitRates.get(index).add(hit);
    }

    public double getHitRate(int index) {
        double average = 0;
        for( boolean h : hitRates.get(index)) average += h ? 1 : 0;
        return (average / hitRates.get(index).size());
    }

    public void addDamage(int index, float hit) {
        damage.get(index).add(hit);
    }

    float getTotalDamage() {
        float totalDmg = 0;
        for(ArrayList<Float> dmgList : damage) {
            for(float dmg : dmgList) totalDmg += dmg;
        }
        return totalDmg;
    }

    public boolean playerWasInvolved(String player) {
        return players.contains(player);
    }

    public double getTotalDamage(int index) {
        double average = 0;
        for( float dmg : damage.get(index)) average += dmg;
        return (average / damage.get(index).size());
    }

    public void addRound(int index) {
        roundsInCombat.set(index, roundsInCombat.get(index)+1);
    }

    public boolean setWinner(String id) {
        try {
            winner = id;
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
