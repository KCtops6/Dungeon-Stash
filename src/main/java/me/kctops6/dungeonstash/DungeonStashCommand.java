package me.kctops6.dungeonstash;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonStash.MODID)
public class DungeonStashCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("dungeonstash")
                        .requires(source -> true) // Allows all players to use it in survival without cheats
                        .then(Commands.literal("config")
                                .then(Commands.argument("guiMode", BoolArgumentType.bool())
                                        .executes(context -> {
                                            boolean mode = BoolArgumentType.getBool(context, "guiMode");
                                            Config.setGuiMode(mode);
                                            context.getSource().sendSuccess(() -> Component.literal("Dungeon Loot Bag GUI mode set to: " + mode), true);
                                            return 1;
                                        })
                                )
                                .executes(context -> {
                                    boolean current = Config.guiMode;
                                    context.getSource().sendSuccess(() -> Component.literal("Dungeon Loot Bag GUI mode is currently: " + current), false);
                                    return 1;
                                })
                        )
        );
    }
}