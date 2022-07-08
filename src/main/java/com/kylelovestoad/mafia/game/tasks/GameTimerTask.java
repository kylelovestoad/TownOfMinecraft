package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.states.GameArea;
import com.kylelovestoad.mafia.manager.GameManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimerTask extends BukkitRunnable {
    private final GameArea gameArea;

    private final String heading;
    private final Runnable onEnd;
    private int secondsUntilEnd;


    public GameTimerTask(GameArea gameArea, String heading, Runnable onEnd, int secondsUntilEnd) {
        this.gameArea = gameArea;
        this.heading = heading;
        this.onEnd = onEnd;
        this.secondsUntilEnd = secondsUntilEnd;
    }


    @Override
    public void run() {

        if (secondsUntilEnd <= 0) {
            cancel();
            onEnd.run();
            return;
        }

        TextComponent countDownMessage = Component.text(heading + ": ", NamedTextColor.RED)
                .append(Component.text(secondsUntilEnd, NamedTextColor.YELLOW));

        gameArea.broadcastMessage(countDownMessage);
        secondsUntilEnd--;

    }
}

