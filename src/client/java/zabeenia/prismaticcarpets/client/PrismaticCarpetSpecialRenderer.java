package zabeenia.prismaticcarpets.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import org.joml.Vector3fc;

import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;

import zabeenia.prismaticcarpets.PrismaticCarpetComponents;
import zabeenia.prismaticcarpets.PrismaticCarpetData;
import zabeenia.prismaticcarpets.PrismaticCarpets;

public class PrismaticCarpetSpecialRenderer
        implements SpecialModelRenderer<DataComponentMap> {

    private final Model.Simple model;
    private final Model.Simple fringeModel;
    private final SpriteGetter sprites;

    public PrismaticCarpetSpecialRenderer(
            Model.Simple model,
            Model.Simple fringeModel,
            SpriteGetter sprites
    ) {
        this.model = model;
        this.fringeModel = fringeModel;
        this.sprites = sprites;
    }

    @Override
    public @Nullable DataComponentMap extractArgument(ItemStack stack) {
        return stack.immutableComponents();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.model.root().getExtentsForGui(poseStack, output);
        this.fringeModel.root().getExtentsForGui(poseStack, output);
    }

    private SpriteId getSprite(String path) {
        return Sheets.BLOCKS_MAPPER.apply(
                PrismaticCarpets.id(path)
        );
    }

    @Override
    public void submit(
            @Nullable DataComponentMap components,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int lightCoords,
            int overlayCoords,
            boolean hasFoil,
            int outlineColor
    ) {
        if (components == null) {
            return;
        }

        PrismaticCarpetData data =
                components.get(
                        PrismaticCarpetComponents.PRISMATIC_CARPET_DATA
                );

        if (data == null || data.carpet().isEmpty()) {
            return;
        }

        SpriteId carpetSprite =
                getSprite(data.carpet() + "_carpet");

        SpriteId patternSprite = data.pattern().isEmpty()
                ? null
                : getSprite("patterns/" + data.pattern());
        SpriteId borderSprite = data.border().isEmpty()
                ? null
                : getSprite("borders/" + data.border());
        poseStack.scale(0.5F, 0.5F, 0.5F);

        // Grundteppich
        submitNodeCollector.submitModel(
                this.model,
                Unit.INSTANCE,
                poseStack,
                lightCoords,
                overlayCoords,
                -1,
                carpetSprite,
                this.sprites,
                outlineColor
        );

        // Standard: Teppich → Pattern → Border
        if (!data.layersSwapped()) {

            if (patternSprite != null) {
                submitNodeCollector.submitModel(
                        this.model,
                        Unit.INSTANCE,
                        poseStack,
                        lightCoords,
                        overlayCoords,
                        -1,
                        patternSprite,
                        this.sprites,
                        outlineColor
                );
            }

            if (borderSprite != null) {
                submitNodeCollector.submitModel(
                        this.model,
                        Unit.INSTANCE,
                        poseStack,
                        lightCoords,
                        overlayCoords,
                        -1,
                        borderSprite,
                        this.sprites,
                        outlineColor
                );
            }

        } else {

            // Getauscht: Teppich → Border → Pattern

            if (borderSprite != null) {
                submitNodeCollector.submitModel(
                        this.model,
                        Unit.INSTANCE,
                        poseStack,
                        lightCoords,
                        overlayCoords,
                        -1,
                        borderSprite,
                        this.sprites,
                        outlineColor
                );
            }

            if (patternSprite != null) {
                submitNodeCollector.submitModel(
                        this.model,
                        Unit.INSTANCE,
                        poseStack,
                        lightCoords,
                        overlayCoords,
                        -1,
                        patternSprite,
                        this.sprites,
                        outlineColor
                );
            }
        }

        // Zusätzliche Fringe-Geometrie folgt derselben Pose und bleibt die oberste Lage.
        submitNodeCollector.submitModel(
                this.fringeModel,
                Unit.INSTANCE,
                poseStack,
                lightCoords,
                overlayCoords,
                -1,
                carpetSprite,
                this.sprites,
                outlineColor
        );

        if (hasFoil) {
            submitNodeCollector.order(1).submitModel(
                    this.model,
                    Unit.INSTANCE,
                    poseStack,
                    RenderTypes.patternedShieldGlint(),
                    lightCoords,
                    overlayCoords,
                    -1,
                    this.sprites.get(carpetSprite),
                    0
            );
        }
    }

    public record Unbaked()
            implements SpecialModelRenderer.Unbaked<DataComponentMap> {

        public static final MapCodec<Unbaked> MAP_CODEC =
                MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public PrismaticCarpetSpecialRenderer bake(
                SpecialModelRenderer.BakingContext context
        ) {
            Model.Simple carpetModel = new Model.Simple(
                    context.entityModelSet().bakeLayer(
                            PrismaticCarpetLayers.PRISMATIC_CARPET
                    ),
                    RenderTypes::entityCutout
            );
            return new PrismaticCarpetSpecialRenderer(
                    carpetModel,
                    new Model.Simple(
                            PrismaticCarpetLayers.createFringePart(),
                            RenderTypes::entityCutout
                    ),
                    context.sprites()
            );
        }
    }
}
