package me.lemo.fading_clouds.mixin;


import me.lemo.fading_clouds.FadingClouds;
import me.lemo.fading_clouds.registry.FadingCloudsBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
       if (fading_clouds$getArmorTrim(serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET)) != null
               && fading_clouds$getArmorTrim(serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET)).getPattern().value().assetId().equals(FadingClouds.id("cloud_walker"))
               && !serverPlayerEntity.isSneaking()
               && serverPlayerEntity.getWorld().getTime() % 1 == 0){
           if (serverPlayerEntity.getWorld() instanceof ServerWorld serverWorld
                   && (serverPlayerEntity.getWorld().getBlockState(serverPlayerEntity.getBlockPos().down()).isReplaceable()
                   && !serverPlayerEntity.getWorld().getBlockState(serverPlayerEntity.getBlockPos().down()).isOf(Blocks.WATER)
                   && !serverPlayerEntity.getWorld().getBlockState(serverPlayerEntity.getBlockPos().down()).isOf(Blocks.LAVA))) {
               serverWorld.setBlockState(serverPlayerEntity.getBlockPos().down(), FadingCloudsBlocks.FADING_CLOUD.getDefaultState());
               if (!serverPlayerEntity.isCreative()&& serverPlayerEntity.getWorld().getTime() % 20 == 0)
                   serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET).damage(1, serverPlayerEntity, EquipmentSlot.MAINHAND);
               if (!serverPlayerEntity.isCreative() && serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET).getDamage() <= 0)
                   serverWorld.playSound(null, serverPlayerEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.BLOCKS, 1.0F, 1.0F);
           }
       }
    }
    @Unique
    private static ArmorTrim fading_clouds$getArmorTrim(ItemStack itemStack) {
        return itemStack.get(DataComponentTypes.TRIM);
    }
}
