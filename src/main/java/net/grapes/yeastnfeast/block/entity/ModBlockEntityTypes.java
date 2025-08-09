package net.grapes.yeastnfeast.block.entity;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.grapes.yeastnfeast.block.ModBlocks;
import net.grapes.yeastnfeast.block.entity.custom.KegBlockEntity;
import net.grapes.yeastnfeast.block.entity.custom.TreeTapBlockEntity;
import net.grapes.yeastnfeast.block.entity.wood.ModHangingSignBlockEntity;
import net.grapes.yeastnfeast.block.entity.wood.ModSignBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, YeastNFeastMod.MODID);

    public static final Supplier<BlockEntityType<KegBlockEntity>> KEG = BLOCK_ENTITY_TYPE.register("keg",
            () -> BlockEntityType.Builder.of(KegBlockEntity::new, ModBlocks.KEG.get()).build(null));

    public static final Supplier<BlockEntityType<TreeTapBlockEntity>> TREE_TAP = BLOCK_ENTITY_TYPE.register("tree_tap",
            () -> BlockEntityType.Builder.of(TreeTapBlockEntity::new, ModBlocks.TREE_TAP.get()).build(null));

    // Mod Signs
    public static final Supplier<BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITY_TYPE.register(
            "mod_sign",
            () -> BlockEntityType.Builder.of(
                            ModSignBlockEntity::new,
                            ModBlocks.MAPLE_SIGN.get(),
                            ModBlocks.MAPLE_WALL_SIGN.get()
                    )
                    .build(null)
    );

    public static final Supplier<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN = BLOCK_ENTITY_TYPE.register(
            "mod_hanging_sign",
            () -> BlockEntityType.Builder.of(
                            ModHangingSignBlockEntity::new,
                            ModBlocks.MAPLE_HANGING_SIGN.get(),
                            ModBlocks.MAPLE_HANGING_WALL_SIGN.get()
                    )
                    .build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
    }
}
