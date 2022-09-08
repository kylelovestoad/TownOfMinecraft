package com.kylelovestoad.mafia.game.states;

import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.DayCycle;
import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.Time;
import com.kylelovestoad.mafia.game.gameplayers.neutral.ExecutionerPlayer;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.properties.Status;
import com.kylelovestoad.mafia.game.tasks.DiscussionTask;
import com.kylelovestoad.mafia.game.tasks.NightTask;
import com.kylelovestoad.mafia.game.tasks.RoleAssignmentAnimationTask;
import com.kylelovestoad.mafia.game.tasks.VotingTask;
import com.kylelovestoad.mafia.manager.GeneralManager;
import com.kylelovestoad.mafia.manager.RoleManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * The main {@link GameState} with the game logic. When the game is over begin the {@link EndingGameState}
 */
public class ActiveGameState extends GameState {

    private final Game game;
    private final GeneralManager generalManager;

    private final RoleManager roleManager;

    List<UUID> activePlayers;
    List<GamePlayer> gamePlayers;
    List<GamePlayer> townPlayers;
    List<GamePlayer> mafiaPlayers;
    private final BukkitScheduler scheduler;

    private final DayCycle dayCycle = new DayCycle();


    public ActiveGameState(Game game, GeneralManager generalManager) {
        this.game = game;
        this.generalManager = generalManager;
        roleManager = generalManager.getRoleManager();
        activePlayers = game.getActivePlayers();
        gamePlayers = game.getGamePlayers();
        townPlayers = game.getTownPlayers();
        mafiaPlayers = game.getMafiaPlayers();
        scheduler = generalManager.getPlugin().getServer().getScheduler();
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
         Other important actions done to a player (i.e. a roleblock or control) are shown to that player
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

        game.broadcastMessage(gameStartedMessage);
        game.broadcastSound(gameStartedSound);

        activePlayers.forEach(playerUUID ->
                Bukkit.getPlayer(playerUUID).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 9)));

        // Creates GamePlayers for each Player
        activePlayers.forEach(playerUUID ->
                game.addGamePlayer(roleManager.getRandomRoleFiltered(game).gamePlayerOf(playerUUID, game, generalManager)));

        gamePlayers.forEach(gamePlayer ->
                new RoleAssignmentAnimationTask(gamePlayer, generalManager.getRoleManager(), 30).runTaskTimer(mafiaPlugin, 0, 2));

        // Begin game loop
        scheduler.runTaskLater(generalManager.getPlugin(), () -> {
            assignRoleExtras();
            startDiscussionPhase();
        }, 60);

    }

    @Override
    public void onDisable(MafiaPlugin mafiaPlugin) {
        super.onDisable(mafiaPlugin);
    }

    private void assignRoleExtras() {
        game.getPlayersFromRole(ExecutionerPlayer.class).forEach(executionerPlayer -> {
            Random random = new Random();
            executionerPlayer.setTarget(townPlayers.get(random.nextInt(townPlayers.size())));
            game.broadcastMessage(Component.text("Executioner Target Set!"));
        });

        gamePlayers.forEach(gamePlayer -> gamePlayer.giveItems(generalManager.getPlugin()));
    }

    private void startDay() {
        dayCycle.incrementDay();
        dayCycle.setCurrentTime(Time.DAY);
        game.setTimeForActivePlayers(6000);
        game.broadcastMessage(Component.text("Day " + dayCycle.getDay(), TextColor.color(0xffae1e)));
    }

    private void startNight() {
        dayCycle.incrementNight();
        dayCycle.setCurrentTime(Time.NIGHT);
        game.setTimeForActivePlayers(18000);
        game.broadcastMessage(Component.text("Night " + dayCycle.getNight(), TextColor.color(0x2a65af)));
        if (dayCycle.isFullMoon()) {
            game.broadcastMessage(Component.text("There is a full moon out tonight.", TextColor.color(0x64e8ff)));
        }
    }

    private void startDiscussionPhase() {
        startDay();
        if (dayCycle.getDay() == 1) {
            new DiscussionTask(this, this::startVotingPhase, 200).runTaskTimer(generalManager.getPlugin(), 0, 1);
        } else {
            new DiscussionTask(this, this::startVotingPhase, 600).runTaskTimer(generalManager.getPlugin(), 0, 1);
        }
    }

    private void startVotingPhase() {
        new VotingTask(game, this::startNightPhase, 600).runTaskTimer(generalManager.getPlugin(), 0, 1);
    }

    private void startNightPhase() {
        startNight();
        new NightTask(game, this::startDiscussionPhase, 600).runTaskTimer(generalManager.getPlugin(),0,1);
    }

    public Game getGame() {
        return game;
    }

    private ItemStack getPlayerHead(GamePlayer gamePlayer) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(gamePlayer.getPlayer());
        skullMeta.displayName(gamePlayer.getPlayer().displayName());
        skull.setItemMeta(skullMeta);
        return skull;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (!event.getAction().isRightClick()) {
            return;
        }

        event.getPlayer().sendMessage(Component.text("CLICK!!!"));

        ItemStack itemStack = event.getItem();

        if (itemStack == null) {
            return;
        }

        NamespacedKey key = new NamespacedKey(generalManager.getPlugin(), "mafia_item");

        // Check for plugin made item
        if (!itemStack.getItemMeta().getPersistentDataContainer().has(key)) {
            return;
        }

        Player eventPlayer = event.getPlayer();

        GamePlayer eventGamePlayer = game.getGamePlayerFromUUID(eventPlayer.getUniqueId());

        if (eventGamePlayer == null) {
            return;
        }

        eventGamePlayer.useItem(itemStack);
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (game.isPlaying(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (game.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (game.isPlaying(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        game.removePlayer(player);

        GamePlayer gamePlayer = game.getGamePlayerFromUUID(player.getUniqueId());
        game.removeGamePlayer(gamePlayer);
        gamePlayer.setStatus(Status.QUEUED);
        event.quitMessage(Component.text(player.getName(), NamedTextColor.YELLOW)
                .append(Component.text(" left the game! They will die after the next night", NamedTextColor.RED)));
    }
}

