package me.lemo.fading_clouds.registry;

import me.lemo.fading_clouds.FadingClouds;
import me.lemo.fading_clouds.item.FadingCloudItem;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("unused")
public class FadingCloudsItems {
    public static final Item FADING_CLOUD_ITEM = itemRegister(
            new FadingCloudItem(new Item.Settings()), "fading_cloud"
    );

    public static final Item BLUE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "blue_cloud_bottle"
    );

    public static final Item LAVENDER_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "lavender_cloud_bottle"
    );

    public static final Item LILAC_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "lilac_cloud_bottle"
    );

    public static final Item PINK_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "pink_cloud_bottle"
    );

    public static final Item PURPLE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()), "purple_cloud_bottle"
    );

    public static final Item CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE = smithingTemplateRegister(
            "cloud_walker_armor_trim_smithing_template"
    );





    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, FadingClouds.id(id), item);


    }

    public static Item smithingTemplateRegister(String id){
        return Registry.register(Registries.ITEM, FadingClouds.id(id), SmithingTemplateItem.of(FadingClouds.id(id)));
    }
}
