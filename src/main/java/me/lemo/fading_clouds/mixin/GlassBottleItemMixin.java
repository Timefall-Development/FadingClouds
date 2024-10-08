package me.lemo.fading_clouds.mixin;

import me.lemo.fading_clouds.registry.FadingCloudsItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public class GlassBottleItemMixin {

    @Unique
    float ultraColdMin = -2.0f;
    @Unique
    float ultraColdMax = 0.19f;

    @Unique
    float coldMin = 0.2f;
    @Unique
    float coldMax = 0.59f;

    @Unique
    float warmMin = 0.6f;
    @Unique
    float warmMax = 0.79f;

    @Unique
    float hotMin = 0.8f;
    @Unique
    float hotMax = 1.99f;

    @Unique
    float ultraHot = 2.0f;

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void fading_clouds$collectCloudsOnUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (world.isClient())
            return;

        ItemStack heldStack = user.getStackInHand(hand);


        float temperature = user.getWorld().getBiome(user.getBlockPos()).value().getTemperature();

        ItemStack cloudBottleItemStack = fading_clouds$getCloudBottleColorFromTemperature(temperature);

        if (user.getY() >= 330 && fading_clouds$isInOverworld(user)){
            cir.setReturnValue(TypedActionResult.success(this.fading_clouds$fill(heldStack, user, cloudBottleItemStack)));
        } else {
            cir.setReturnValue(TypedActionResult.consume(heldStack));
        }
    }


    @Unique
    private ItemStack fading_clouds$getCloudBottleColorFromTemperature(float temperature){
        if (temperature >= ultraColdMin && temperature <= ultraColdMax){
            return new ItemStack(FadingCloudsItems.BLUE_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= coldMin && temperature <= coldMax){
            return new ItemStack(FadingCloudsItems.LAVENDER_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= warmMin && temperature <= warmMax){
            return new ItemStack(FadingCloudsItems.LILAC_CLOUD_BOTTLE_ITEM);

        } else if (temperature >= hotMin && temperature <= hotMax){
            return new ItemStack(FadingCloudsItems.PINK_CLOUD_BOTTLE_ITEM);

        }else if (temperature >= ultraHot) {
            return new ItemStack(FadingCloudsItems.PURPLE_CLOUD_BOTTLE_ITEM);
        }
        return ItemStack.EMPTY;
    }

    @Unique
    protected ItemStack fading_clouds$fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        GlassBottleItem glassBottleItem = (GlassBottleItem) (Object) this;

        player.incrementStat(Stats.USED.getOrCreateStat(glassBottleItem));
        return ItemUsage.exchangeStack(stack, player, outputStack);

    }

    @Unique
    private static boolean fading_clouds$isInOverworld(PlayerEntity playerEntity){
        return playerEntity.getWorld().getDimension().bedWorks();
    }
}

/* TODO:
    Check if above y330 ✓
    Check if your in the overworld ✓
    Check if on a server ✓
    Use bottle fill sound
    Fill empty bottle with correct cloud bottle
    5 Items for cloud bottles ✓
    Recipe for fading cloud ✓
    5 biome groups ✓
    to get biome temp the player is in: playerEntity.getWorld().getBiome(playerEntity.getBlockPos()).value().getTemperature()

*/