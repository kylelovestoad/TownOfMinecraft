package com.kylelovestoad.mafia.game.gameplayers.properties;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;

/**
 * The status of a {@link GamePlayer}
 */
public enum Status {

    /**
     * The {@link GamePlayer} is alive
     */
    ALIVE,

    /**
     * The {@link GamePlayer} is queued to be dead at the next day.
     * This is to allow the start of the discussion state to show dead {@link GamePlayer}s
     */
    QUEUED,

    /**
     * The {@link GamePlayer} is dead
     */
    DEAD,
}
