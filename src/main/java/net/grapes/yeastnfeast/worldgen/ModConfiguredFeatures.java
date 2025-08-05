package net.grapes.yeastnfeast.worldgen;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.grapes.yeastnfeast.block.ModBlocks;
import net.grapes.yeastnfeast.block.custom.ElderberryBushBlock;
import net.grapes.yeastnfeast.block.custom.HawthornLeavesBlock;
import net.grapes.yeastnfeast.block.custom.LemonLeavesBlock;
import net.grapes.yeastnfeast.block.custom.RoseHipsBushBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public class ModConfiguredFeatures {

    // Keys
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_BARLEY = registerKey("wild_barley");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_RYE = registerKey("wild_rye");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GINGER = registerKey("wild_ginger");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_GARLIC = registerKey("wild_garlic");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ELDERBERRIES = registerKey("elderberries");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROSE_HIPS = registerKey("rose_hips");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE = registerKey("maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEMON_TREE = registerKey("lemon_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HAWTHORN_TREE = registerKey("hawthorn_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        registerWildCrops(context);
        registerBushes(context);
        registerTrees(context);
    }

    private static void registerWildCrops(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, WILD_BARLEY, Feature.FLOWER, patchConfig(ModBlocks.WILD_BARLEY.get(), 2, 3, 1));
        register(context, WILD_RYE, Feature.FLOWER, patchConfig(ModBlocks.WILD_RYE.get(), 2, 3, 1));
        register(context, WILD_GINGER, Feature.FLOWER, patchConfig(ModBlocks.WILD_GINGER.get(), 2, 3, 1));
        register(context, WILD_GARLIC, Feature.FLOWER, patchConfig(ModBlocks.WILD_GARLIC.get(), 3, 7, 3));
    }

    private static void registerBushes(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, ELDERBERRIES, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(30, 8, 4,
                        simple(ModBlocks.ELDERBERRY_BUSH.get().defaultBlockState().setValue(ElderberryBushBlock.AGE, 3))));
        register(context, ROSE_HIPS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(30, 8, 4,
                        simple(ModBlocks.ROSE_HIPS_BUSH.get().defaultBlockState().setValue(RoseHipsBushBlock.AGE, 3))));
    }

    private static void registerTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MAPLE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        SimpleWeightedRandomList.Builder<BlockState> lemonLeavesBuilder = SimpleWeightedRandomList.builder();
        lemonLeavesBuilder.add(ModBlocks.LEMON_TREE_LEAVES.get().defaultBlockState(), 8);
        lemonLeavesBuilder.add(ModBlocks.FLOWERING_LEMON_TREE_LEAVES.get().defaultBlockState()
                .setValue(LemonLeavesBlock.AGE, 2), 1);

        register(context, LEMON_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                new WeightedStateProvider(lemonLeavesBuilder.build()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());

        SimpleWeightedRandomList.Builder<BlockState> hawthornLeavesBuilder = SimpleWeightedRandomList.builder();
        hawthornLeavesBuilder.add(ModBlocks.HAWTHORN_TREE_LEAVES.get().defaultBlockState(), 8);
        hawthornLeavesBuilder.add(ModBlocks.FLOWERING_HAWTHORN_TREE_LEAVES.get().defaultBlockState()
                .setValue(HawthornLeavesBlock.AGE, 2), 1);

        register(context, HAWTHORN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                new WeightedStateProvider(hawthornLeavesBuilder.build()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F,
                        0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 1))
                .ignoreVines()
                .build());
    }

    // Utility
    private static RandomPatchConfiguration patchConfig(Block block, int tries, int xzSpread, int ySpread) {
        return new RandomPatchConfiguration(tries, xzSpread, ySpread,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(block))));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(YeastNFeastMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key,
            F feature,
            FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }

    private static <C extends FeatureConfiguration> Supplier<C> simple(C config) {
        return () -> config;
    }

    private static Supplier<SimpleBlockConfiguration> simple(BlockStateProvider provider) {
        return () -> new SimpleBlockConfiguration(provider);
    }


    private static Supplier<SimpleBlockConfiguration> simple(Block block) {
        return simple(BlockStateProvider.simple(block));
    }

    private static Holder<PlacedFeature> simple(BlockState state) {
        return PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(state)));
    }
}
