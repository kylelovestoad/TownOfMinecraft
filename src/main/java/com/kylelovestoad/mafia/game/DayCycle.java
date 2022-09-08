package com.kylelovestoad.mafia.game;

/**
 * Class used to keep track of day and nights
 */
public class DayCycle {

    private int day = 0;

    private int night = 0;

    private Time currentTime = null;

    public int getDay() {
        return day;
    }

    public int getNight() {
        return night;
    }

    public boolean isFullMoon() {
        return night != 1 && night != 3;
    }
    public void incrementDay() {
        day++;
    }

    public void incrementNight() {
        night++;
    }

    public Time getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Time currentTime) {
        this.currentTime = currentTime;
    }
}
