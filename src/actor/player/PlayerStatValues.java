package actor.player;

public class PlayerStatValues {
    private final long attackDamage;
    private final Double attackPrecision;
    private final long armor;
    private final Float hpTotal;
    private final Float currentHp;

    public PlayerStatValues(long dmg, double ap, long arm, float hptotal, float chp) {
        attackDamage = dmg;
        attackPrecision = ap;
        armor = arm;
        hpTotal = hptotal;
        currentHp = chp;
    }

    public long getAttackDamage() {
        return attackDamage;
    }
    public long getArmor() {
        return armor;
    }
    public Double getAttackPrecision() {
        return attackPrecision;
    }
    public Float getHpTotal() {
        return hpTotal;
    }
    public Float getCurrentHp() {
        return currentHp;
    }
}
