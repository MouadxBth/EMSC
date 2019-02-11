package me.espada.system.components.utils;

public enum ComponentPriority {

    LOW(1),NORMAL(2),HIGH(3), HIGHEST(4);

    private int power;

    ComponentPriority(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
