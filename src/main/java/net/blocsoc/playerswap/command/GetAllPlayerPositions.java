package net.blocsoc.playerswap.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;


import java.util.EnumSet;
import java.util.List;


public class GetAllPlayerPositions {

    //run the command by typing "/player find"
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("player")
                .then(CommandManager.literal("find").executes(GetAllPlayerPositions::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {


        List<ServerPlayerEntity> temp = context.getSource().getServer().getPlayerManager().getPlayerList();

        temp.getFirst().interactionManager.getGameMode();

        //need to make a list of all survival mode players, randomise it, and then swap
        return 1;

    }
}
