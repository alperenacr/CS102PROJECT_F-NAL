package main.spacegame;

public enum WeaponType {
    GUN,
    SNIPER,
    LASERGUN;

    public boolean isBetterThan(WeaponType other) {
        return this.ordinal() > other.ordinal();
    }

    public static WeaponType fromMultiplier(int multiplier) {
        if (multiplier > 200) return LASERGUN;
        if (multiplier > 100) return SNIPER;

        return GUN;
    }
}
