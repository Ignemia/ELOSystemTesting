package stats;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CombatStats {

    boolean locked = false;

    ArrayList<String> players = new ArrayList<>();
    ArrayList<ArrayList<Boolean>> hitRates = new ArrayList<>();
    ArrayList<ArrayList<Float>> damage = new ArrayList<>();
    ArrayList<Integer> roundsInCombat = new ArrayList<>();
    int winner = -1;


    public void addPlayer(String id) {
        players.add(id);
        hitRates.add(new ArrayList<>());
        damage.add(new ArrayList<>());
        roundsInCombat.add(0);
    }

    public int getIndex(String player) {
        return players.indexOf(player);
    }

    public void addToHitRate(int index, Boolean hit) {
        hitRates.get(index).add(hit);
    }

    public double getHitRate(int index) {
        double average = 0;
        for (boolean h : hitRates.get(index)) average += h ? 1 : 0;
        return (average / hitRates.get(index).size());
    }

    public void addDamage(int index, float hit) {
        damage.get(index).add(hit);
    }

    float getTotalDamage() {
        float totalDmg = 0;
        for (ArrayList<Float> dmgList : damage) {
            for (float dmg : dmgList) totalDmg += dmg;
        }
        return totalDmg;
    }

    public boolean playerWasInvolved(String player) {
        return players.contains(player);
    }

    public double getTotalDamage(int index) {
        double average = 0;
        for (float dmg : damage.get(index)) average += dmg;
        return (average / damage.get(index).size());
    }

    public void addRound(String id) {
        int ind = getIndex(id);
        int roundCount = roundsInCombat.get(ind);
        roundsInCombat.set(ind, roundCount + 1);
    }

    public void setWinner(int team) {
        winner = team;
    }

    public CombatStats lock() throws RuntimeException {
        if (locked) throw new RuntimeException("Combat stats already locked!");
        locked = true;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(3);

        for (String p : players) {
            StringBuilder localString = new StringBuilder();
            int id = getIndex(p);

            localString.append(p).append(" - ").append(id).append(" - {\t");

            // hitRate
            localString.append("HitRate: ").append(formatter.format(getHitRate(id))).append(";\t");

            // Damage Dealth
            localString.append("Damage: ").append(formatter.format(getTotalDamage(id))).append(";\t");

            // Damage Dealth
            localString.append("In combat for: ").append(roundsInCombat.get(id)).append(" rounds\t");

            output.append(localString.append("}\r"));
        }

        return output.toString();
    }

}
