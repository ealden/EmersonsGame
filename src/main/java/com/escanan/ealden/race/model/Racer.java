package com.escanan.ealden.race.model;

import static java.lang.Math.max;

public class Racer {
    public enum SpeedType {
        NORMAL, SUPER
    }

    private static final int DEFAULT_FINISH_LINE = 20;
    private static final int ZERO_MOVE = 0;
    private static final int ODD_NORMAL_MOVE = 1;
    private static final int EVEN_NORMAL_MOVE = 2;

    private int position = 0;
    private int damage = 0;
    private int finishLine = DEFAULT_FINISH_LINE;

    public boolean isOver() {
        return position >= finishLine;
    }

    public void move(int roll, SpeedType speedType) {
        if (SpeedType.NORMAL.equals(speedType)) {
            normalMove(roll);
        } else if (SpeedType.SUPER.equals(speedType)) {
            superMove(roll);
        }
    }

    public int getDamage() {
        return damage;
    }

    public int getPosition() {
        if (isOver()) {
            return finishLine;
        } else {
            return position;
        }
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setFinishLine(int finishLine) {
        this.finishLine = finishLine;
    }

    private void normalMove(int roll) {
        move(normal(roll));
    }

    private void superMove(int roll) {
        move(roll);

        incrementDamage();
    }

    private void move(int roll) {
        position += max((roll - damage), ZERO_MOVE);
    }

    private int normal(int roll) {
        return (isOdd(roll) ? ODD_NORMAL_MOVE : EVEN_NORMAL_MOVE);
    }

    private boolean isOdd(int roll) {
        return (roll % 2) == 1;
    }

    private void incrementDamage() {
        damage++;
    }
}
