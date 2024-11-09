package im.expensive.ui.display.impl;

import im.expensive.ui.display.ElementRenderer;
import im.expensive.events.EventDisplay;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.font.Fonts;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.math.MathHelper;

@RequiredArgsConstructor
public class CoordsRenderer implements ElementRenderer {

    private float posY = 0;
    private float targetPosY = 0;

    @Override
    public void render(EventDisplay eventDisplay) {
        float offset = 3;
        float fontSize = 7;
        float fontHeight = Fonts.sfui.getHeight(fontSize);

        targetPosY = calculateInitialPosY(eventDisplay);

        // Анимация
        posY += (targetPosY - posY) * 0.2f; // 0.1f - скорость анимации

        float stringWidth = Fonts.sfui.getWidth("BPS: ", fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), "BPS: ", offset + 1, posY - 1, -1, fontSize, 0.05f);
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), String.format("%.2f", calculateBPS()), offset + 1 + stringWidth, posY - 1, ColorUtils.rgb(112, 0, 255), fontSize, 0.05f);

        stringWidth = Fonts.sfui.getWidth("XYZ: ", fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), "XYZ: ", offset + 42, posY - 1, -1, fontSize, 0.05f);
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), (int) mc.player.getPosX() + ", "
                + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), offset + 42 + stringWidth, posY - 1, ColorUtils.rgb(112, 0, 255), fontSize, 0.05f);
    }

    private float calculateInitialPosY(EventDisplay eventDisplay) {
        float offset = -5;
        float fontSize = 7;
        float fontHeight = Fonts.sfui.getHeight(fontSize);

        float posY = window.getScaledHeight() - offset - fontHeight * 2; // уменьшаем posY на высоту шрифта

        if (mc.currentScreen instanceof ChatScreen) {
            int chatHeight = MathHelper.floor(mc.gameSettings.chatHeightFocused * 12);
            posY -= chatHeight;
        }

        return posY;
    }

    private double calculateBPS() {
        return Math.hypot(mc.player.prevPosX - mc.player.getPosX(), mc.player.prevPosZ - mc.player.getPosZ()) * 20;
    }
}