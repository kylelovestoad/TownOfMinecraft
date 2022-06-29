package com.kylelovestoad.mafia.roles;

import com.kylelovestoad.mafia.roles.roleproperties.Attack;
import com.kylelovestoad.mafia.roles.roleproperties.Defense;
import com.kylelovestoad.mafia.roles.roleproperties.Faction;
import net.kyori.adventure.text.format.TextColor;

public interface Role {

    Faction faction();

    String objective();

    String name();

    TextColor color();

    String description();

    Attack attackPower();

    Defense defensePower();

}
