package com.kylelovestoad.mafia.game.roles;

import com.kylelovestoad.mafia.game.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.game.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.game.roles.roleproperties.Faction;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.format.TextColor;

public interface Role {


    Faction faction();
    String objective();
    String name();
    TextColor color();
    Sound introSound();
    Attack attackPower();
    Defense defensePower();
    boolean isUnique();

}
