package me.htrewrite.client.customgui;

import net.minecraft.client.gui.GuiMainMenu;

public class CustomMainMenuGui extends GuiMainMenu {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawHoveringText("I added some music, change it on htRewrite\\resources\\sound\\music\\main.mp3", 0, 0);
    }
}