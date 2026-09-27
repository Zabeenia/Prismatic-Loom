package zabeenia.prismaticcarpets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PrismaticCarpetData(
        String carpet,
        String pattern,
        String border,
        boolean layersSwapped
) {

    public static final Codec<PrismaticCarpetData> CODEC =
            RecordCodecBuilder.create(builder ->
                    builder.group(
                            Codec.STRING.fieldOf("carpet")
                                    .forGetter(PrismaticCarpetData::carpet),
                            Codec.STRING.optionalFieldOf("pattern", "")
                                    .forGetter(PrismaticCarpetData::pattern),
                            Codec.STRING.optionalFieldOf("border", "")
                                    .forGetter(PrismaticCarpetData::border),
                            Codec.BOOL.optionalFieldOf("layers_swapped", false)
                                    .forGetter(PrismaticCarpetData::layersSwapped)
                    ).apply(builder, PrismaticCarpetData::new)
            );

    public static final StreamCodec<FriendlyByteBuf, PrismaticCarpetData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    PrismaticCarpetData::carpet,
                    ByteBufCodecs.STRING_UTF8,
                    PrismaticCarpetData::pattern,
                    ByteBufCodecs.STRING_UTF8,
                    PrismaticCarpetData::border,
                    ByteBufCodecs.BOOL,
                    PrismaticCarpetData::layersSwapped,
                    PrismaticCarpetData::new
            );
}