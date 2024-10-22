package me.lemo.fading_clouds.gamerule;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class RemoveFadedBlocksGameRule {

    public static final GameRules.Key<GameRules.BooleanRule> REMOVE_FADED_BLOCKS
            = GameRuleRegistry.register("removeFadedBlocks", GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(true));

    public static void register() {}


}
