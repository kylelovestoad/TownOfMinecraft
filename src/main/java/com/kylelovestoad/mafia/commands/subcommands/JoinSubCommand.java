package com.kylelovestoad.mafia.commands.subcommands;

import org.bukkit.entity.Player;

public class JoinSubCommand implements SubCommand<Player> {

    @Override
    public void execute(Player player, String[] args) {

    }

    @Override
    public String name() {
        return "join";
    }
}
