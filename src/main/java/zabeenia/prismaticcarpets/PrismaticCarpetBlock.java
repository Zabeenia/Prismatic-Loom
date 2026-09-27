package zabeenia.prismaticcarpets;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;

public class PrismaticCarpetBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape CARPET_SHAPE =
            Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    public PrismaticCarpetBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                defaultBlockState().setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PrismaticCarpetBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        return new PrismaticCarpetBlockEntity(pos, state);
    }

    @Override
    public void setPlacedBy(
            net.minecraft.world.level.Level level,
            BlockPos pos,
            BlockState state,
            net.minecraft.world.entity.LivingEntity placer,
            net.minecraft.world.item.ItemStack stack
    ) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (stack.has(PrismaticCarpetComponents.PRISMATIC_CARPET_DATA)) {
            PrismaticCarpetData data =
                    stack.get(PrismaticCarpetComponents.PRISMATIC_CARPET_DATA);

            if (level.getBlockEntity(pos) instanceof PrismaticCarpetBlockEntity blockEntity) {
                blockEntity.setCarpetData(data);
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {
        return defaultBlockState().setValue(
                FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            net.minecraft.world.level.BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return CARPET_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(
            BlockState state,
            net.minecraft.world.level.BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return CARPET_SHAPE;
    }
}
