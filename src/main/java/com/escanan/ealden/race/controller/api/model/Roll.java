package com.escanan.ealden.race.controller.api.model;

import com.escanan.ealden.race.model.SpeedType;

public class Roll {
    private SpeedType speedType;
    private Integer number;

    public Roll(String speedType, Integer roll) {
        this.speedType = SpeedType.valueOf(speedType);
        this.number = roll;
    }

    public SpeedType getSpeedType() {
        return speedType;
    }

    public Integer getNumber() {
        return number;
    }
}
