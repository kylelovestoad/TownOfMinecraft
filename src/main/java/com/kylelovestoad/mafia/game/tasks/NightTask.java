package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class NightTask extends BukkitRunnable {
    private final Game game;
    private final Runnable nightEnd;
    private int ticksUntilEnd;

    public NightTask(Game game, Runnable nightEnd, int ticksUntilEnd) {
        this.game = game;
        this.nightEnd = nightEnd;
        this.ticksUntilEnd = ticksUntilEnd;
    }

    @Override
    public void run() {

        int secondsUntilEnd = (int) Math.ceil(ticksUntilEnd / 20f);

        if (ticksUntilEnd <= 0) {
            game.broadcastActionBar(Component.empty());
            cancel();
            nightEnd.run();
            return;
        }


        TextComponent countDownMessage = Component.text("Night: " + secondsUntilEnd, NamedTextColor.DARK_BLUE);
        game.broadcastActionBar(countDownMessage);
        ticksUntilEnd--;
    }
}
