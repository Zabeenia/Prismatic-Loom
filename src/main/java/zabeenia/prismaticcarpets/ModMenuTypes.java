package zabeenia.prismaticcarpets;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {

    public static final MenuType<PrismaticLoomMenu> PRISMATIC_LOOM =
            Registry.register(
                    BuiltInRegistries.MENU,
                    PrismaticCarpets.id("prismatic_loom"),
                    new MenuType<>(
                            PrismaticLoomMenu::new,
                            net.minecraft.world.flag.FeatureFlagSet.of()
                    )
            );

    public static void initialize() {
    }
}