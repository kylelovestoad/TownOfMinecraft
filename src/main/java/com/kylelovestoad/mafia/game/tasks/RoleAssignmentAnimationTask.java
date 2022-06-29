package com.kylelovestoad.mafia.game.tasks;

import com.kylelovestoad.mafia.manager.RoleManager;
import com.kylelovestoad.mafia.roles.Role;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class RoleAssignmentAnimationTask extends BukkitRunnable {

    private final Player player;
    private final RoleManager roleManager;

    private int animationAmount;

    public RoleAssignmentAnimationTask(Player player, RoleManager roleManager, int animationAmount) {
        this.player = player;
        this.roleManager = roleManager;
        this.animationAmount = animationAmount;
    }

    @Override
    public void run() {

        if (animationAmount <= 0) {
            this.cancel();
            return;
        }

        Random random = new Random();
        List<Role> roles = roleManager.getRoles();
        Role randomRole = roles.get(random.nextInt(roles.size()));


        TextComponent roleTitle = Component.text(randomRole.name(), randomRole.color());
        TextComponent roleSubtitle;
        final Title.Times times;

        if (animationAmount != 1) {
            roleSubtitle = Component.text("", NamedTextColor.WHITE);
            times = Title.Times.times(Duration.ZERO, Duration.ofMillis(500), Duration.ZERO);
        } else {
            // Chooses final role
            roleSubtitle = Component.text(randomRole.objective(), NamedTextColor.WHITE);
            times = Title.Times.times(Duration.ZERO, Duration.ofMillis(3000), Duration.ofMillis(500));
            roleManager.createMafiaPlayer(player, randomRole);
        }


        final Title animationTitle = Title.title(roleTitle, roleSubtitle, times);

        player.showTitle(animationTitle);

        Sound sound =
                Sound.sound(Key.key("block.bamboo.break"), Sound.Source.BLOCK, 1f, 1f);

        player.playSound(sound);

        animationAmount--;
    }
}
