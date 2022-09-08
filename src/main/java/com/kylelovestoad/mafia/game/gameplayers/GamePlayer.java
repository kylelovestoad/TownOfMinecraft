package com.kylelovestoad.mafia.game.gameplayers;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.properties.Attacker;
import com.kylelovestoad.mafia.game.gameplayers.properties.Status;
import com.kylelovestoad.mafia.game.roles.Role;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.game.roles.properties.Defense;
import com.kylelovestoad.mafia.game.roles.properties.Faction;
import com.kylelovestoad.mafia.manager.ConfigurationManager;
import com.kylelovestoad.mafia.manager.GeneralManager;
import com.kylelovestoad.mafia.manager.GuiManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a player in the mafia mini-game
 */
public abstract class GamePlayer {

    private final UUID playerUUID;

    private final Role role;
    private final Game game;
    private final GeneralManager generalManager;
    private final List<GamePlayer> visitors = new ArrayList<>();

    private final HashMap<Material, TextComponent> gamePlayerItemsMap = new HashMap<>();
    private boolean jailed = false;
    private boolean framed = false;
    private boolean blackmailed = false;

    private boolean doused = false;

    private boolean won = false;

    private Defense currentDefense = null;

    private Status status = Status.ALIVE;

    private List<TextComponent> deathMessages = new ArrayList<>();

    /**
     *
     * @param playerUUID The {@link UUID} for the player this is
     * @param role The {@link Role} that this has
     * @param game The {@link Game} that this is in
     * @param generalManager The {@link GeneralManager} that this is using
     */
    public GamePlayer(UUID playerUUID, Role role, Game game, GeneralManager generalManager) {
        this.playerUUID = playerUUID;
        this.role = role;
        this.game = game;
        this.generalManager = generalManager;
    }

    public Faction faction() {
        return role.faction();
    }

    public String objective() {
        return role.objective();
    }

    public String name() {
        return role.name();
    }

    public TextColor color() {
        return role.color();
    }

    public Sound introSound() {
        return role.introSound();
    }

    public boolean isUnique() {
        return role.isUnique();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Role getRole() {
        return role;
    }

    public Game getGame() {
        return game;
    }

    public GeneralManager getGeneralManager() {
        return generalManager;
    }

    public boolean isJailed() {
        return jailed;
    }

    public boolean isFramed() {
        return framed;
    }

    public boolean isBlackmailed() {
        return blackmailed;
    }

    public boolean isDoused() {
        return doused;
    }


    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public void setFramed(boolean framed) {
        this.framed = framed;
    }

    public void setBlackmailed(boolean blackmailed) {
        this.blackmailed = blackmailed;
    }

    public void setDoused(boolean doused) {
        this.doused = doused;
    }

    public void addVisitor(GamePlayer visitor) {
        visitors.add(visitor);
    }

    public List<GamePlayer> getVisitors() {
        return visitors;
    }


    public boolean hasWon() {
        return won;
    }

    public void win() {
        won = true;
    }
    public boolean isFaction(Faction faction) {
        return role.faction().equals(faction);
    }

    public <T extends Role> boolean isRole(Class<T> roleClass) {
        return role.getClass().equals(roleClass);
    }

    public void setCurrentDefense(Defense defense) {
        this.currentDefense = defense;
    }


    public HashMap<Material, TextComponent> getGamePlayerItemsMap() {
        return gamePlayerItemsMap;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    /**
     * Gives special in-game items to this {@link GamePlayer} to be used with {@link GamePlayer#useItem(ItemStack)}
     * @param mafiaPlugin The plugin instance. Used to create the {@link NamespacedKey} to distinguish the items from others
     */
    public void giveItems(MafiaPlugin mafiaPlugin) {

        ConfigurationManager configurationManager = generalManager.getConfigurationManager();
        ConfigurationSection itemsSection = configurationManager.getConfig("general.yml").getConfigurationSection("item_names");

        if (itemsSection == null) {
            mafiaPlugin.getLogger().severe("item_names doesn't exist!");
        }

        TextComponent alivePlayersComponent = Component.text(Objects.requireNonNull(itemsSection.getString("alive_players_checker")));
        TextComponent graveyardPlayersComponent = Component.text(Objects.requireNonNull(itemsSection.getString("graveyard_players_checker")));

        getGamePlayerItemsMap().put(Material.PLAYER_HEAD, alivePlayersComponent);
        getGamePlayerItemsMap().put(Material.ZOMBIE_HEAD, graveyardPlayersComponent);

        // Each item created and added to the map is given a special tag to distinguish it from other items if they
        // have similar names
        getGamePlayerItemsMap().forEach(((material, textComponent) -> {
            ItemStack itemStack = new ItemStack(material);
            ItemMeta itemMeta = itemStack.getItemMeta();
            NamespacedKey key = new NamespacedKey(mafiaPlugin, "mafia_item");
            itemMeta.displayName(textComponent);
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            itemStack.setItemMeta(itemMeta);
            getPlayer().getInventory().addItem(itemStack);
        }));
    }

    /**
     * Uses the {@link ItemStack} function on right click based on the display name
     * @param itemStack the item being used
     */
    public void useItem(ItemStack itemStack) {

        TextComponent displayName = (TextComponent) itemStack.getItemMeta().displayName();

        ConfigurationManager configurationManager = generalManager.getConfigurationManager();

        ConfigurationSection itemsSection =  configurationManager.getConfig("general.yml").getConfigurationSection("item_names");

        GuiManager guiManager = generalManager.getGuiManager();

        if (displayName.content().equals(itemsSection.getString("alive_players_checker"))) {
            guiManager.createPlayerListGui(getGame().getGamePlayers(), "Alive Players", 3).show(getPlayer());
        }

        if (displayName.content().equals(itemsSection.getString("graveyard_players_checker"))) {
            guiManager.createPlayerListGui(getGame().getGraveyardGamePlayers(), "Graveyard Players", 3).show(getPlayer());
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Defense getCurrentDefense() {
        return currentDefense;
    }

    public List<TextComponent> getDeathMessages() {
        return deathMessages;
    }

    /**
     * Deal an {@link Attack} to this. If the power of the {@link Attack} is equal or higher
     * than the {@link Defense} of this {@link GamePlayer}, kill this {@link GamePlayer}
     * @param attack The {@link Attack} type dealt
     * @param attacker The {@link GamePlayer} that is dealing the {@link Attack}
     */
    public void dealAttack(Attack attack, Attacker attacker) {
        if (attack.getPower() > getCurrentDefense().getPower()) {
            setStatus(Status.QUEUED);
            game.sendMessage(Component.text("You were attacked.", NamedTextColor.DARK_RED), this);
            game.showTitle(Component.text("You have died!", NamedTextColor.DARK_RED), this);
            deathMessages.add(Component.text(getPlayer().getName()).append(Component.space()).append(attacker.killMessage()));
        } else {
            game.sendMessage(Component.text("You were attacked, but your defense was too strong", NamedTextColor.DARK_RED), this);
        }
    }
}
