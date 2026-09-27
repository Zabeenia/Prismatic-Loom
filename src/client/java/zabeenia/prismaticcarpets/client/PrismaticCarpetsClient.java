package zabeenia.prismaticcarpets.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

import zabeenia.prismaticcarpets.ModMenuTypes;

import net.minecraft.client.renderer.special.SpecialModelRenderers;
import zabeenia.prismaticcarpets.PrismaticCarpets;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import zabeenia.prismaticcarpets.ModBlocks;

public class PrismaticCarpetsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MenuScreens.register(
				ModMenuTypes.PRISMATIC_LOOM,
				PrismaticLoomScreen::new
		);

		BlockEntityRenderers.register(
				zabeenia.prismaticcarpets.ModBlockEntities.PRISMATIC_CARPET,
				PrismaticCarpetBlockEntityRenderer::new
		);

		ModelLoadingPlugin.register(plugin ->
				plugin.modifyBlockModelAfterBake().register((model, context) ->
						context.state().is(ModBlocks.PRISMATIC_CARPET)
								? new PrismaticCarpetParticleModel(model)
								: model
				)
		);

		SpecialModelRenderers.ID_MAPPER.put(
				PrismaticCarpets.id("prismatic_carpet"),
				PrismaticCarpetSpecialRenderer.Unbaked.MAP_CODEC
		);

		ModelLayerRegistry.registerModelLayer(
				PrismaticCarpetLayers.PRISMATIC_CARPET,
				PrismaticCarpetLayers::createLayer
		);
	}
}
