package com.kylelovestoad.mafia.manager;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GuiManager {

    private GeneralManager generalManager;

    public GuiManager(GeneralManager generalManager) {
        this.generalManager = generalManager;
    }

    /**
     *
     * @param displayName The name shown when opening the gui
     * @param rows The amount of rows the gui has
     * @return
     */
    public ChestGui createPlayerListGui(List<GamePlayer> gamePlayers, String displayName, int rows) {

        // Rows must be 2 or greater in order to fit the pager at the bottom.
        // Rows must not exceed 6 as that is the limit for how big a chest gui can be.
        if (rows < 2 || rows > 6) {
            throw new IllegalArgumentException("number of rows must be between 2 and 6");
        }

        ChestGui gui = new ChestGui(rows, displayName);
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        PaginatedPane pagePanel = new PaginatedPane(9, gui.getRows() - 1);

        List<GuiItem> guiItems = gamePlayers.stream().map(gamePlayer -> {
            GuiItem guiItem = new GuiItem(getPlayerHead(gamePlayer));
            return guiItem;
        }).collect(Collectors.toList());

        pagePanel.populateWithGuiItems(guiItems);

        // Used to give items their symbol as their display name
        BiFunction<Character, ItemStack, GuiItem> arrowBiFunction = (character, item) -> {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.displayName(Component.text(character));
            item.setItemMeta(itemMeta);
            return new GuiItem(item);
        };


        Label leftArrow = new Label(3, gui.getRows() - 1, 1, 1, Font.STONE);
        leftArrow.setText("←", arrowBiFunction);

        // Left arrow is invisible because the user doesn't need to go back a page when they are at the first page
        leftArrow.setVisible(false);

        Label rightArrow = new Label(5, gui.getRows() - 1, 1, 1, Font.STONE);
        rightArrow.setText("→", arrowBiFunction);

        int pages = pagePanel.getPages();

        // If there is only one page there is no need for arrows
        if (pages <= 1) {
            rightArrow.setVisible(false);
        }



        leftArrow.setOnClick(event -> {

            // Needs this check so that the user cannot keep clicking the invisible label
            // advancing the page number past the bounds
            // The same applies to the right arrow
            if (leftArrow.isVisible()) {
                pagePanel.setPage(pagePanel.getPage() - 1);
                rightArrow.setVisible(true);
            }
            if (pagePanel.getPage() <= 0) {
                leftArrow.setVisible(false);
            }

            gui.update();
        });

        rightArrow.setOnClick(event -> {

            if (rightArrow.isVisible()) {
                leftArrow.setVisible(true);
                pagePanel.setPage(pagePanel.getPage() + 1);
            }
            if (pagePanel.getPage() >= pages - 1) {
                rightArrow.setVisible(false);
            }

            gui.update();
        });

        gui.addPane(pagePanel);
        gui.addPane(leftArrow);
        gui.addPane(rightArrow);

        return gui;
    }

    /**
     * @param gamePlayer the {@link GamePlayer} whose head is being returned
     * @return the head that the {@link GamePlayer} has as an {@link ItemStack}
     */
    public ItemStack getPlayerHead(GamePlayer gamePlayer) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(gamePlayer.getPlayer());
        skullMeta.displayName(gamePlayer.getPlayer().displayName());
        skull.setItemMeta(skullMeta);
        return skull;
    }

    /**
     *
     * @param event The {@link InventoryClickEvent} event
     * @return The {@link UUID} of the {@link Player} that owns the skull being clicked
     */
    public UUID getPlayerUUIDFromGuiClick(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        return getPlayerUUIDFromItem(clickedItem);
    }

    private UUID getPlayerUUIDFromItem(ItemStack itemStack) {
        // This check is needed so the item clicked has to be a player head
        if (itemStack == null || !itemStack.getType().equals(Material.PLAYER_HEAD)) {
            return null;
        }

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        return Objects.requireNonNull(skullMeta.getOwningPlayer()).getUniqueId();
    }
}
