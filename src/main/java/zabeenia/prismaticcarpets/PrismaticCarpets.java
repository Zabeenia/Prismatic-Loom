package zabeenia.prismaticcarpets;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

public class PrismaticCarpets implements ModInitializer {
	public static final String MOD_ID = "prismatic-carpets";

	@Override
	public void onInitialize() {
		PrismaticCarpetComponents.initialize();
		ModBlockEntities.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModMenuTypes.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
