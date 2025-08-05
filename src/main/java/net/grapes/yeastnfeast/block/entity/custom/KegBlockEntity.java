package net.grapes.yeastnfeast.block.entity.custom;

import net.grapes.yeastnfeast.block.entity.ModBlockEntityTypes;
import net.grapes.yeastnfeast.recipe.KegRecipe;
import net.grapes.yeastnfeast.recipe.ModRecipes;
import net.grapes.yeastnfeast.screen.custom.KegMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KegBlockEntity extends SyncBlockEntity implements MenuProvider {

    public final ItemStackHandler inventory = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public static final int INPUT_SLOT_1 = 0;
    public static final int INPUT_SLOT_2 = 1;
    public static final int INPUT_SLOT_3 = 2;
    public static final int OUTPUT_SLOT = 3;
    public static final int YEAST_SLOT = 4;
    public static final int TANKARD_SLOT = 5;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;
    private final int DEFAULT_MAX_PROGRESS = 200;
    @Nullable private Player lastInteractedPlayer;

    public KegBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.KEG.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> KegBlockEntity.this.progress;
                    case 1 -> KegBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> KegBlockEntity.this.progress = value;
                    case 1 -> KegBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void clearContents() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (hasRecipe() && isOutputSlotEmptyOrReceivable()) {
            increaseCraftingProgress();
            setChanged(level, pos, state);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Level level = this.getLevel();
        if (level == null) return;

        RecipeWrapper wrapper = new RecipeWrapper(inventory);

        Optional<RecipeHolder<KegRecipe>> match = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KEG_TYPE.get(), wrapper, level);

        if (match.isPresent()) {
            KegRecipe recipe = match.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());
            ItemStack currentOutput = inventory.getStackInSlot(OUTPUT_SLOT);

            inventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                    currentOutput.getCount() + result.getCount()));

            for (int i = 0; i < 3; i++) {
                inventory.extractItem(i, 1, false);
            }

            if (!recipe.getYeastSlot().isEmpty()) {
                inventory.extractItem(YEAST_SLOT, 1, false);
            }

            if (!recipe.getTankardSlot().isEmpty()) {
                inventory.extractItem(TANKARD_SLOT, 1, false);
            }

            if (lastInteractedPlayer != null) {
                grantExperience(lastInteractedPlayer, recipe.getExperience());
            }

            setChanged(level, worldPosition, getBlockState());
        }
    }

    private void grantExperience(Player player, float experience) {
        if (experience > 0 && !player.level().isClientSide) {
            player.giveExperiencePoints((int) experience);
        }
    }

    public void setLastInteractedPlayer(Player player) {
        this.lastInteractedPlayer = player;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                inventory.getStackInSlot(OUTPUT_SLOT).getCount() < inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasRecipe() {
        Level level = this.getLevel();
        if (level == null) return false;

        RecipeWrapper wrapper = new RecipeWrapper(inventory);
        Optional<RecipeHolder<KegRecipe>> match = level.getRecipeManager()
                .getRecipeFor(ModRecipes.KEG_TYPE.get(), wrapper, level);

        if (match.isEmpty()) return false;

        KegRecipe recipe = match.get().value();
        ItemStack result = recipe.getResultItem(level.registryAccess());

        this.maxProgress = recipe.getBrewTime();

        return canInsertAmountIntoOutputSlot(result.getCount()) &&
                canInsertItemIntoOutputSlot(result);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                inventory.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = inventory.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = inventory.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.yeastnfeast.keg");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new KegMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("keg.progress", progress);
        tag.putInt("keg.max_progress", maxProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("keg.progress");
        maxProgress = tag.getInt("keg.max_progress");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
