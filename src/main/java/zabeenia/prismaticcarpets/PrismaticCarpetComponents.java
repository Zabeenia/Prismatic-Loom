package zabeenia.prismaticcarpets;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.component.DataComponentType;

public class PrismaticCarpetComponents {

    public static final DataComponentType<PrismaticCarpetData> PRISMATIC_CARPET_DATA =
            Registry.register(
                    BuiltInRegistries.DATA_COMPONENT_TYPE,
                    PrismaticCarpets.id("prismatic_carpet_data"),
                    DataComponentType.<PrismaticCarpetData>builder()
                            .persistent(PrismaticCarpetData.CODEC)
                            .networkSynchronized(PrismaticCarpetData.STREAM_CODEC)
                            .build()
            );

    public static void initialize() {
    }
}
