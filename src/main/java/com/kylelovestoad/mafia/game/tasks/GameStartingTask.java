package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.states.LobbyGameState;
import com.kylelovestoad.mafia.manager.GeneralManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartingTask extends BukkitRunnable {

    private final GeneralManager generalManager;
    private final Game game;
    private final Runnable onStart;
    private int secondsUntilStart;

    public GameStartingTask(GeneralManager generalManager, Game game, Runnable onStart, int secondsUntilStart) {
        this.generalManager = generalManager;
        this.game = game;
        this.onStart = onStart;
        this.secondsUntilStart = secondsUntilStart;
    }


    @Override
    public void run() {

        if (secondsUntilStart <= 0) {
            cancel();
            onStart.run();
            return;
        }

        String suffix;
        if (secondsUntilStart == 1) {
            suffix = " second";
        } else {
            suffix = " seconds";
        }

        TextComponent countDownMessage = Component.text("Game is starting in ", NamedTextColor.RED)
                .append(Component.text(secondsUntilStart, NamedTextColor.YELLOW))
                .append(Component.text(suffix, NamedTextColor.RED));

        game.broadcastMessage(countDownMessage);

        Sound countdownSound =
                Sound.sound(Key.key("block.note_block.pling"), Sound.Source.BLOCK, 1f, 1f);
        game.broadcastSound(countdownSound);

        secondsUntilStart--;

    }
}

