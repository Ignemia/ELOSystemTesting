package game;

import java.util.ArrayList;
import java.util.Date;

public class RoundStats {
    boolean locked = false;
    int winner = -1;
    ArrayList<CombatStats> combats = new ArrayList<>();
    ArrayList<String> deadPlayers = new ArrayList<>();
    ArrayList<String> survivingPLayers = new ArrayList<>();
    float totalDamageDealt = 0;
    Date startTime = new Date();
    Date endTime;
    long totalTime;

    String mvp;

    void setWinner() {

    }

    String findMvp() {
        ArrayList<Float> damagesDealt = new ArrayList<>();
        for (String p : survivingPLayers) {
            float damage = 0F;
            for (CombatStats c : combats) {
                if(c.playerWasInvolved(p)) damage +=  c.getTotalDamage(c.getIndex(p));
            }
            damagesDealt.add(damage);
        }
        int highestDamageDealtIndex = -1;
        for (int i = 0; i < damagesDealt.size(); i++) {
            if(highestDamageDealtIndex == -1)
            {
                highestDamageDealtIndex = 0;
                continue;
            }
            if(damagesDealt.get(i) > damagesDealt.get(highestDamageDealtIndex)) highestDamageDealtIndex = i;
        }
        return survivingPLayers.get(highestDamageDealtIndex);
    }

    void endTiming() {
        endTime = new Date();
        totalTime = endTime.getTime() - startTime.getTime();
    }

    void setTotalDamageDealt() {
        for (CombatStats c : combats) {
            totalDamageDealt += c.getTotalDamage();
        }
    }

    void lock() throws RuntimeException{
        if (locked) throw new RuntimeException("Round is already locked!");
        endTiming();
        mvp = findMvp();
        setTotalDamageDealt();
    }

    public int getWinner() {
        return winner;
    }
}
