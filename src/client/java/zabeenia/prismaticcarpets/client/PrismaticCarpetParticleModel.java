package zabeenia.prismaticcarpets.client;

import net.fabricmc.fabric.api.client.model.loading.v1.wrapper.WrapperBlockStateModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import zabeenia.prismaticcarpets.PrismaticCarpetBlockEntity;
import zabeenia.prismaticcarpets.PrismaticCarpets;
import net.minecraft.client.renderer.Sheets;

public final class PrismaticCarpetParticleModel extends WrapperBlockStateModel {

    public PrismaticCarpetParticleModel(BlockStateModel wrapped) {
        super(wrapped);
    }

    @Override
    public Material.Baked particleMaterial(
            BlockAndTintGetter level,
            BlockPos pos,
            BlockState state
    ) {
        if (level.getBlockEntity(pos) instanceof PrismaticCarpetBlockEntity blockEntity) {
            DyeColor color = DyeColor.byName(blockEntity.getCarpet(), null);
            if (color != null) {
                var spriteId = Sheets.BLOCKS_MAPPER.apply(
                        PrismaticCarpets.id(color.getName() + "_carpet")
                );
                return new Material.Baked(
                        Minecraft.getInstance().getAtlasManager().get(spriteId),
                        false
                );
            }
        }

        return this.wrapped.particleMaterial(level, pos, state);
    }
}
