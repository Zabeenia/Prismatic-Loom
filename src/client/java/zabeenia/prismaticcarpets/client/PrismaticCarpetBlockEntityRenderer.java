package zabeenia.prismaticcarpets.client;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponentMap;
import zabeenia.prismaticcarpets.PrismaticCarpetBlockEntity;
import zabeenia.prismaticcarpets.PrismaticCarpetBlock;
import zabeenia.prismaticcarpets.PrismaticCarpetComponents;
import zabeenia.prismaticcarpets.PrismaticCarpetData;

public class PrismaticCarpetBlockEntityRenderer
        implements BlockEntityRenderer<
        PrismaticCarpetBlockEntity,
        PrismaticCarpetBlockEntityRenderState> {

    private final PrismaticCarpetSpecialRenderer carpetRenderer;

    public PrismaticCarpetBlockEntityRenderer(
            BlockEntityRendererProvider.Context context
    ) {
        this.carpetRenderer = new PrismaticCarpetSpecialRenderer(
                new Model.Simple(
                        context.entityModelSet().bakeLayer(
                                PrismaticCarpetLayers.PRISMATIC_CARPET
                        ),
                        RenderTypes::entityCutout
                ),
                new Model.Simple(
                        PrismaticCarpetLayers.createFringePart(),
                        RenderTypes::entityCutout
                ),
                context.sprites()
        );
    }

    @Override
    public PrismaticCarpetBlockEntityRenderState createRenderState() {
        return new PrismaticCarpetBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            PrismaticCarpetBlockEntity blockEntity,
            PrismaticCarpetBlockEntityRenderState state,
            float tickProgress,
            Vec3 cameraPos,
            @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
    ) {
        BlockEntityRenderer.super.extractRenderState(
                blockEntity,
                state,
                tickProgress,
                cameraPos,
                crumblingOverlay
        );

        state.setCarpet(blockEntity.getCarpet());
        state.setPattern(blockEntity.getPattern());
        state.setBorder(blockEntity.getBorder());
        state.setLayersSwapped(blockEntity.isLayersSwapped());
        state.setFacing(
                blockEntity.getBlockState().getValue(PrismaticCarpetBlock.FACING)
        );
    }

    @Override
    public void submit(
            PrismaticCarpetBlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraState
    ) {
        if (state.getCarpet().isEmpty()) {
            return;
        }

        DataComponentMap components = DataComponentMap.builder()
                .set(
                        PrismaticCarpetComponents.PRISMATIC_CARPET_DATA,
                        new PrismaticCarpetData(
                                state.getCarpet(),
                                state.getPattern(),
                                state.getBorder(),
                                state.isLayersSwapped()
                        )
                )
                .build();

        poseStack.pushPose();
        poseStack.translate(0.5, 0.0, 0.5);
        float rotation = switch (state.getFacing()) {
            case NORTH -> 0.0F;
            case EAST -> 270.0F;
            case SOUTH -> 180.0F;
            case WEST -> 90.0F;
            default -> 0.0F;
        };
        poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
        this.carpetRenderer.submit(
                components,
                poseStack,
                submitNodeCollector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                false,
                0
        );
        poseStack.popPose();
    }
}
