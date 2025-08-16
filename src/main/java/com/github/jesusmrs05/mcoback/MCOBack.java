package com.github.jesusmrs05.mcoback;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class MCOBack {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    public static String coordinates = "";
    public static String dimension = "";

    private static final Set<String> TRACKED_COMMANDS = new HashSet<>(Arrays.asList(
        "tp", "tpto", "goto", "teleport", "spawn",
        "warp", "randomwarp", "rules", "acceptsummon", "home", "wilderness"
    ));

    /**
     * <a href="https://cleanroommc.com/wiki/forge-mod-development/event#overview">
     *     Take a look at how many FMLStateEvents you can listen to via the @Mod.EventHandler annotation here
     * </a>
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new BackCommand());
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        String msg = event.getMessage();

        if (msg.startsWith("/")) {

            String[] parts = msg.substring(1).split(" ");
            if (parts.length != 0) {

                String commandName = parts[0].toLowerCase();

                if (TRACKED_COMMANDS.contains(commandName)) {
                    EntityPlayerSP player = Minecraft.getMinecraft().player;
                    if (player != null) {

                        coordinates = String.format("%d %d %d",
                            (int) Math.floor(player.posX),
                            (int) Math.floor(player.posY),
                            (int) Math.floor(player.posZ));

                        int dimId = player.dimension;
                        if (dimId == DimensionType.NETHER.getId()) {
                            dimension = "nether";
                        } else if (dimId == DimensionType.THE_END.getId()) {
                            dimension = "end";
                        } else {
                            dimension = "overworld";
                        }

                        LOGGER.info("Saved position {} in {}", coordinates, dimension);
                    }
                }
            }
        }
    }
}
