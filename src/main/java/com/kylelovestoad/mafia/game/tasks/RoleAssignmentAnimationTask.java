package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.manager.RoleManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;

public class RoleAssignmentAnimationTask extends BukkitRunnable {

    private final GamePlayer gamePlayer;
    private final RoleManager roleManager;
    private int animationTime;

    public RoleAssignmentAnimationTask(GamePlayer gamePlayer, RoleManager roleManager, int animationTime) {
        this.gamePlayer = gamePlayer;
        this.roleManager = roleManager;
        this.animationTime = animationTime;
    }

    @Override
    public void run() {

        if (animationTime <= 0) {
            this.cancel();
            return;
        }

        Role role;
        final TextComponent roleTitle;
        final Title.Times times;
        TextComponent roleSubtitle;
        Sound sound;

        if (animationTime != 1) {
            role = roleManager.getRandomRole();
            roleTitle = Component.text(role.name(), role.color());
            roleSubtitle = Component.text("", NamedTextColor.WHITE);
            times = Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(500), Duration.ZERO);
            sound = Sound.sound(Key.key("block.bamboo.break"), Sound.Source.BLOCK, 1f, 1.2f);
        } else {
            // Chooses final role
            role = gamePlayer.getRole();
            roleTitle = Component.text(role.name(), role.color());
            roleSubtitle = Component.text(role.objective(), NamedTextColor.WHITE);
            times = Title.Times.times(Duration.ofMillis(100), Duration.ofMillis(3000), Duration.ofMillis(500));
            sound = role.introSound();
        }

        final Title animationTitle = Title.title(roleTitle, roleSubtitle, times);

        gamePlayer.getPlayer().showTitle(animationTitle);

        gamePlayer.getPlayer().playSound(sound);

        animationTime--;
    }
}
