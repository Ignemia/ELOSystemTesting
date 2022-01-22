package actor;

public interface Stats {
    public Float hp = 0F;
    public Float hpTotal = 100F;
    public final Float hpMax = Float.MAX_VALUE;
    public long armor = 0L;
    public final long armorMax = 100;
    public long attackDamage = 0L;
    public final long attackDamageMax = Long.MAX_VALUE;
    public Double attackPrecision = 0.0D;
    public final Double attackPrecisionMax = 1D;

    public Float mitigateDamage(Float damage);
    public Float increaseTotalHealth(Float amount);
    public long increaseArmor(long amount);
    public long increaseAttackDamage(long amount);
    public Double increaseAttackPrecision(Double amount);
}
