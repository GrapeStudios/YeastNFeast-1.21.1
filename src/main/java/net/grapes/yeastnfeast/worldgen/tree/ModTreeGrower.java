package net.grapes.yeastnfeast.worldgen.tree;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.grapes.yeastnfeast.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
    public static final TreeGrower MAPLE = new TreeGrower(YeastNFeastMod.MODID + ":maple",
            Optional.empty(), Optional.of(ModConfiguredFeatures.MAPLE_TREE), Optional.empty());
    public static final TreeGrower LEMON = new TreeGrower(YeastNFeastMod.MODID + ":lemon",
            Optional.empty(), Optional.of(ModConfiguredFeatures.MAPLE_TREE), Optional.empty());
    public static final TreeGrower HAWTHORN = new TreeGrower(YeastNFeastMod.MODID + ":hawthorn",
            Optional.empty(), Optional.of(ModConfiguredFeatures.MAPLE_TREE), Optional.empty());
}
