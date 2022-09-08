package com.kylelovestoad.mafia.game.gameplayers.neutral;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.kylelovestoad.mafia.MafiaPlugin;
import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.properties.*;
import com.kylelovestoad.mafia.game.roles.ArsonistRole;
import com.kylelovestoad.mafia.game.roles.properties.Attack;
import com.kylelovestoad.mafia.manager.ConfigurationManager;
import com.kylelovestoad.mafia.manager.GeneralManager;
import com.kylelovestoad.mafia.manager.GuiManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

public class ArsonistPlayer extends NeutralPlayer implements RoleBlockable, Attacker {

    private boolean roleBlocked = false;

    /* TODO not sure if this is the best approach for abilities since there is so much code to define one.
    *   Maybe each ability should be defined as it's own implementation instead of a builder
    */
    private final NamedTargetedAbility arsonistAbility = new NamedTargetedAbility.Builder("Douse")
            .onUse(selectedPlayer -> {
                if (selectedPlayer.equals(this)) {
                    getGame().getGamePlayers().stream().filter(GamePlayer::isDoused).forEach(this::attack);
                } else {
                    selectedPlayer.addVisitor(this);
                    selectedPlayer.setDoused(true);
                }
            }).onSelect(selectedPlayer -> {
                if (selectedPlayer.equals(this)) {
                    getPlayer().sendMessage(Component.text("You have decided to ignite tonight."));
                } else {
                    String selectedPlayerName = selectedPlayer.getPlayer().getName();
                    getPlayer().sendMessage(Component.text("You have decided to douse " + selectedPlayerName + " tonight" ));
                }
            })
            .build();
    public ArsonistPlayer(UUID playerUUID, ArsonistRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }

    @Override
    public void giveItems(MafiaPlugin mafiaPlugin) {
        final TextComponent arsonistAbilityItemName = Component.text(arsonistAbility.name(), this.color());

        getGamePlayerItemsMap().put(Material.FLINT_AND_STEEL, arsonistAbilityItemName);

        super.giveItems(mafiaPlugin);
    }

    @Override
    public void useItem(ItemStack itemStack) {



        super.useItem(itemStack);

        TextComponent displayName = (TextComponent) itemStack.getItemMeta().displayName();

        ConfigurationManager configurationManager = getGeneralManager().getConfigurationManager();

        ConfigurationSection itemsSection = configurationManager.getConfig("general.yml").getConfigurationSection("item_names");

        GuiManager guiManager = getGeneralManager().getGuiManager();

        if (displayName.content().equals(itemsSection.getString("arsonist_ability"))) {


            ChestGui gui = guiManager.createPlayerListGui(getGame().getGamePlayers(), "Choose who to douse", 3);

            // Get the 0th index in order to get the paginated pane with all the player heads
            gui.getPanes().get(0).getItems().forEach(guiItem -> guiItem.setAction(event -> {
                UUID clickedPlayerUUID = guiManager.getPlayerUUIDFromGuiClick(event);
                arsonistAbility.select(getGame().getGamePlayerFromUUID(clickedPlayerUUID));
                ItemMeta itemMeta = guiItem.getItem().getItemMeta();
                itemMeta.lore(Collections.singletonList(Component.text("SELECTED!")));
            }));

            gui.update();
        }
    }

    @Override
    public void setRoleBlocked(boolean roleBlocked) {
        this.roleBlocked = roleBlocked;
    }

    @Override
    public boolean isRoleBlocked() {
        return roleBlocked;
    }

    @Override
    public void attack(GamePlayer gamePlayer) {
        gamePlayer.dealAttack(Attack.UNSTOPPABLE, this);
    }

    @Override
    public TextComponent killMessage() {
        return Component.text("was ignited by an ").append(Component.text("Arsonist", getRole().color()));
    }

    @Override
    NeutralAlignment getNeutralAlignment() {
        return NeutralAlignment.KILLING;
    }

}
