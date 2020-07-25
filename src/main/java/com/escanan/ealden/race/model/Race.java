package com.escanan.ealden.race.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Race {
    private static final int MAX_ROLL = 6;
    private static final int DEFAULT_FINISH_LINE = 10;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "race", cascade = ALL, fetch = EAGER)
    @OrderBy("id")
    private List<Racer> racers = new ArrayList<>();

    @ManyToOne
    private Racer currentRacer;

    @OneToMany(mappedBy = "race", cascade = ALL, fetch = EAGER)
    @OrderBy("id")
    private List<Roll> rolls = new ArrayList<>();

    @ManyToOne
    private Roll lastRoll;

    private int finishLine = DEFAULT_FINISH_LINE;

    public Race() {

    }

    public Race(List<Racer> racers) {
        this.racers = racers;
    }

    public Race addRacer(Racer racer) {
        racers.add(racer);

        racer.setRace(this);
        racer.setRank(racers.size());

        if (currentRacer == null) {
            currentRacer = racer;
        }

        return this;
    }

    public void roll(SpeedType speedType) {
        int roll = new Random().nextInt(MAX_ROLL) + 1;

        roll(roll, speedType);
    }

    public void roll(int roll, SpeedType speedType) {
        if (currentRacer != null) {
            currentRacer.roll(roll, speedType);

            lastRoll = currentRacer.getLastRoll();

            nextRacer();
        }
    }

    private void nextRacer() {
        int nextRank = ((currentRacer.getRank() % racers.size()) + 1);

        for (Racer racer : racers) {
            if (nextRank == racer.getRank()) {
                currentRacer = racer;

                break;
            }
        }
    }

    public String getMessage() {
        if ((currentRacer != null) && (getLastRoll() == null)) {
            return format("Time to RACE!  %s rolls first!", currentRacer.getName());
        } else if (isAllCrashed()) {
            return "All racers CRASHED!!!  This race is over!";
        } else if (isOver()) {
            return format("%s wins the race!  Congratulations!!!", lastRoll.getRacer().getName());
        } else if ((lastRoll != null) && lastRoll.getRacer().isCrashed()) {
            return format("%s chose %s speed, and rolled %d and moved %d.  %s CRASHED!!!  %s rolls next!",
                    lastRoll.getRacer().getName(),
                    lastRoll.getSpeedType().toString().toUpperCase(),
                    lastRoll.getRoll(),
                    lastRoll.getMove(),
                    lastRoll.getRacer().getName(),
                    currentRacer.getName());
        } else if ((lastRoll != null) && (lastRoll.getSpeedType() == SpeedType.NORMAL) && (lastRoll.getRacer().getDamage() > 0)) {
            return format("%s chose %s speed, and rolled %d and moved %d.  %s has %d damage.  %s rolls next!",
                    lastRoll.getRacer().getName(),
                    lastRoll.getSpeedType().toString().toUpperCase(),
                    lastRoll.getRoll(),
                    lastRoll.getMove(),
                    lastRoll.getRacer().getName(),
                    lastRoll.getNewDamage(),
                    currentRacer.getName());
        } else {
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public List<Racer> getRacers() {
        return racers;
    }

    public Racer getCurrentRacer() {
        return currentRacer;
    }

    public int getFinishLine() {
        return finishLine;
    }

    public Roll getLastRoll() {
        return lastRoll;
    }

    public boolean isOver() {
        for (Racer racer : racers) {
            if (racer.isWinner()) {
                return true;
            }
        }

        return isAllCrashed();
    }

    public boolean isAllCrashed() {
        boolean allCrashed = !racers.isEmpty();

        for (Racer racer : racers) {
            allCrashed = (allCrashed && racer.isCrashed());
        }

        return allCrashed;
    }

    public void setFinishLine(int finishLine) {
        this.finishLine = finishLine;
    }
}
