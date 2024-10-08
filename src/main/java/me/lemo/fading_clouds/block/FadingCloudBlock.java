package me.lemo.fading_clouds.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FadingCloudBlock extends TransparentBlock {
    public static IntProperty AGE = Properties.AGE_4;
    int minFade = 10;
    int maxFade = 20;

    public FadingCloudBlock(Settings settings) {
        super(settings);
    }

    public static BlockState getUnfadedState() {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(world.getRandom(), minFade, maxFade));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.fade(world, pos);
    }

    private boolean canFade(BlockView world, BlockPos pos, int maxNeighbors) {
        int i = 0;
        BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutableBlockPosition.set(pos, direction);
            if (!world.getBlockState(mutableBlockPosition).isOf(this) || ++i < maxNeighbors) continue;
            return false;
        }
        return true;
    }

    protected void fade(World world, BlockPos pos) {
        world.setBlockState(pos, getUnfadedState());
        world.updateNeighbor(pos, getUnfadedState().getBlock(), pos);
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int ageState = state.get(AGE);
        if (ageState < 4) {
            world.setBlockState(pos, state.with(AGE, ageState + 1), Block.NOTIFY_LISTENERS);
            return false;
        }
        this.fade(world, pos);
        return true;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (
                (
                        random.nextInt(4) == 0
                        || this.canFade(world, pos, 4)
                )
                        && this.increaseAge(state, world, pos)
        ) {
            BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutableBlockPosition.set(pos, direction);
                BlockState blockState = world.getBlockState(mutableBlockPosition);
                if (!blockState.isOf(this) || this.increaseAge(blockState, world, mutableBlockPosition)) continue;
                world.scheduleBlockTick(mutableBlockPosition, this, MathHelper.nextInt(random, minFade, maxFade));
            }
            return;
        }
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, minFade, maxFade));
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        super.onEntityLand(world, entity);
        if (entity instanceof LivingEntity livingEntity && world.getBlockState(livingEntity.getBlockPos().down()).isOf(this)) {

            this.fading_clouds$computeFallDamage(livingEntity);
        } else if (entity instanceof LivingEntity livingEntity && !world.getBlockState(livingEntity.getBlockPos().down()).isOf(this)) {
            EntityAttributeInstance entityAttributeInstance = ((LivingEntity)entity).getAttributeInstance(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER);
            if (entityAttributeInstance != null)

                entityAttributeInstance.setBaseValue(1.0f);
        }
    }

    protected void fading_clouds$computeFallDamage(LivingEntity livingEntity) {
        if (livingEntity.getType().isIn(EntityTypeTags.FALL_DAMAGE_IMMUNE)) {
            return;
        }
        EntityAttributeInstance entityAttributeInstance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER);
        if (entityAttributeInstance != null )

            entityAttributeInstance.setBaseValue(0.0f);
    }

    @SuppressWarnings("all")
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {

    }
}