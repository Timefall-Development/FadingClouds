package me.lemo.fading_clouds.item;

import me.lemo.fading_clouds.registry.FadingCloudsBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FadingCloudItem extends Item {
    public FadingCloudItem(Settings settings) {
        super(settings.maxDamage(256));
    }
    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand){
        if (user.getWorld() instanceof ServerWorld serverWorld
                && (user.getWorld().getBlockState(user.getBlockPos().down()).isReplaceable()
                && !user.getWorld().getBlockState(user.getBlockPos().down()).isOf(Blocks.WATER)
                && !user.getWorld().getBlockState(user.getBlockPos().down()).isOf(Blocks.LAVA))){
        serverWorld.setBlockState(user.getBlockPos().down(), FadingCloudsBlocks.FADING_CLOUD.getDefaultState());
            if (!user.isCreative())
                user.getStackInHand(hand).damage(1, user, EquipmentSlot.MAINHAND);
            if (!user.isCreative() && user.getStackInHand(hand).getDamage() <= 0)
                serverWorld.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

}

/*TODO ✓
   ADD feature that lets you use a bottle in the sky above the build limit that gives you the cloud ✓
   ADD cloud bottles ✓
   ADD feature to not take fall damage when falling on the block ✓
   CHANGE item break sound ✓
   FIGURE OUT why i jump too high ✓
 */

