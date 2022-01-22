package actor.player;

import actor.Stats;

public class PlayerStats implements Stats {
    public long attackDamage = 100L;
    public Double attackPrecision = 0.05;
    public long armor = 0L;
    public Float hpTotal = 0F;
    public Float currentHp = 0F;
    public boolean isDead = false;

    public PlayerStatValues getStats() {
        return new PlayerStatValues(attackDamage, attackPrecision, armor, hpTotal, currentHp);
    }


    public long takeDamage(float amount) {
        currentHp -= amount;
        if(currentHp <= 0) isDead = true;
        return ((long) amount);
    }






    @Override
    public Float mitigateDamage(Float damage) {
        if (damage - armor <= 0) return 0F;
        return damage - armor;
    }

    @Override
    public Float increaseTotalHealth(Float amount) {
        if (hpTotal + amount >= hpMax) throw new RuntimeException("HpTotal cannot exceed Max TotalHP!");
        if (hpTotal + amount <= 0) throw new RuntimeException("HpTotal cannot go below 0!");
        return hpTotal += amount;
    }

    @Override
    public long increaseArmor(long amount) {
        if (armor + amount >= armorMax) throw new RuntimeException("Armor cannot exceed Max Armor!");
        if (armor + amount <= 0) throw new RuntimeException("Armor cannot go below 0!");
        return armor += amount;
    }

    @Override
    public long increaseAttackDamage(long amount) {
        if (attackDamage + amount >= attackDamageMax) throw new RuntimeException("AttackDamage cannot exceed Max AttackDamage!");
        if (attackDamage + amount <= 0) throw new RuntimeException("AttackDamage cannot go below 0!");
        return attackDamage += amount;
    }

    @Override
    public Double increaseAttackPrecision(Double amount) {
        if (attackPrecision + amount >= attackPrecisionMax) throw new RuntimeException("AttackPrecision cannot exceed Max AttackPrecision!");
        if (attackPrecision + amount <= 0) throw new RuntimeException("AttackPrecision cannot go below 0!");
        return attackPrecision += amount;
    }
}
