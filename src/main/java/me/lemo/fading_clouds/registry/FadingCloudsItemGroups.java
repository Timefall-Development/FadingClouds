package me.lemo.fading_clouds.registry;

import me.lemo.fading_clouds.FadingClouds;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class FadingCloudsItemGroups {
    public static final RegistryKey<ItemGroup> FADING_CLOUDS_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP,
            FadingClouds.id("fading_clouds_group_key")
    );

    public static final ItemGroup FADING_CLOUDS_ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(FadingCloudsItems.FADING_CLOUD_ITEM))
            .displayName(Text.translatable("itemGroup." + FadingClouds.MOD_ID))
            .entries(((displayContext, entries) -> {
                entries.add(FadingCloudsItems.FADING_CLOUD_ITEM);
                entries.add(FadingCloudsItems.PINK_CLOUD_BOTTLE_ITEM);
                entries.add(FadingCloudsItems.PURPLE_CLOUD_BOTTLE_ITEM);
                entries.add(FadingCloudsItems.LAVENDER_CLOUD_BOTTLE_ITEM);
                entries.add(FadingCloudsItems.LILAC_CLOUD_BOTTLE_ITEM);
                entries.add(FadingCloudsItems.BLUE_CLOUD_BOTTLE_ITEM);
                entries.add(FadingCloudsItems.CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE);
            }))
            .build();

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, FADING_CLOUDS_GROUP_KEY, FADING_CLOUDS_ITEMS_GROUP);
    }
}
