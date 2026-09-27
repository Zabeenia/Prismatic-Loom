package zabeenia.prismaticcarpets.client;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class PrismaticCarpetBlockEntityRenderState
        extends BlockEntityRenderState {

    private String carpet = "";
    private String pattern = "";
    private String border = "";
    private boolean layersSwapped = false;
    private Direction facing = Direction.NORTH;

    public String getCarpet() {
        return carpet;
    }

    public String getPattern() {
        return pattern;
    }

    public String getBorder() {
        return border;
    }

    public boolean isLayersSwapped() {
        return layersSwapped;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setCarpet(String carpet) {
        this.carpet = carpet;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setLayersSwapped(boolean layersSwapped) {
        this.layersSwapped = layersSwapped;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }
}
