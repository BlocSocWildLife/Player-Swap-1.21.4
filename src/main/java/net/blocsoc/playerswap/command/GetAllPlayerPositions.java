package net.blocsoc.playerswap.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;


public class GetAllPlayerPositions {

    //run the command by typing "/player find"
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("player")
                .then(CommandManager.literal("find").executes(GetAllPlayerPositions::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        context.getSource().getServer().getPlayerManager().getPlayerList().forEach(player -> {
            context.getSource().sendFeedback(() -> (Text) Text.literal(player.getName() + "exists"), false);
        });
        return 1;

    }
}
