package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scheduler.BukkitRunnable;

public class VotingTask extends BukkitRunnable {

    private final Game game;
    private final Runnable onVotingEnd;
    private int ticksUntilEnd;

    private boolean isTrialEnabled;

    public VotingTask(Game game, Runnable onVotingEnd, int ticksUntilEnd) {
        this.game = game;
        this.onVotingEnd = onVotingEnd;
        this.ticksUntilEnd = ticksUntilEnd;
    }

    @Override
    public void run() {

        int secondsUntilEnd = (int) Math.ceil(ticksUntilEnd / 20f);


        int remainingTrials = 3;

        if (remainingTrials == 0) {
            cancel();
            return;
        }

        if (ticksUntilEnd <= 0) {
            game.broadcastActionBar(Component.empty());
            cancel();
            onVotingEnd.run();
            return;
        }



        TextComponent countDownMessage = Component.text("Voting: " + secondsUntilEnd, NamedTextColor.GOLD);
        game.broadcastActionBar(countDownMessage);

        ticksUntilEnd--;
    }
}
