package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.events.EventDisplay;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.utils.client.PingUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.ResourceLocation;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer implements ElementRenderer {

    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/logo.png");

    // Добавьте переменную для состояния таба
    boolean isTabOpen = false; // Установите в true, если таб открыт

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posY = 4;
        float padding = 5;
        float extraPadding = 3;
        float fontSize = 6.5f;
        float iconSizeX = 10;
        float iconSizeY = 5.72f;

        // Увеличьте позицию Y, если таб открыт
        if (isTabOpen) {
            posY += 20; // Здесь можно настроить величину смещения
        }

        String text = "Enlisted";
        float textWidth = Fonts.sfui.getWidth(text, fontSize);

        float posX = 400 - textWidth / 2.2f;

        String fpsText = mc.debugFPS + " fps";
        float fpsTextWidth = Fonts.sfui.getWidth(fpsText, fontSize);

        String pingText = PingUtil.calculatePing() + " ms";
        float pingTextWidth = Fonts.sfui.getWidth(pingText, fontSize);

        String username = "ProstoYa";
        float usernameWidth = Fonts.sfui.getWidth(username, fontSize);

        float rectWidth = iconSizeX + padding * 3.5f + extraPadding + textWidth + padding * 1.5f + fpsTextWidth + padding * 1.5f + usernameWidth + padding * 1.5f + pingTextWidth + padding + 9.5f;
        float rectHeight = Math.max(iconSizeY, fontSize) + padding * 2;

        float imageOffsetY = (rectHeight - iconSizeY) / 2;

        float finalPosY = posY;
        drawStyledRect(posX, finalPosY, rectWidth, rectHeight, 5);

        DisplayUtils.drawShadow(posX, posY, rectWidth, rectHeight, 15, ColorUtils.rgba(21,24,40,165));

        DisplayUtils.drawImage(logo, posX + padding, posY + imageOffsetY, iconSizeX, iconSizeY, ColorUtils.rgb(76,0,172));

        float textPosX = posX + iconSizeX + padding * 4 + extraPadding;
        float textPosY = posY + (rectHeight - fontSize) / 2;

        Fonts.sfui.drawCenteredText(ms, text, textPosX, textPosY, ColorUtils.rgb(76,0,172),fontSize);

        float usernamePosX = textPosX + textWidth + padding * 4.5f;
        float usernamePosY = textPosY;

        Fonts.sfui.drawCenteredText(ms, username, usernamePosX - 3, usernamePosY, ColorUtils.rgba(255, 255, 255, 255), fontSize);

        float fpsTextPosX = usernamePosX + usernameWidth + padding * 1.5f;
        float fpsTextPosY = usernamePosY;

        Fonts.sfui.drawCenteredText(ms, fpsText, fpsTextPosX, usernamePosY, ColorUtils.rgba(255, 255, 255, 255), fontSize);

        float pingPosX = fpsTextPosX + fpsTextWidth + padding * 1.5f;
        float pingPosY = fpsTextPosY;

        Fonts.sfui.drawCenteredText(ms, pingText, pingPosX, pingPosY, ColorUtils.rgba(255, 255, 255, 255), fontSize);
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 90), true, false, true, false);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 24, 40, 90), true, false, true, false);
    }
}
