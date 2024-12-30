package net.blocsoc.playerswap.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;


public class GetAllPlayerPositions {

    //run the command by typing "/player find"
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("shuffle")
                .then(CommandManager.literal("player").executes(GetAllPlayerPositions::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {


        List<ServerPlayerEntity> allPlayers = context.getSource().getServer().getPlayerManager().getPlayerList();

        List<ServerPlayerEntity> survivalPlayers = new ArrayList<>();

        for (int i = 0; i < allPlayers.size(); i++) {
            if(allPlayers.get(i).interactionManager.getGameMode().toString() == "SURVIVAL"){
                survivalPlayers.add(allPlayers.get(i));
            }
        }

        Collections.shuffle(survivalPlayers);

        int startPlayers = survivalPlayers.size();

        while (!survivalPlayers.isEmpty()){

            //maybe set to if divisible by 2?
            if((survivalPlayers.size() > 3) || (survivalPlayers.size() == 2)){
                teleportPlayer(survivalPlayers.get(0), survivalPlayers.get(1));
                teleportPlayer(survivalPlayers.get(1), survivalPlayers.get(0));
                survivalPlayers.removeFirst();
                survivalPlayers.removeFirst();
            }
            else if(survivalPlayers.size() == 3){
                teleportPlayer(survivalPlayers.get(0), survivalPlayers.get(1));
                teleportPlayer(survivalPlayers.get(1), survivalPlayers.get(2));
                teleportPlayer(survivalPlayers.get(2), survivalPlayers.get(0));
                survivalPlayers.removeFirst();
                survivalPlayers.removeFirst();
                survivalPlayers.removeFirst();
            }

            else{

                context.getSource().sendFeedback(() -> (Text) Text.literal("Attempted to shuffle 1 player and errored out"), false);
                return -1;
            }

        }

        context.getSource().sendFeedback(() -> (Text) Text.literal("Successfully shuffled " + survivalPlayers + " players"), false);

        return 1;

    }

    private static void teleportPlayer(ServerPlayerEntity player1, ServerPlayerEntity player2){
        player1.teleport(player2.getServerWorld(), player2.getX(), player2.getY() + 10, player2.getZ(), EnumSet.noneOf(PositionFlag.class), player2.getYaw(), player2.getPitch(), false);
    }
}
