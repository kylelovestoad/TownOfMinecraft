package com.kylelovestoad.mafia.game.gameplayers.neutral;

import com.kylelovestoad.mafia.game.Game;
import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import com.kylelovestoad.mafia.game.gameplayers.properties.Ability;
import com.kylelovestoad.mafia.game.gameplayers.properties.TargetedAbility;
import com.kylelovestoad.mafia.game.roles.JesterRole;
import com.kylelovestoad.mafia.manager.GeneralManager;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class JesterPlayer extends NeutralPlayer implements TargetedAbility {

    GamePlayer selectedPlayer;
    public JesterPlayer(UUID playerUUID, JesterRole role, Game game, GeneralManager generalManager) {
        super(playerUUID, role, game, generalManager);
    }

    @Override
    NeutralAlignment getNeutralAlignment() {
        return NeutralAlignment.CHAOS;
    }

    @Override
    public void useAbility() {

    }

    @Override
    public void select(GamePlayer selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    @Override
    public GamePlayer getSelected() {
        return selectedPlayer;
    }

    @Override
    public void useItem(ItemStack itemStack) {
        super.useItem(itemStack);


    }
}
