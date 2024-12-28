package net.blocsoc.playerswap.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;


import java.util.EnumSet;


public class GetAllPlayerPositions {

    //run the command by typing "/player find"
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("player")
                .then(CommandManager.literal("find").executes(GetAllPlayerPositions::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        context.getSource().getServer().getPlayerManager().getPlayerList().forEach(player -> {
            player.teleport(player.getServerWorld(), player.getX(), player.getY() + 10, player.getZ(), EnumSet.noneOf(PositionFlag.class), player.getYaw(), player.getPitch(), false);
        });

        return 1;

    }
}
