package zabeenia.prismaticcarpets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;

public class PrismaticLoomBlock extends Block {

    public static final EnumProperty<Direction> FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public PrismaticLoomBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                defaultBlockState().setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
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
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (!level.isClientSide()) {
            player.openMenu(getMenuProvider(level, pos));
        }

        return InteractionResult.SUCCESS;
    }

    private SimpleMenuProvider getMenuProvider(
            Level level,
            BlockPos pos
    ) {
        return new SimpleMenuProvider(
                (containerId, inventory, player) ->
                        new PrismaticLoomMenu(
                                containerId,
                                inventory
                        ),
                Component.translatable(
                        "block.prismatic-carpets.prismatic_loom"
                )
        );
    }
}