package me.lemo.fading_clouds.registry;

import me.lemo.fading_clouds.FadingClouds;
import me.lemo.fading_clouds.item.FadingCloudItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.List;

@SuppressWarnings("unused")
public class FadingCloudsItems {
    public static final Item FADING_CLOUD_ITEM = itemRegister(
            new FadingCloudItem(new Item.Settings().registryKey(getItemRegistryKey("fading_cloud"))), "fading_cloud"
    );

    public static final Item BLUE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()
                    .component(
                            DataComponentTypes.LORE,
                            lore(Text.translatable("item.fading_clouds.blue_cloud_bottle.lore")))
                    .registryKey(getItemRegistryKey("blue_cloud_bottle"))),
            "blue_cloud_bottle"
    );

    public static final Item LAVENDER_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()
                    .component(
                            DataComponentTypes.LORE,
                            lore(Text.translatable("item.fading_clouds.lavender_cloud_bottle.lore")))
                    .registryKey(getItemRegistryKey("lavender_cloud_bottle"))),
            "lavender_cloud_bottle"
    );

    public static final Item LILAC_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()
                    .component(
                            DataComponentTypes.LORE,
                            lore(Text.translatable("item.fading_clouds.lilac_cloud_bottle.lore")))
                    .registryKey(getItemRegistryKey("lilac_cloud_bottle"))),
            "lilac_cloud_bottle"
    );

    public static final Item PINK_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()
                    .component(
                            DataComponentTypes.LORE,
                            lore(Text.translatable("item.fading_clouds.pink_cloud_bottle.lore")))
                    .registryKey(getItemRegistryKey("pink_cloud_bottle"))),
            "pink_cloud_bottle"
    );

    public static final Item PURPLE_CLOUD_BOTTLE_ITEM = itemRegister(
            new Item(new Item.Settings()
                    .component(
                            DataComponentTypes.LORE,
                            lore(Text.translatable("item.fading_clouds.purple_cloud_bottle.lore")))
                    .registryKey(getItemRegistryKey("purple_cloud_bottle"))),
            "purple_cloud_bottle"
    );

    public static final Item CLOUD_WALKER_ARMOR_TRIM_SMITHING_TEMPLATE = smithingTemplateRegister(
            "cloud_walker_armor_trim_smithing_template"
    );

    public static void register(){

    }

    public static Item itemRegister(Item item, String id){
        return Registry.register(Registries.ITEM, FadingClouds.id(id), item);
    }

    public static RegistryKey<Item> getItemRegistryKey(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, FadingClouds.id(id));
    }

    public static Item smithingTemplateRegister(String id){
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, FadingClouds.id(id));

        return Registry.register(Registries.ITEM, key, SmithingTemplateItem.of(new Item.Settings().registryKey(key)));
    }

    /**
     * Used with permission from FoundationGames, <a href="https://github.com/FoundationGames/Splinecart/blob/702581399890341e506aef9a26f8831daada745e/src/main/java/io/github/foundationgames/splinecart/Splinecart.java#L68">Spinecart</a><br />
     * MIT License
     */
    public static LoreComponent lore(Text lore) {
        return new LoreComponent(List.of(lore));
    }
}
