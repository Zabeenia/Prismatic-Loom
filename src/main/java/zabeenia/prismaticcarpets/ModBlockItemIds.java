package zabeenia.prismaticcarpets;

import net.minecraft.resources.Identifier;
import net.minecraft.references.BlockItemId;

public class ModBlockItemIds {

    public static final BlockItemId PRISMATIC_LOOM =
            create("prismatic_loom");

    public static final BlockItemId PRISMATIC_CARPET =
            create("prismatic_carpet");

    private static BlockItemId create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(
                PrismaticCarpets.MOD_ID,
                name
        );
        return BlockItemId.create(id, id);
    }
}
