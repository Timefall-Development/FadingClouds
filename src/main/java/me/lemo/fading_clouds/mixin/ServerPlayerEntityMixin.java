package me.lemo.fading_clouds.mixin;


import me.lemo.fading_clouds.FadingClouds;
import me.lemo.fading_clouds.registry.FadingCloudsBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)

public class ServerPlayerEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void fading_clouds$placeCloudOnTick(CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Object) this;
        if (serverPlayerEntity.getWorld() instanceof ServerWorld serverWorld
                && fadingClouds$isCloudWalker(serverPlayerEntity)
                && !serverPlayerEntity.isSneaking()) {
            BlockPos pos = serverPlayerEntity.getBlockPos().down();
            if (fadingClouds$isValidReplacementBlock(serverWorld, pos)) {
                serverWorld.setBlockState(pos, FadingCloudsBlocks.FADING_CLOUD.getDefaultState());
                fadingClouds$damageCloudWalker(serverPlayerEntity);
            }
        }
    }

    @Unique
    private boolean fadingClouds$isCloudWalker(ServerPlayerEntity serverPlayerEntity) {
        return fadingClouds$getArmorTrim(serverPlayerEntity, EquipmentSlot.FEET) != null
                && fadingClouds$getArmorTrim(serverPlayerEntity, EquipmentSlot.FEET)
                    .pattern().value().assetId().equals(FadingClouds.id("cloud_walker"));
    }

    @Unique
    @SuppressWarnings("SameParameterValue")
    private ArmorTrim fadingClouds$getArmorTrim(ServerPlayerEntity serverPlayerEntity, EquipmentSlot slot) {
        return serverPlayerEntity.getEquippedStack(slot).getComponents().get(DataComponentTypes.TRIM);
    }

    @Unique
    private boolean fadingClouds$isValidReplacementBlock(ServerWorld serverWorld, BlockPos pos) {
        BlockState state = serverWorld.getBlockState(pos);
        return state.isReplaceable() && !state.isOf(Blocks.WATER) && !state.isOf(Blocks.LAVA);
    }

    @Unique
    private void fadingClouds$damageCloudWalker(ServerPlayerEntity serverPlayerEntity) {
        if (!serverPlayerEntity.isCreative()) {
            ItemStack stack = serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET);
            if (serverPlayerEntity.getWorld().getTime() % 20 == 0) {
                stack.damage(1, serverPlayerEntity, EquipmentSlot.MAINHAND);
            }
            if (stack.getDamage() <= 0) {
                serverPlayerEntity.getWorld().playSound(
                        null, serverPlayerEntity.getBlockPos(),
                        SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(),
                        SoundCategory.BLOCKS,
                        1.0F,
                        1.0F);
            }
        }
    }
}
