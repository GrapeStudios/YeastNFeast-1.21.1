package net.grapes.yeastnfeast.item.custom;

import net.grapes.yeastnfeast.item.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MeadItem extends FeastItem {

    public MeadItem(Properties properties, Supplier<Holder<MobEffect>> effectSupplier, int duration, int amplifier, Component tooltip) {
        super(properties, effectSupplier, duration, amplifier, tooltip);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 40;
    }

    @Override
    protected ItemStack getReturnContainer(ItemStack stack) {
        return new ItemStack(ModItems.TANKARD.get());
    }
}
