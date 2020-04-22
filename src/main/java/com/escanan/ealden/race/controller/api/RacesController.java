package com.escanan.ealden.race.controller.api;

import com.escanan.ealden.race.controller.api.model.Roll;
import com.escanan.ealden.race.controller.api.model.Settings;
import com.escanan.ealden.race.model.Race;
import com.escanan.ealden.race.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RacesController {
    @Autowired
    private RaceService raceService;

    @Value("${race.test.enable}")
    private boolean testMode;

    @GetMapping("/api/races")
    public Race index() {
        return raceService.getCurrentRace();
    }

    @PostMapping("/api/races/roll")
    public Race roll(@RequestBody Roll roll) {
        return raceService.roll(roll.getRoll(), roll.getSpeedType());
    }

    @PostMapping("/api/races/new")
    public Race newRace() {
        return raceService.newRace();
    }

    @GetMapping("/api/races/settings")
    public Settings settings() {
        Settings settings = new Settings();
        settings.setTestMode(testMode);

        return settings;
    }
}
