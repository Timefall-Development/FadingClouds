package me.lemo.fading_clouds.client;

import me.lemo.fading_clouds.registry.FadingCloudsBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FadingCloudsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(FadingCloudsBlocks.FADING_CLOUD, RenderLayer.getTranslucent());
    }
}
