package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.GamePlayer;
import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.roles.ExecutionerRole;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.game.tasks.RoleAssignmentAnimationTask;
import com.kylelovestoad.mafia.manager.GameManager;
import com.kylelovestoad.mafia.manager.RoleManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;

public class ActiveGameState extends GameState {

    private final GameArea gameArea;
    private final GameManager gameManager;

    private final RoleManager roleManager;

    List<Player> activePlayers;
    List<GamePlayer<? extends Role>> gamePlayers;
    List<GamePlayer<? extends Role>> townPlayers;
    List<GamePlayer<? extends Role>> mafiaPlayers;

    private Phase currentPhase = null;
    private int dayNum = 0;
    private int nightNum = 0;

    public ActiveGameState(GameArea gameArea, GameManager gameManager) {
        this.gameArea = gameArea;
        this.gameManager = gameManager;
        roleManager = gameManager.getRoleManager();
        activePlayers = gameArea.getActivePlayers();
        gamePlayers = roleManager.getGamePlayers();
        townPlayers = roleManager.getTownPlayers();
        mafiaPlayers = roleManager.getMafiaPlayers();
    }

    private enum Phase {
        DAY,
        NIGHT
    }


    @Override
    public void onEnable(MafiaPlugin mafiaPlugin) {
        /*
        TODO: Discussion
         Killed players are revealed, it shows what role they were and what role they were killed by
         Timer starts for discussion
         Gamemaster might be able to stop timer?
         Story time for how players were killed?
         */

        /*
        TODO: Night
         Players with night abilities can use abilities
         Timer starts
         Members of evil factions can speak to each other
         */

        /*
        TODO: After Night
         Killed players are shown that they are killed
         Other important actions done to a player (i.e. a roleblock or mind control) are shown to that player
         */

         /*
        TODO: Voting
         Start voting for players
         Voted players are killed, and their roles are revealed
         */

        super.onEnable(mafiaPlugin);
        TextComponent gameStartedMessage = Component.text("Let the games begin...", NamedTextColor.GREEN);
        Sound gameStartedSound =
                Sound.sound(Key.key("block.note_block.pling"), Sound.Source.BLOCK, 1f, 2f);

        gameArea.broadcastMessage(gameStartedMessage);
        gameArea.broadcastSound(gameStartedSound);

        activePlayers.forEach(player -> roleManager.assignRole(player, roleManager.getRandomRoleFiltered()));

        activePlayers.forEach(player ->
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 9)));


        BukkitScheduler scheduler = gameManager.getPlugin().getServer().getScheduler();

        // Creates GamePlayers for each Player
        scheduler.runTask(mafiaPlugin, () -> gamePlayers.forEach(gamePlayer ->
                new RoleAssignmentAnimationTask(gamePlayer, gameManager.getRoleManager(), 30)
                        .runTaskTimer(mafiaPlugin, 0, 2)));


        scheduler.runTaskLater(gameManager.getPlugin(), () -> {
            roleManager.getPlayersFromRole(ExecutionerRole.class).forEach(executionerPlayer -> {
                Random random = new Random();
                ExecutionerRole executionerRole = executionerPlayer.getRole();
                executionerRole.setTarget(townPlayers.get(random.nextInt(townPlayers.size())));
                gameArea.broadcastMessage(Component.text("Executioner Target Set!"));
            });
        }, 60);
    }

    @Override
    public void onDisable(MafiaPlugin mafiaPlugin) {
        super.onDisable(mafiaPlugin);
    }

    public void gameLoop() {
        startDay();
    }

    public void startDay() {
        dayNum++;
        currentPhase = Phase.DAY;
        gameArea.setTimeForActivePlayers(6000);
        gameArea.broadcastMessage(Component.text("Day " + dayNum, TextColor.color(0xffae1e)));
    }

    public void startNight() {
        nightNum++;
        currentPhase = Phase.NIGHT;
        gameArea.setTimeForActivePlayers(18000);
        gameArea.broadcastMessage(Component.text("Night " + nightNum, TextColor.color(0x2a65af)));
        if (nightNum != 1 && nightNum != 3) {
            gameArea.broadcastMessage(Component.text("There is a full moon out tonight.", TextColor.color(0x64e8ff)));
        }
    }
}

