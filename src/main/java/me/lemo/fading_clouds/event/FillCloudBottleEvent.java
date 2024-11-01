package me.lemo.fading_clouds.event;

import me.lemo.fading_clouds.registry.FadingCloudsItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;

public class FillCloudBottleEvent {
    /**
     * <h1>Why are these fields declared as final?</h1>
     * In Java, it is a good practice to declare fields that are not meant to change as final. This is because final
     * fields cannot be reassigned after they are initialised.
     * <p>
     *     <h2>Declaring these fields as final</h2>
     *      <p>
     *          In this code, the float fields
     *      </p>
     *      <ul>
     *          <li>ULTRA_COLD_MIN</li>
     *          <li>ULTRA_COLD_MAX</li>
     *          <li>COLD_MIN</li>
     *          <li>COLD_MAX</li>
     *          <li>WARM_MIN</li>
     *          <li>WARM_MAX</li>
     *          <li>HOT_MIN</li>
     *          <li>HOT_MAX</li>
     *          <li>ULTRA_HOT</li>
     *      </ul>
     *      <p>
     *          are used as constants to define temperature ranges for different types of cloud bottles.
     *          Since their values are not intended to change throughout the execution of the program, it is recommended to
     *          declare them as final.
     *      </p>
     * </p>
     *      <h2>Declaring these fields as final can provide several benefits:</h2>
     *      <ul>
     *          <li>Immutability: By making these fields final, one ensures that their values cannot be changed after initialisation. This can help prevent bugs and make this code more predictable.</li>
     *          <li>Readability: Using final constants with descriptive names can improve the readability of the code. It makes it clear that these values are intended to be constants and provides a clear understanding of their purpose.</li>
     *          <li>Type Safety: Declaring these fields as final can also provide type safety. Since the values are fixed, the code that uses these constants can rely on their specific values without the risk of unexpected changes.</li>
     *          <li>Runtime Efficiency: Declaring these fields as final can help reduce the runtime overhead of using these constants. Final primitive fields can be inclined directly to their call sites, which removes the need for the JVM to have to fetch the value from reference fields.</li>
     *      </ul>
     * <p>
     *      Overall, declaring these fields as final is a good practice to ensure immutability, improve code readability, and promote type safety in the codebase.</li>
     * </p>
     */
    private static final float ULTRA_COLD_MIN = -2.0f;
    private static final float ULTRA_COLD_MAX = 0.19f;

    private static final float COLD_MIN = 0.2f;
    private static final float COLD_MAX = 0.59f;

    private static final float WARM_MIN = 0.6f;
    private static final float WARM_MAX = 0.79f;

    private static final float HOT_MIN = 0.8f;
    private static final float HOT_MAX = 1.99f;

    private static final float ULTRA_HOT = 2.0f;

    public static void registerEvent() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            // Always start UseItemCallback with a spectator check because the event fires before this is checked
            if (!player.isSpectator()) {
                if (player.getStackInHand(hand).isOf(Items.GLASS_BOTTLE) && player.getY() >= 330 && fading_clouds$isInOverworld(player)) {
                    ItemStack heldStack = player.getStackInHand(hand);
                    float temperature = player.getWorld().getBiome(player.getBlockPos()).value().getTemperature();
                    ItemStack cloudBottleItemStack = fading_clouds$getCloudBottleColorFromTemperature(temperature);

                    return ActionResult.SUCCESS.withNewHandStack(fading_clouds$fill(heldStack, player, cloudBottleItemStack));
                }
            }
            return ActionResult.CONSUME;
        });
    }

    private static ItemStack fading_clouds$getCloudBottleColorFromTemperature(float temperature){
        if (fading_clouds$isUltraCold(temperature)){
            return new ItemStack(FadingCloudsItems.BLUE_CLOUD_BOTTLE_ITEM);
        } else if (fading_clouds$isCold(temperature)){
            return new ItemStack(FadingCloudsItems.LAVENDER_CLOUD_BOTTLE_ITEM);
        } else if (fading_clouds$isWarm(temperature)){
            return new ItemStack(FadingCloudsItems.LILAC_CLOUD_BOTTLE_ITEM);
        } else if (fading_clouds$isHot(temperature)){
            return new ItemStack(FadingCloudsItems.PINK_CLOUD_BOTTLE_ITEM);
        } else if (fading_clouds$isUltraHot(temperature)){
            return new ItemStack(FadingCloudsItems.PURPLE_CLOUD_BOTTLE_ITEM);
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack fading_clouds$fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
        if(player.getMainHandStack().getItem() instanceof GlassBottleItem glassBottleItem) {
            player.incrementStat(Stats.USED.getOrCreateStat(glassBottleItem));
            return ItemUsage.exchangeStack(stack, player, outputStack);
        }
        return stack;
    }

    private static boolean fading_clouds$isInOverworld(PlayerEntity playerEntity){
        return playerEntity.getWorld().getDimension().bedWorks();
    }

    private static boolean fading_clouds$isUltraCold(float temperature){
        return temperature >= ULTRA_COLD_MIN && temperature <= ULTRA_COLD_MAX;
    }

    private static boolean fading_clouds$isCold(float temperature){
        return temperature >= COLD_MIN && temperature <= COLD_MAX;
    }

    private static boolean fading_clouds$isWarm(float temperature){
        return temperature >= WARM_MIN && temperature <= WARM_MAX;
    }

    private static boolean fading_clouds$isHot(float temperature){
        return temperature >= HOT_MIN && temperature <= HOT_MAX;
    }

    private static boolean fading_clouds$isUltraHot(float temperature){
        return temperature >= ULTRA_HOT;
    }
}