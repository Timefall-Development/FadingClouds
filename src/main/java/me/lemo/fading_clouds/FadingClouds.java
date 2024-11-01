package me.lemo.fading_clouds;

import me.lemo.fading_clouds.gamerule.RemoveFadedBlocksGameRule;
import me.lemo.fading_clouds.registry.FadingCloudsBlocks;
import me.lemo.fading_clouds.registry.FadingCloudsEvents;
import me.lemo.fading_clouds.registry.FadingCloudsItemGroups;
import me.lemo.fading_clouds.registry.FadingCloudsItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class FadingClouds implements ModInitializer {
    public static final String MOD_ID = "fading_clouds";
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
    @Override
    public void onInitialize() {
        RemoveFadedBlocksGameRule.register();
        FadingCloudsItems.register();
        FadingCloudsBlocks.register();
        FadingCloudsItemGroups.register();
        FadingCloudsEvents.register();
    }
}
