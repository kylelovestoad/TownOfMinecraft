package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimerTask extends BukkitRunnable {
    private final Runnable onCycle;
    private final Runnable onEnd;
    private int secondsUntilEnd;


    public GameTimerTask(Runnable onCycle, Runnable onEnd, int secondsUntilEnd) {
        this.onCycle = onCycle;
        this.onEnd = onEnd;
        this.secondsUntilEnd = secondsUntilEnd;
    }


    @Override
    public void run() {

        if (secondsUntilEnd == 0) {
            onEnd.run();
            this.cancel();
            return;
        }

        onCycle.run();
        secondsUntilEnd--;
    }
}

