package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.states.ActiveGameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class DiscussionTask extends BukkitRunnable {
    private final ActiveGameState activeGameState;
    private final Runnable onDiscussionEnd;
    private int ticksUntilEnd;

    public DiscussionTask(ActiveGameState activeGameState, Runnable onDiscussionEnd, int ticksUntilEnd) {
        this.activeGameState = activeGameState;
        this.onDiscussionEnd = onDiscussionEnd;
        this.ticksUntilEnd = ticksUntilEnd;
    }

    @Override
    public void run() {

        int secondsUntilEnd = (int) Math.ceil(ticksUntilEnd / 20f);

        if (ticksUntilEnd <= 0) {
            activeGameState.getGame().broadcastActionBar(Component.empty());
            cancel();
            onDiscussionEnd.run();
            return;
        }


        TextComponent countDownMessage = Component.text("Discussion: " + secondsUntilEnd, NamedTextColor.WHITE);
        activeGameState.getGame().broadcastActionBar(countDownMessage);
        ticksUntilEnd--;
    }
}
