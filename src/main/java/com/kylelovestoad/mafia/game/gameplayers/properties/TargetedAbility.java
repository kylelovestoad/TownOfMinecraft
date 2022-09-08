package com.kylelovestoad.mafia.game.gameplayers.properties;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;

public interface TargetedAbility extends Ability {

    void select(GamePlayer selectedPlayer);

    GamePlayer getSelected();
}
