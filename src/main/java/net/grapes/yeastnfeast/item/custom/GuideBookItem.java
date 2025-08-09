package net.grapes.yeastnfeast.item.custom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;

public class GuideBookItem extends Item {

    public GuideBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        if (player instanceof ServerPlayer) {
            PatchouliAPI.get().openBookGUI((ServerPlayer) player, BuiltInRegistries.ITEM.getKey(this));
            player.playSound(SoundEvents.BOOK_PAGE_TURN, 1F, (float) (0.7 + Math.random() * 0.4));
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }
}
