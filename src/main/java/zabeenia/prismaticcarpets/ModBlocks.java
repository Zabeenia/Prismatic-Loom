package zabeenia.prismaticcarpets;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ModBlocks {

    public static final Block PRISMATIC_LOOM = register(
            ModBlockItemIds.PRISMATIC_LOOM,
            PrismaticLoomBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(2.5F)
                    .noOcclusion()
    );

    public static final Block PRISMATIC_CARPET = register(
            ModBlockItemIds.PRISMATIC_CARPET,
            PrismaticCarpetBlock::new,
            BlockBehaviour.Properties.of()
    );

    private static Block register(
            BlockItemId id,
            java.util.function.Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties
    ) {
        Block block = register(id.block(), blockFactory, properties);

        net.minecraft.world.item.BlockItem blockItem =
                new net.minecraft.world.item.BlockItem(
                        block,
                        new net.minecraft.world.item.Item.Properties()
                                .useBlockDescriptionPrefix()
                                .setId(id.item())
                );

        Registry.register(
                BuiltInRegistries.ITEM,
                id.item(),
                blockItem
        );

        return block;
    }

    private static Block register(
            net.minecraft.resources.ResourceKey<Block> id,
            java.util.function.Function<BlockBehaviour.Properties, Block> blockFactory,
            BlockBehaviour.Properties properties
    ) {
        return Registry.register(
                BuiltInRegistries.BLOCK,
                id,
                blockFactory.apply(properties.setId(id))
        );
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(creativeTab -> {
                    creativeTab.accept(PRISMATIC_LOOM.asItem());
                    creativeTab.accept(PRISMATIC_CARPET.asItem());
                });
    }
}
