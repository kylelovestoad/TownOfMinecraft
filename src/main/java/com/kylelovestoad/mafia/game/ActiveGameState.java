package com.kylelovestoad.mafia.game;

import com.kylelovestoad.mafia.Mafia;
import com.kylelovestoad.mafia.game.tasks.RoleAssignmentAnimationTask;
import com.kylelovestoad.mafia.manager.GameManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class ActiveGameState extends GameState {

    private final GameArea gameArea;
    private final GameManager gameManager;
    public ActiveGameState(GameArea gameArea, GameManager gameManager) {
        this.gameArea = gameArea;
        this.gameManager = gameManager;
    }

    @Override
    public void onEnable(Mafia mafia) {
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

        super.onEnable(mafia);
        TextComponent gameStartedMessage = Component.text("Let the games begin...", NamedTextColor.GREEN);
        Sound gameStartedSound =
                Sound.sound(Key.key("block.note_block.pling"), Sound.Source.BLOCK, 1f, 2f);

        gameArea.broadcastMessage(gameStartedMessage);
        gameArea.broadcastSound(gameStartedSound);

        List<UUID> activePlayers = gameArea.getActivePlayers();

        activePlayers.forEach(uuid -> mafia
                .getServer()
                .getPlayer(uuid)
                .addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 9)));


        activePlayers.forEach(uuid -> new RoleAssignmentAnimationTask(mafia
                .getServer()
                .getPlayer(uuid), gameManager.getRoleManager(), 20).runTaskTimer(mafia, 0, 2));

    }

    @Override
    public void onDisable(Mafia mafia) {
        super.onDisable(mafia);
    }
}
