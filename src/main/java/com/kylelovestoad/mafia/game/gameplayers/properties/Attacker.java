package com.kylelovestoad.mafia.game.gameplayers.properties;

import com.kylelovestoad.mafia.game.gameplayers.GamePlayer;
import net.kyori.adventure.text.TextComponent;

public interface Attacker {

    void attack(GamePlayer gamePlayer);

    TextComponent killMessage();

}
