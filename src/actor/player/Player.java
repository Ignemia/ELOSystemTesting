package actor.player;

import actor.Actor;
import enums.PlayerStates;

import java.util.Random;
import java.util.UUID;

public class Player extends Actor {
    public static final Float SkillMax = 4000F;
    PlayerStats stats = new PlayerStats();
    public Float skill = 1000F;
    public PlayerStates status = PlayerStates.IN_GAME_SEARCH;


    public Player(String in_name) {
        name = in_name;
        id = UUID.randomUUID().toString();
        Random r = new Random();
        skill += r.nextFloat(-100F, 100F);
    }

    public float getSearchingCoeficient() {
        return 1F;
    }

    public float getDamageMultiplier() {
        double hitValue = (new Random()).nextDouble();

        double headshotLow = Math.cos(((Math.PI * skill) / (SkillMax * 2)) * 0.7 + 0.3);
        double bodyshotLow = Math.cos(((Math.PI * skill) / (SkillMax * 2)) * 0.4 + 0.05);
        if (hitValue > headshotLow) return 2F;
        if (hitValue > bodyshotLow) return 1F;
        return 0.5F;
    }

    public long damage(final float amount) {
        long takenDamage = stats.takeDamage(stats.mitigateDamage(amount));
        if (stats.currentHp <= 0) status = PlayerStates.DEAD;
        return takenDamage;
    }

    public PlayerStatValues getStats() {
        return stats.getStats();
    }

    public String getId() {
        return this.id;
    }
}
