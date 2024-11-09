package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Function;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.display.ElementUpdater;
import im.expensive.ui.styles.Style;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.util.math.vector.Vector4f;
import ru.hogoshi.Animation;

import java.util.List;

public class ArrayListRenderer implements ElementRenderer, ElementUpdater {



    private int lastIndex;

    List<Function> list;


    StopWatch stopWatch = new StopWatch();

    @Override
    public void update(EventUpdate e) {
        if (stopWatch.isReached(1000)) {
            list = Expensive.getInstance().getFunctionRegistry().getSorted(Fonts.sfui, 9 - 1.5f)
                    .stream()
                    .toList();
            stopWatch.reset();
        }
    }

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float rounding = 6;
        float padding = 3.5f;
        float posX = 4;
        float posY = 4 + 28;
        int index = 0;

        if (list == null) return;

        for (Function f : list) {
            float fontSize = 6.5f;
            Animation anim = f.getAnimation();
            float value = (float) anim.getValue();
            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                posY += (fontSize + padding * 2) * value;
                index++;
            }
        }
        index = 0;
        posY = 4 + 28;
        for (Function f : list) {
            float fontSize = 6.5f;
            Animation anim = f.getAnimation();
            anim.update();

            float value = (float) anim.getValue();

            String text = f.getName();
            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            if (value != 0) {
                float localFontSize = fontSize * value;
                float localTextWidth = textWidth * value;

                boolean isFirst = index == 0;
                boolean isLast = index == lastIndex;

                float localRounding = rounding;

                for (Function f2 : list.subList(list.indexOf(f) + 1, list.size())) {
                    if (f2.getAnimation().getValue() != 0) {
                        localRounding = isLast ? rounding : Math.min(textWidth - Fonts.sfui.getWidth(f2.getName(), fontSize), rounding);
                        break;
                    }
                }
                
                Vector4f rectVec = new Vector4f(isFirst ? rounding : 0, isLast ? rounding : 0, isFirst ? rounding : 0, isLast ? rounding : localRounding);

                float finalPosY = posY;
                DisplayUtils.drawRoundedRect(posX - 0.5f, finalPosY - 0.5f, localTextWidth + padding * 2 + 1, localFontSize + padding * 2, 1 + 0.5f, ColorUtils.rgba(25, 26, 40, 165), true, false, true, false);
                DisplayUtils.drawRoundedRect(posX, finalPosY, localTextWidth + padding * 2 + 1, localFontSize + padding * 2, 1, ColorUtils.rgba(25, 26, 40, 165), true, false, true, false);
                DisplayUtils.drawShadow(posX, posY, localTextWidth + padding * 2, localFontSize + padding * 2, 15, ColorUtils.rgba(25, 26, 40, 165));

                Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();

                DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY + 0.6f, 1.5f, localFontSize + padding * 1.3f, ColorUtils.rgb(32,0,99),ColorUtils.rgb(115, 0, 205));
                DisplayUtils.drawShadow(posX + 0.5f, posY + 0.6f, 1.5f, localFontSize + padding * 1.3f, 4, ColorUtils.rgb(21,24,40));

                Fonts.sfui.drawText(ms, f.getName(), posX + padding, posY + padding, ColorUtils.rgb(255,255,255), localFontSize);

                posY += (fontSize + padding * 2) * value;
                index++;

            }
        }

        lastIndex = index - 1;
    }
}
