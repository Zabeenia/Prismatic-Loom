package zabeenia.prismaticcarpets;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomModelData;

public class PrismaticLoomMenu extends AbstractContainerMenu {

    public static final int SWAP_LAYERS_BUTTON_ID = 2;
    public static final int PATTERN_BUTTON_START_ID = 10;
    public static final int CLEAR_PATTERN_BUTTON_ID =
            PATTERN_BUTTON_START_ID + PrismaticLoomSelections.PATTERNS.length;
    public static final int BORDER_BUTTON_START_ID = CLEAR_PATTERN_BUTTON_ID + 1;
    public static final int CLEAR_BORDER_BUTTON_ID =
            BORDER_BUTTON_START_ID + PrismaticLoomSelections.BORDERS.length;

    // Unsere drei Slots:
// 0 = Prismatisches Garn
// 1 = Blanko-Teppich
// 2 = Ergebnis
    private static final int INPUT_SLOTS = 3;

    private static final int INVENTORY_START = INPUT_SLOTS;
    private static final int INVENTORY_END =
            INVENTORY_START + Inventory.INVENTORY_SIZE;

    private final Container container;

    private String selectedPattern = "";
    private String selectedBorder = "";
    private boolean layersSwapped = false;

    public PrismaticLoomMenu(
            int containerId,
            Inventory inventory
    ) {
        this(
                containerId,
                inventory,
                new SimpleContainer(INPUT_SLOTS)
        );
    }

    public PrismaticLoomMenu(
            int containerId,
            Inventory inventory,
            Container container
    ) {
        super(ModMenuTypes.PRISMATIC_LOOM, containerId);

        this.container = container;

// Prismatisches Garn
        this.addSlot(
                new Slot(
                        this.container,
                        0,
                        124,
                        21
                ) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.is(ModItems.PRISMATIC_YARNS);
                    }

                    @Override
                    public void setChanged() {
                        super.setChanged();
                        updateResult();
                    }
                }
        );

// Blanko-Teppich
        this.addSlot(
                new Slot(
                        this.container,
                        1,
                        148,
                        21
                ) {
                    @Override
                    public void setChanged() {
                        super.setChanged();
                        updateResult();
                    }
                }
        );

// Ergebnis
        this.addSlot(
                new Slot(
                        this.container,
                        2,
                        148,
                        45
                ) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }


                    @Override
                    public void onTake(Player player, ItemStack stack) {
                        super.onTake(player, stack);

                        this.container.removeItem(0, 1);
                        this.container.removeItem(1, 1);

                        updateResult();
                    }
                }
        );

// Spielerinventar
        this.addStandardInventorySlots(
                inventory,
                8,
                84
        );
    }

    @Override
    public ItemStack quickMoveStack(
            Player player,
            int slotIndex
    ) {
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack clicked = stack.copy();

        if (slotIndex < INVENTORY_END) {
            if (!this.moveItemStackTo(
                    stack,
                    INVENTORY_START,
                    INVENTORY_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!this.moveItemStackTo(
                    stack,
                    0,
                    INPUT_SLOTS,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return clicked;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void setSelectedPattern(String pattern) {
        this.selectedPattern = pattern;
        this.updateResult();
    }

    public void setSelectedBorder(String border) {
        this.selectedBorder = border;
        this.updateResult();
    }

    public void setLayersSwapped(boolean swapped) {
        this.layersSwapped = swapped;
        this.updateResult();
    }

    private String getCarpetColor() {
        ItemStack carpetStack = this.container.getItem(1);

        if (carpetStack.isEmpty()) {
            return "";
        }

        String name = BuiltInRegistries.ITEM
                .getKey(carpetStack.getItem())
                .getPath();

        if (name.endsWith("_carpet")) {
            return name.substring(
                    0,
                    name.length() - "_carpet".length()
            );
        }

        return "";
    }

    public boolean canCraft() {
        ItemStack yarnStack = this.container.getItem(0);
        ItemStack carpetStack = this.container.getItem(1);

        return !yarnStack.isEmpty()
                && yarnStack.is(ModItems.PRISMATIC_YARNS)
                && !carpetStack.isEmpty()
                && Items.CARPET.asList().contains(carpetStack.getItem());
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == SWAP_LAYERS_BUTTON_ID) {
            this.layersSwapped = !this.layersSwapped;
            this.updateResult();
            return true;
        }

        if (id >= PATTERN_BUTTON_START_ID && id < CLEAR_PATTERN_BUTTON_ID) {
            this.selectedPattern = PrismaticLoomSelections.PATTERNS[
                    id - PATTERN_BUTTON_START_ID
            ];
            this.updateResult();
            return true;
        }

        if (id == CLEAR_PATTERN_BUTTON_ID) {
            this.selectedPattern = "";
            this.updateResult();
            return true;
        }

        if (id >= BORDER_BUTTON_START_ID && id < CLEAR_BORDER_BUTTON_ID) {
            this.selectedBorder = PrismaticLoomSelections.BORDERS[
                    id - BORDER_BUTTON_START_ID
            ];
            this.updateResult();
            return true;
        }

        if (id == CLEAR_BORDER_BUTTON_ID) {
            this.selectedBorder = "";
            this.updateResult();
            return true;
        }

        return false;
    }

    public void updateResult() {
        if (canCraft()) {
            ItemStack result =
                    ModBlocks.PRISMATIC_CARPET.asItem().getDefaultInstance();

            String pattern = selectedPattern;
            String border = selectedBorder;

            result.set(
                    PrismaticCarpetComponents.PRISMATIC_CARPET_DATA,
                    new PrismaticCarpetData(
                            getCarpetColor(),
                            pattern,
                            border,
                            layersSwapped
                    )
            );

            result.set(
                    DataComponents.CUSTOM_MODEL_DATA,
                    new CustomModelData(
                            List.of(),
                            List.of(),
                            List.of(getCarpetColor()),
                            List.of()
                    )
            );

            this.container.setItem(2, result);
        } else {
            this.container.setItem(2, ItemStack.EMPTY);
        }
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        this.container.setItem(2, ItemStack.EMPTY);
        this.clearContainer(player, this.container);
    }
}
