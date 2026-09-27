package zabeenia.prismaticcarpets.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeColor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.registries.BuiltInRegistries;

import zabeenia.prismaticcarpets.PrismaticLoomMenu;
import zabeenia.prismaticcarpets.PrismaticLoomSelections;
import zabeenia.prismaticcarpets.PrismaticCarpets;
import zabeenia.prismaticcarpets.ModBlocks;
import zabeenia.prismaticcarpets.PrismaticCarpetComponents;
import zabeenia.prismaticcarpets.PrismaticCarpetData;

public class PrismaticLoomScreen extends AbstractContainerScreen<PrismaticLoomMenu> {

    private static final Identifier GUI_TEXTURE =
            PrismaticCarpets.id("textures/gui/prismatic_loom_gui.png");

    private static final Identifier BLUE_BOOK_CLOSED =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_blue_book_closed.png");

    private static final Identifier BLUE_BOOK_OPEN =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_blue_book_open.png");

    private static final Identifier GREEN_BOOK_CLOSED =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_green_book_closed.png");

    private static final Identifier GREEN_BOOK_OPEN =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_green_book_open.png");

    private static final Identifier CHANGE_LAYERS =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_change_layers.png");

    private static final Identifier CHANGE_LAYERS_PRESSED =
            PrismaticCarpets.id("textures/gui/prismatic_loom_button_change_layers_pressed.png");

    private static final Identifier BORDER_BOOK_BLUE =
            PrismaticCarpets.id("textures/gui/prismatic_border_book_blue.png");

    private static final Identifier PATTERN_BOOK_GREEN =
            PrismaticCarpets.id("textures/gui/prismatic_pattern_book_green.png");

    private static final Identifier PAGE_LEFT =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_green_left.png");

    private static final Identifier PAGE_RIGHT =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_green_right.png");

    private static final Identifier PAGE_LEFT_CLICK =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_green_left_click.png");

    private static final Identifier PAGE_RIGHT_CLICK =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_green_right_click.png");

    private static final Identifier BORDER_PAGE_LEFT =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_blue_left.png");

    private static final Identifier BORDER_PAGE_RIGHT =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_blue_right.png");

    private static final Identifier BORDER_PAGE_LEFT_CLICK =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_blue_left_click.png");

    private static final Identifier BORDER_PAGE_RIGHT_CLICK =
            PrismaticCarpets.id("textures/gui/prismatic_gui_arrow_blue_right_click.png");


    private boolean blueBookOpen = false;
    private boolean greenBookOpen = false;
    private boolean layersSwapped = false;

    private int patternPage = 0;
    private int borderPage = 0;
    private int hoveredPattern = -1;
    private int hoveredBorder = -1;
    private int selectedPattern = -1;
    private int selectedBorder = -1;

    private int leftArrowClickTicks = 0;
    private int rightArrowClickTicks = 0;
    private int borderLeftArrowClickTicks = 0;
    private int borderRightArrowClickTicks = 0;

    private static final int ARROW_CLICK_DURATION = 3;


    private static final String[] PATTERNS = PrismaticLoomSelections.PATTERNS;
    private static final String[] BORDERS = PrismaticLoomSelections.BORDERS;


    public PrismaticLoomScreen(
            PrismaticLoomMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(menu, inventory, title, 176, 166);
    }


    @Override
    public void extractBackground(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {

        // Haupt-GUI
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                GUI_TEXTURE,
                this.leftPos,
                this.topPos,
                0.0F,
                0.0F,
                256,
                256,
                256,
                256
        );

        ItemStack carpetStack = this.menu.getSlot(1).getItem();

        if (!carpetStack.isEmpty()) {
            Identifier itemId = BuiltInRegistries.ITEM.getKey(carpetStack.getItem());
            String itemName = itemId.getPath();
            DyeColor carpetColor = null;

            if (itemId.getNamespace().equals("minecraft") && itemName.endsWith("_carpet")) {
                carpetColor = DyeColor.byName(
                        itemName.substring(0, itemName.length() - "_carpet".length()), null
                );
            } else if (carpetStack.is(ModBlocks.PRISMATIC_CARPET.asItem())) {
                PrismaticCarpetData data = carpetStack.get(
                        PrismaticCarpetComponents.PRISMATIC_CARPET_DATA
                );
                if (data != null) {
                    carpetColor = DyeColor.byName(data.carpet(), null);
                }
            }

            if (carpetColor != null) {
                Identifier carpetTexture =
                        PrismaticCarpets.id("textures/block/" + carpetColor.getName() + "_carpet.png");

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        carpetTexture,
                        this.leftPos + 72,
                        this.topPos + 26,
                        0.0F,
                        0.0F,
                        32,
                        32,
                        32,
                        32
                );

                if (!layersSwapped) {

                    // Standard: Teppich → Pattern → Border

                    if (selectedPattern != -1) {
                        Identifier patternTexture =
                                PrismaticCarpets.id("textures/patterns/" + PATTERNS[selectedPattern] + ".png");

                        graphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                patternTexture,
                                this.leftPos + 72,
                                this.topPos + 26,
                                0.0F,
                                0.0F,
                                32,
                                32,
                                32,
                                32
                        );
                    }

                    if (selectedBorder != -1) {
                        Identifier borderTexture =
                                PrismaticCarpets.id("textures/borders/" + BORDERS[selectedBorder] + ".png");

                        graphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                borderTexture,
                                this.leftPos + 72,
                                this.topPos + 26,
                                0.0F,
                                0.0F,
                                32,
                                32,
                                32,
                                32
                        );
                    }

                } else {

                    // Gedrückt: Teppich → Border → Pattern

                    if (selectedBorder != -1) {
                        Identifier borderTexture =
                                PrismaticCarpets.id("textures/borders/" + BORDERS[selectedBorder] + ".png");

                        graphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                borderTexture,
                                this.leftPos + 72,
                                this.topPos + 26,
                                0.0F,
                                0.0F,
                                32,
                                32,
                                32,
                                32
                        );
                    }

                    if (selectedPattern != -1) {
                        Identifier patternTexture =
                                PrismaticCarpets.id("textures/patterns/" + PATTERNS[selectedPattern] + ".png");

                        graphics.blit(
                                RenderPipelines.GUI_TEXTURED,
                                patternTexture,
                                this.leftPos + 72,
                                this.topPos + 26,
                                0.0F,
                                0.0F,
                                32,
                                32,
                                32,
                                32
                        );
                    }
                }
            }
        }

        // Blaues Buch
        Identifier blueBook =
                blueBookOpen ? BLUE_BOOK_OPEN : BLUE_BOOK_CLOSED;

        int blueBookWidth = 29;
        int blueBookHeight = 19;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                blueBook,
                this.leftPos + 23,
                this.topPos + 45,
                0.0F,
                0.0F,
                blueBookWidth,
                blueBookHeight,
                blueBookWidth,
                blueBookHeight
        );


        // Grünes Buch
        Identifier greenBook =
                greenBookOpen ? GREEN_BOOK_OPEN : GREEN_BOOK_CLOSED;

        int greenBookWidth = 29;
        int greenBookHeight = 19;

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                greenBook,
                this.leftPos + 23,
                this.topPos + 18,
                0.0F,
                0.0F,
                greenBookWidth,
                greenBookHeight,
                greenBookWidth,
                greenBookHeight
        );


        // Layer-Wechsel-Button
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                layersSwapped ? CHANGE_LAYERS_PRESSED : CHANGE_LAYERS,
                this.leftPos + 123,
                this.topPos + 44,
                0.0F,
                0.0F,
                18,
                18,
                18,
                18
        );


        // Blaues Border-Panel
        if (blueBookOpen) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    BORDER_BOOK_BLUE,
                    this.leftPos - 149,
                    this.topPos,
                    0.0F,
                    0.0F,
                    147,
                    166,
                    256,
                    256
            );
        }


        // Grünes Pattern-Panel
        if (greenBookOpen) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    PATTERN_BOOK_GREEN,
                    this.leftPos + 176,
                    this.topPos,
                    0.0F,
                    0.0F,
                    147,
                    166,
                    256,
                    256
            );
        }


        // Pattern-Seitenpfeile
        if (greenBookOpen) {

            int maxPage = (PATTERNS.length - 1) / 9;

            // Linker Pfeil
            if (patternPage > 0) {

                Identifier leftTexture =
                        leftArrowClickTicks > 0
                                ? PAGE_LEFT_CLICK
                                : PAGE_LEFT;

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        leftTexture,
                        this.leftPos + 221,
                        this.topPos + 140,
                        0.0F,
                        0.0F,
                        18,
                        18,
                        18,
                        18
                );
            }


            // Rechter Pfeil
            if (patternPage < maxPage) {

                Identifier rightTexture =
                        rightArrowClickTicks > 0
                                ? PAGE_RIGHT_CLICK
                                : PAGE_RIGHT;

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        rightTexture,
                        this.leftPos + 263,
                        this.topPos + 140,
                        0.0F,
                        0.0F,
                        18,
                        18,
                        18,
                        18
                );
            }
        }


        // Pattern-Auswahl
        if (greenBookOpen) {

            int start = patternPage * 9;
            int end = Math.min(start + 9, PATTERNS.length);

            hoveredPattern = -1;

            for (int i = start; i < end; i++) {

                int slot = i - start;

                int column = slot % 3;
                int row = slot / 3;

                int x = this.leftPos + 193 + column * 42;

                int y = this.topPos
                        + 34
                        + row * 34
                        + (row > 0 ? 1 : 0)
                        + (row == 2 ? 1 : 0);

                if (mouseX >= x && mouseX < x + 32
                        && mouseY >= y && mouseY < y + 32) {

                    hoveredPattern = i;
                }

                Identifier pattern =
                        PrismaticCarpets.id(
                                "textures/patterns/" + PATTERNS[i] + ".png"
                        );

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        pattern,
                        x,
                        y,
                        0.0F,
                        0.0F,
                        32,
                        32,
                        32,
                        32
                );
            }
        }

        // Pattern-Hover
        if (hoveredPattern != -1) {

            graphics.text(this.font, getPatternName(hoveredPattern), this.leftPos + 250 - this.font.width(getPatternName(hoveredPattern)) / 2, this.topPos + 15, 0xFF354806, false);

            int slot = hoveredPattern - patternPage * 9;

            int column = slot % 3;
            int row = slot / 3;

            int x = this.leftPos + 193 + column * 42;

            int y = this.topPos
                    + 34
                    + row * 34
                    + (row > 0 ? 1 : 0)
                    + (row == 2 ? 1 : 0);

            graphics.fill(
                    x,
                    y,
                    x + 32,
                    y + 1,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x,
                    y + 31,
                    x + 32,
                    y + 32,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x,
                    y,
                    x + 1,
                    y + 32,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x + 31,
                    y,
                    x + 32,
                    y + 32,
                    0xFFFFFFFF
            );
        }

        // Border-Auswahl
        if (blueBookOpen) {

            int start = borderPage * 9;
            int end = Math.min(start + 9, BORDERS.length);

            hoveredBorder = -1;

            for (int i = start; i < end; i++) {

                int slot = i - start;

                int column = slot % 3;
                int row = slot / 3;

                int x = this.leftPos - 132 + column * 42;

                int y = this.topPos
                        + 34
                        + row * 34
                        + (row > 0 ? 1 : 0)
                        + (row == 2 ? 1 : 0);

                if (mouseX >= x && mouseX < x + 32
                        && mouseY >= y && mouseY < y + 32) {

                    hoveredBorder = i;
                }

                Identifier border =
                        PrismaticCarpets.id(
                                "textures/borders/" + BORDERS[i] + ".png"
                        );

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        border,
                        x,
                        y,
                        0.0F,
                        0.0F,
                        32,
                        32,
                        32,
                        32
                );
            }
            if (hoveredBorder != -1) {
                graphics.text(this.font, getBorderName(hoveredBorder), this.leftPos - 75 - this.font.width(getBorderName(hoveredBorder)) / 2, this.topPos + 15, 0xFF00326C, false);
            }
        }

        // Border-Hover
        if (hoveredBorder != -1) {

            int slot = hoveredBorder - borderPage * 9;

            int column = slot % 3;
            int row = slot / 3;

            int x = this.leftPos - 132 + column * 42;

            int y = this.topPos
                    + 34
                    + row * 34
                    + (row > 0 ? 1 : 0)
                    + (row == 2 ? 1 : 0);

            graphics.fill(
                    x,
                    y,
                    x + 32,
                    y + 1,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x,
                    y + 31,
                    x + 32,
                    y + 32,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x,
                    y,
                    x + 1,
                    y + 32,
                    0xFFFFFFFF
            );

            graphics.fill(
                    x + 31,
                    y,
                    x + 32,
                    y + 32,
                    0xFFFFFFFF
            );
        }

// Border-Seitenpfeile
        if (blueBookOpen) {

            int maxPage = (BORDERS.length - 1) / 9;

            // Linker Pfeil
            if (borderPage > 0) {

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        borderLeftArrowClickTicks > 0
                                ? BORDER_PAGE_LEFT_CLICK
                                : BORDER_PAGE_LEFT,
                        this.leftPos - 104,
                        this.topPos + 140,
                        0.0F,
                        0.0F,
                        18,
                        18,
                        18,
                        18
                );
            }

            // Rechter Pfeil
            if (borderPage < maxPage) {

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        borderRightArrowClickTicks > 0
                                ? BORDER_PAGE_RIGHT_CLICK
                                : BORDER_PAGE_RIGHT,
                        this.leftPos - 62,
                        this.topPos + 140,
                        0.0F,
                        0.0F,
                        18,
                        18,
                        18,
                        18
                );
            }
        }
    }


    @Override
    public void containerTick() {
        super.containerTick();

        if (leftArrowClickTicks > 0) {
            leftArrowClickTicks--;
        }

        if (rightArrowClickTicks > 0) {
            rightArrowClickTicks--;
        }

        if (borderLeftArrowClickTicks > 0) {
            borderLeftArrowClickTicks--;
        }

        if (borderRightArrowClickTicks > 0) {
            borderRightArrowClickTicks--;
        }

    }


    @Override
    public boolean mouseClicked(
            MouseButtonEvent event,
            boolean doubleClick
    ) {

        double mouseX = event.x();
        double mouseY = event.y();

        double bookX = mouseX - this.leftPos;
        double bookY = mouseY - this.topPos;


        // Blaues Buch
        if (bookX >= 23 && bookX <= 52
                && bookY >= 45 && bookY <= 64) {

            blueBookOpen = !blueBookOpen;

            if (!blueBookOpen) {
                selectedBorder = -1;
                this.minecraft.gameMode.handleInventoryButtonClick(
                        this.menu.containerId,
                        PrismaticLoomMenu.CLEAR_BORDER_BUTTON_ID
                );
            }

            return true;
        }


        // Grünes Buch
        if (bookX >= 23 && bookX <= 52
                && bookY >= 18 && bookY <= 37) {

            greenBookOpen = !greenBookOpen;

            if (!greenBookOpen) {
                selectedPattern = -1;
                this.minecraft.gameMode.handleInventoryButtonClick(
                        this.menu.containerId,
                        PrismaticLoomMenu.CLEAR_PATTERN_BUTTON_ID
                );
            }

            return true;
        }


        // Layer-Wechsel-Button
        if (bookX >= 123 && bookX <= 141
                && bookY >= 44 && bookY <= 62) {

            this.minecraft.gameMode.handleInventoryButtonClick(
                    this.menu.containerId,
                    PrismaticLoomMenu.SWAP_LAYERS_BUTTON_ID
            );

            layersSwapped = !layersSwapped;

            return true;
        }


        // Linker Pattern-Pfeil
        if (greenBookOpen
                && mouseX >= this.leftPos + 221
                && mouseX <= this.leftPos + 240
                && mouseY >= this.topPos + 140
                && mouseY <= this.topPos + 158) {

            if (patternPage > 0) {
                patternPage--;
                leftArrowClickTicks = ARROW_CLICK_DURATION;
            }

            return true;
        }


        // Rechter Pattern-Pfeil
        if (greenBookOpen
                && mouseX >= this.leftPos + 263
                && mouseX <= this.leftPos + 282
                && mouseY >= this.topPos + 140
                && mouseY <= this.topPos + 158) {

            int maxPage = (PATTERNS.length - 1) / 9;

            if (patternPage < maxPage) {
                patternPage++;
                rightArrowClickTicks = ARROW_CLICK_DURATION;
            }

            return true;
        }

        if (greenBookOpen) {
            int start = patternPage * 9;
            int end = Math.min(start + 9, PATTERNS.length);

            for (int i = start; i < end; i++) {
                int slot = i - start;
                int column = slot % 3;
                int row = slot / 3;

                int x = this.leftPos + 194 + column * 42;
                int y = this.topPos
                        + 34
                        + row * 34
                        + (row > 0 ? 1 : 0)
                        + (row == 2 ? 1 : 0);

                if (mouseX >= x && mouseX < x + 32
                        && mouseY >= y && mouseY < y + 32) {

                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId,
                            PrismaticLoomMenu.PATTERN_BUTTON_START_ID + i
                    );

                    selectedPattern = i;
                    return true;
                }
            }
        }
// Border-Auswahl
        if (blueBookOpen) {
            int start = borderPage * 9;
            int end = Math.min(start + 9, BORDERS.length);

            for (int i = start; i < end; i++) {
                int slot = i - start;
                int column = slot % 3;
                int row = slot / 3;

                int x = this.leftPos - 132 + column * 42;
                int y = this.topPos
                        + 34
                        + row * 34
                        + (row > 0 ? 1 : 0)
                        + (row == 2 ? 1 : 0);

                if (mouseX >= x && mouseX < x + 32
                        && mouseY >= y && mouseY < y + 32) {

                    selectedBorder = i;
                    this.minecraft.gameMode.handleInventoryButtonClick(
                            this.menu.containerId,
                            PrismaticLoomMenu.BORDER_BUTTON_START_ID + i
                    );
                    return true;
                }
            }
        }
        // Linker Border-Pfeil
        if (blueBookOpen
                && mouseX >= this.leftPos - 104
                && mouseX <= this.leftPos - 86
                && mouseY >= this.topPos + 140
                && mouseY <= this.topPos + 158) {

            if (borderPage > 0) {
                borderPage--;
                borderLeftArrowClickTicks = ARROW_CLICK_DURATION;
            }

            return true;
        }


// Rechter Border-Pfeil
        if (blueBookOpen
                && mouseX >= this.leftPos - 62
                && mouseX <= this.leftPos - 44
                && mouseY >= this.topPos + 140
                && mouseY <= this.topPos + 158) {

            int maxPage = (BORDERS.length - 1) / 9;

            if (borderPage < maxPage) {
                borderPage++;
                borderRightArrowClickTicks = ARROW_CLICK_DURATION;
            }

            return true;
        }

        return super.mouseClicked(event, doubleClick);
    }

    private net.minecraft.network.chat.Component getPatternName(int index) {
        String name = PATTERNS[index].substring("prismatic_".length());

        return net.minecraft.network.chat.Component.translatable(
                "prismatic-carpets.pattern." + name
        );
    }

    private net.minecraft.network.chat.Component getBorderName(int index) {
        String name = BORDERS[index].substring("prismatic_border_".length());

        return net.minecraft.network.chat.Component.translatable(
                "prismatic-carpets.border." + name.replaceFirst("_", ".")
        );
    }

}
