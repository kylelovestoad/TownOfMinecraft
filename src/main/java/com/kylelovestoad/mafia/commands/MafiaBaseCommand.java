package com.kylelovestoad.mafia.commands;

import com.kylelovestoad.mafia.commands.subcommands.*;
import com.kylelovestoad.mafia.game.ActiveGameState;
import com.kylelovestoad.mafia.game.GameArea;
import com.kylelovestoad.mafia.game.LobbyGameState;
import com.kylelovestoad.mafia.game.StartingGameState;
import com.kylelovestoad.mafia.manager.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MafiaBaseCommand implements CommandExecutor {

    private final GameManager gameManager;

    private final List<SubCommand<?>> subCommands = new ArrayList<>();

    public MafiaBaseCommand(GameManager gameManager) {
        this.gameManager = gameManager;
        subCommands.add(new ConfigSubCommand());
        subCommands.add(new GameAreaSubCommand());
        subCommands.add(new JoinSubCommand());
        subCommands.add(new LeaveSubCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /*
        TODO: create a command that allows players to join, as well as an actual activator for the start game state
         */

        GameArea gameArea = new GameArea("Test", gameManager);
        Player player = sender.getServer().getPlayer("kylelovestoad");
        gameArea.addPlayer(player);
        gameArea.setGameState(new StartingGameState(gameArea, gameManager));

//        if (args.length == 0) {
//            TextComponent noArgsMessage = Component.text("/mafia <config|join|leave>", NamedTextColor.RED);
//            sender.sendMessage(noArgsMessage);
//            return true;
//        }
//
//        String subCommandArg = args[0];
//
//        Optional<SubCommand<?>> subCommandOptional = subCommands.stream()
//                .filter(subCommand -> subCommandArg.equals(subCommand.name()))
//                .findFirst();
//
//        if (!subCommandOptional.isPresent()) {
//            TextComponent noExistingSubCommandMessage =
//                    Component.text("/mafia <config|join|leave>", NamedTextColor.RED);
//            sender.sendMessage(noExistingSubCommandMessage);
//            return true;
//        }
//
//        SubCommand<?> subCommand = subCommandOptional.get();


        return true;
    }
}
