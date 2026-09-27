package zabeenia.prismaticcarpets;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class ModItems {

    public static final Item PRISMATIC_YARNS = Registry.register(
            BuiltInRegistries.ITEM,
            PrismaticCarpets.id("prismatic_yarns"),
            new Item(
                    new Item.Properties()
                            .setId(net.minecraft.resources.ResourceKey.create(
                                    net.minecraft.core.registries.Registries.ITEM,
                                    PrismaticCarpets.id("prismatic_yarns")
                            ))
            )
    );

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS)
                .register(creativeTab ->
                        creativeTab.accept(PRISMATIC_YARNS)
                );
    }
}