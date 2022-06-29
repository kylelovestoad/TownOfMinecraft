package com.kylelovestoad.mafia.commands.subcommands;

import org.bukkit.command.CommandSender;

public interface SubCommand<T extends CommandSender> {
    void execute(T commandSender, String[] args);

    String name();
}
