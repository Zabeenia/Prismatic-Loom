package zabeenia.prismaticcarpets;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final BlockEntityType<PrismaticCarpetBlockEntity> PRISMATIC_CARPET =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    PrismaticCarpets.id("prismatic_carpet"),
                    FabricBlockEntityTypeBuilder.create(
                            PrismaticCarpetBlockEntity::new,
                            ModBlocks.PRISMATIC_CARPET
                    ).build()
            );

    public static void initialize() {
    }
}