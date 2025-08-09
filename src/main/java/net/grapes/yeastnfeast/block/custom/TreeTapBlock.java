package net.grapes.yeastnfeast.block.custom;

import com.mojang.serialization.MapCodec;
import net.grapes.yeastnfeast.block.entity.ModBlockEntityTypes;
import net.grapes.yeastnfeast.block.entity.custom.TreeTapBlockEntity;
import net.grapes.yeastnfeast.item.ModItems;
import net.grapes.yeastnfeast.particle.ModParticleType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TreeTapBlock extends BaseEntityBlock {

    public static final BooleanProperty DRIPPING = BooleanProperty.create("dripping");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final MapCodec<TreeTapBlock> CODEC = simpleCodec(TreeTapBlock::new);

    private static final VoxelShape NORTH_WALL_SHAPE = Block.box(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
    private static final VoxelShape SOUTH_WALL_SHAPE = Block.box(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
    private static final VoxelShape WEST_WALL_SHAPE = Block.box(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
    private static final VoxelShape EAST_WALL_SHAPE = Block.box(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);


    public TreeTapBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(DRIPPING, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case SOUTH -> SOUTH_WALL_SHAPE;
            case WEST -> WEST_WALL_SHAPE;
            case EAST -> EAST_WALL_SHAPE;
            default -> NORTH_WALL_SHAPE;
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockBehind = level.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        String blockId = blockBehind.getBlock().getDescriptionId();

        return blockId.contains("maple_log") || blockId.contains("maple_wood");
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(DRIPPING) && player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
            player.getItemInHand(hand).shrink(1);
            player.addItem(new ItemStack(ModItems.MAPLE_SYRUP.get()));
            level.setBlock(pos, state.setValue(DRIPPING, false), 3);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(DRIPPING)) {
            if (level.isClientSide && level.random.nextInt(2) == 0) {
                Direction facing = state.getValue(FACING);
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;

                switch (facing) {
                    case NORTH:
                        z = pos.getZ() + 0.8;
                        break;
                    case SOUTH:
                        z = pos.getZ() + 0.2;
                        break;
                    case WEST:
                        x = pos.getX() + 0.8;
                        break;
                    case EAST:
                        x = pos.getX() + 0.2;
                        break;
                }
                level.addParticle(ModParticleType.DRIPPING_SYRUP.get(), x, y, z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DRIPPING);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntityTypes.TREE_TAP.get(),
                (level1, pos, state1, blockEntity) -> TreeTapBlockEntity.tick(level1, pos, state1, blockEntity));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TreeTapBlockEntity(blockPos, blockState);
    }
}
