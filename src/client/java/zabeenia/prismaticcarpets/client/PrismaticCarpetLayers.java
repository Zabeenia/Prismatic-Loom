package zabeenia.prismaticcarpets.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joml.Vector3f;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;

import zabeenia.prismaticcarpets.PrismaticCarpets;

public class PrismaticCarpetLayers {

    private static final float TEXTURE_SIZE = 32.0F;
    private static final float MODEL_SCALE = 2.0F;

    public static final ModelLayerLocation PRISMATIC_CARPET =
            new ModelLayerLocation(
                    PrismaticCarpets.id("prismatic_carpet"),
                    "main"
            );

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition carpet = root.addOrReplaceChild(
                "carpet",
                CubeListBuilder.create()
                        .texOffs(-64, 0)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.DOWN)
                        )
                        .texOffs(0, -1)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.WEST)
                        )
                        .texOffs(-32, -1)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.NORTH)
                        )
                        .texOffs(-64, -1)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.EAST)
                        )
                        .texOffs(-96, -1)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.SOUTH)
                        ),
                PartPose.ZERO.scaled(1.0F, 2.0F, 1.0F)
        );

        carpet.addOrReplaceChild(
                "top",
                CubeListBuilder.create()
                        .mirror()
                        .texOffs(-64, 0)
                        .addBox(
                                -16.0F,
                                0.0F,
                                -16.0F,
                                32.0F,
                                1.0F,
                                32.0F,
                                Set.of(Direction.UP)
                        ),
                PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 32, 32);

    }

    /** Builds 36 UP quads from new_fringes.json, rendered with the carpet sprite. */
    public static ModelPart createFringePart() {
        Map<String, ModelPart> pieces = new LinkedHashMap<>();

        addFringe(pieces, "tassel_west_0",
                -1.2479F, 0.17621F, 15.0F, -0.2479F, 0.17621F, 16.0F,
                -1.2489F, 0.17521F, 14.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_1",
                -1.248F, 0.17621F, 1.0F, -0.248F, 0.17621F, 2.0F,
                -1.249F, 0.17521F, 0.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_2",
                -1.248F, 0.17621F, 3.0F, -0.248F, 0.17621F, 4.0F,
                -1.249F, 0.17521F, 2.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_3",
                -1.248F, 0.17621F, 5.0F, -0.248F, 0.17621F, 6.0F,
                -1.249F, 0.17521F, 4.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_4",
                -1.248F, 0.17621F, 7.0F, -0.248F, 0.17621F, 8.0F,
                -1.249F, 0.17521F, 6.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_5",
                -1.248F, 0.17621F, 9.0F, -0.248F, 0.17621F, 10.0F,
                -1.249F, 0.17521F, 8.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_6",
                -1.248F, 0.17621F, 11.0F, -0.248F, 0.17621F, 12.0F,
                -1.249F, 0.17521F, 10.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_west_7",
                -1.248F, 0.17621F, 13.0F, -0.248F, 0.17621F, 14.0F,
                -1.249F, 0.17521F, 12.999F,
                0.0F, 0.0F, 35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "edge_west",
                -0.43F, 0.74977F, 0.0F, 0.068F, 0.74977F, 16.0F,
                -0.431F, 0.74877F, 0.999F,
                0.0F, 0.0F, 30.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_0",
                16.4308F, 0.74994F, 14.002F, 17.4308F, 0.74994F, 15.0F,
                16.4298F, 0.74894F, 14.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_1",
                16.4308F, 0.74994F, 0.002F, 17.4308F, 0.74994F, 1.0F,
                16.4298F, 0.74894F, 0.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_2",
                16.4308F, 0.74994F, 12.002F, 17.4308F, 0.74994F, 13.0F,
                16.4298F, 0.74894F, 12.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_3",
                16.4308F, 0.74994F, 10.002F, 17.4308F, 0.74994F, 11.0F,
                16.4298F, 0.74894F, 10.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_4",
                16.4308F, 0.74994F, 8.002F, 17.4308F, 0.74994F, 9.0F,
                16.4298F, 0.74894F, 8.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_5",
                16.4308F, 0.74994F, 6.002F, 17.4308F, 0.74994F, 7.0F,
                16.4298F, 0.74894F, 6.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_6",
                16.4308F, 0.74994F, 4.002F, 17.4308F, 0.74994F, 5.0F,
                16.4298F, 0.74894F, 4.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_east_7",
                16.4308F, 0.74994F, 2.002F, 17.4308F, 0.74994F, 3.0F,
                16.4298F, 0.74894F, 2.001F,
                0.0F, 0.0F, -35.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "edge_east",
                15.998F, 0.99977F, 0.002F, 16.498F, 0.99977F, 16.0F,
                15.997F, 0.99877F, 14.001F,
                0.0F, 0.0F, -30.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_0",
                2.002F, 0.74983F, 16.43F, 3.002F, 0.74983F, 17.428F,
                2.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_1",
                4.002F, 0.74983F, 16.43F, 5.002F, 0.74983F, 17.428F,
                4.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_2",
                6.002F, 0.74983F, 16.43F, 7.002F, 0.74983F, 17.428F,
                6.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_3",
                8.002F, 0.74983F, 16.43F, 9.002F, 0.74983F, 17.428F,
                8.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_4",
                10.002F, 0.74983F, 16.43F, 11.002F, 0.74983F, 17.428F,
                10.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_5",
                12.002F, 0.74983F, 16.43F, 13.002F, 0.74983F, 17.428F,
                12.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_6",
                14.002F, 0.74983F, 16.43F, 15.002F, 0.74983F, 17.428F,
                14.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_south_7",
                16.002F, 0.74983F, 16.43F, 17.002F, 0.74983F, 17.428F,
                16.001F, 0.74883F, 16.429F,
                90.0F, -58.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "edge_south",
                2.002F, 0.99977F, 1.999F, 2.502F, 0.99977F, 17.997F,
                2.001F, 0.99877F, 15.998F,
                90.0F, -60.0F, -90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "edge_north",
                7.751F, 0.86807F, -8.2395F, 8.249F, 0.86807F, 7.7605F,
                8.0F, 0.91707F, -0.2395F,
                -90.0F, -60.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_0",
                6.75F, 0.27439F, -9.1874F, 7.75F, 0.27439F, -8.1894F,
                6.999F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_1",
                -7.25F, 0.27439F, -9.1874F, -6.25F, 0.27439F, -8.1894F,
                -7.001F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_2",
                -5.25F, 0.27439F, -9.1874F, -4.25F, 0.27439F, -8.1894F,
                -5.001F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_3",
                -3.25F, 0.27439F, -9.1874F, -2.25F, 0.27439F, -8.1894F,
                -3.001F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_4",
                -1.25F, 0.27439F, -9.1874F, -0.25F, 0.27439F, -8.1894F,
                -1.001F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_5",
                0.75F, 0.27439F, -9.1874F, 1.75F, 0.27439F, -8.1894F,
                0.999F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_6",
                2.75F, 0.27439F, -9.1874F, 3.75F, 0.27439F, -8.1894F,
                2.999F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        addFringe(pieces, "tassel_north_7",
                4.75F, 0.27439F, -9.1874F, 5.75F, 0.27439F, -8.1894F,
                4.999F, 0.52339F, -1.1884F,
                -90.0F, -55.0F, 90.0F,
                new Uv(0.0F, 0.0F, 1.5F, 1.0F, 0));

        return new ModelPart(List.of(), pieces);
    }

    private static void addFringe(
            Map<String, ModelPart> pieces,
            String name,
            float fromX, float y, float fromZ,
            float toX, float toY, float toZ,
            float pivotX, float pivotY, float pivotZ,
            float xDegrees, float yDegrees, float zDegrees,
            Uv upUv
    ) {
        float minX = (fromX - pivotX) * MODEL_SCALE;
        float maxX = (toX - pivotX) * MODEL_SCALE;
        float minZ = (fromZ - pivotZ) * MODEL_SCALE;
        float maxZ = (toZ - pivotZ) * MODEL_SCALE;
        float localY = (y - pivotY) * MODEL_SCALE;

        ModelPart.Cube cube = new ModelPart.Cube(
                0, 0,
                minX, localY, minZ,
                maxX - minX, 0.0F, maxZ - minZ,
                0.0F, 0.0F, 0.0F,
                false, TEXTURE_SIZE, TEXTURE_SIZE,
                Set.of(Direction.UP)
        );

        ModelPart.Vertex[] upVertices = makeVertices(
                minX, localY, minZ, maxX, maxZ, upUv
        );
        cube.polygons[0] = new ModelPart.Polygon(
                upVertices, new Vector3f(0.0F, 1.0F, 0.0F)
        );

        ModelPart piece = new ModelPart(List.of(cube), Map.of());
        piece.setPos(
                pivotX * MODEL_SCALE - 16.0F,
                pivotY * MODEL_SCALE,
                pivotZ * MODEL_SCALE - 16.0F
        );
        // ModelPart applies these Euler angles in Z-Y-X order, matching the reference.
        piece.xRot = (float) Math.toRadians(xDegrees);
        piece.yRot = (float) Math.toRadians(yDegrees);
        piece.zRot = (float) Math.toRadians(zDegrees);
        pieces.put(name, piece);
    }

    private static ModelPart.Vertex[] makeVertices(
            float minX, float y, float minZ,
            float maxX, float maxZ,
            Uv uv
    ) {
        // One face winds toward +Y; entityCutout renders it from both sides.
        float[][] positions = {
                {minX, y, minZ}, {minX, y, maxZ},
                {maxX, y, maxZ}, {maxX, y, minZ}
        };
        float[][] texCoords = {
                {uv.u0, uv.v0}, {uv.u0, uv.v1},
                {uv.u1, uv.v1}, {uv.u1, uv.v0}
        };
        int turns = Math.floorMod(uv.rotation / 90, 4);
        for (int turn = 0; turn < turns; turn++) {
            float[] first = texCoords[0];
            texCoords[0] = texCoords[1];
            texCoords[1] = texCoords[2];
            texCoords[2] = texCoords[3];
            texCoords[3] = first;
        }

        ModelPart.Vertex[] vertices = new ModelPart.Vertex[4];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new ModelPart.Vertex(
                    positions[i][0], positions[i][1], positions[i][2],
                    texCoords[i][0] / 16.0F, texCoords[i][1] / 16.0F
            );
        }
        return vertices;
    }

    private record Uv(float u0, float v0, float u1, float v1, int rotation) {
    }
}
