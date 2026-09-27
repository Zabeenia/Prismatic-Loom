package zabeenia.prismaticcarpets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;

public class PrismaticCarpetBlockEntity extends BlockEntity {

    private String carpet = "";
    private String pattern = "";
    private String border = "";
    private boolean layersSwapped = false;

    public PrismaticCarpetBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(ModBlockEntities.PRISMATIC_CARPET, pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putString("carpet", this.carpet);
        output.putString("pattern", this.pattern);
        output.putString("border", this.border);
        output.putBoolean("layers_swapped", this.layersSwapped);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.carpet = input.getStringOr("carpet", "");
        this.pattern = input.getStringOr("pattern", "");
        this.border = input.getStringOr("border", "");
        this.layersSwapped = input.getBooleanOr("layers_swapped", false);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(
                PrismaticCarpetComponents.PRISMATIC_CARPET_DATA,
                new PrismaticCarpetData(this.carpet, this.pattern, this.border, this.layersSwapped)
        );
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setCarpetData(PrismaticCarpetData data) {
        this.carpet = data.carpet();
        this.pattern = data.pattern();
        this.border = data.border();
        this.layersSwapped = data.layersSwapped();

        this.setChanged();
    }

    public String getCarpet() {
        return this.carpet;
    }

    public String getPattern() {
        return this.pattern;
    }

    public String getBorder() {
        return this.border;
    }

    public boolean isLayersSwapped() {
        return this.layersSwapped;
    }

}
