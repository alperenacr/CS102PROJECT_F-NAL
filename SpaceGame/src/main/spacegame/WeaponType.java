package main.spacegame;

public enum WeaponType {
    SINGLE,
    DOUBLE,
    TRIPLE;

    public boolean isBetterThan(WeaponType other) {
        return this.ordinal() > other.ordinal();
    }

    public static WeaponType fromMultiplier(int multiplier) {
        if (multiplier > 200) return DOUBLE;
        if (multiplier > 100) return TRIPLE;

        return SINGLE;
    }
}
