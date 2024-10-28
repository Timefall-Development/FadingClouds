package me.lemo.fading_clouds.registry;

import me.lemo.fading_clouds.FadingClouds;
import me.lemo.fading_clouds.block.FadingCloudBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

public class FadingCloudsBlocks {
    public static final Block FADING_CLOUD = registerBlock(
            new FadingCloudBlock(AbstractBlock.Settings
                    .copy(Blocks.GLASS)
                    .ticksRandomly()
                    .sounds(BlockSoundGroup.SHROOMLIGHT)
                    .nonOpaque()
                    .registryKey(getBlockRegistryKey("fading_cloud_block"))),
            "fading_cloud_block"
    );

    public static void register(){

    }

    @SuppressWarnings("SameParameterValue")
    protected static Block registerBlock(Block block, String id){
        return Registry.register(Registries.BLOCK, FadingClouds.id(id), block);
    }

    public static RegistryKey<Block> getBlockRegistryKey(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, FadingClouds.id(id));
    }
}
