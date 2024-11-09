package im.expensive.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AltWidget implements IMinecraft {
    public final List<Alt> alts = new ArrayList<>();

    private float x;
    private final float y;

    public AltWidget() {

        y = 10;

    }


    public boolean open;

    private String altName = "";
    private boolean typing;
    private float scrollPre;
    private float scroll;

    public void updateScroll(int mouseX, int mouseY, float delta) {

        if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 145, 100) && open) {
            scrollPre += delta * 10;
        }
    }
    
    private float openProgress; // добавьте эту переменную для отслеживания прогресса открытия

    private boolean opening; // добавьте эту переменную для отслеживания состояния открытия


private final ResourceLocation head = new ResourceLocation("expensive/images/head.png");

    public void render(MatrixStack stack, int x, int y) {
        scroll = MathUtil.fast(scroll, scrollPre, 10);

        this.x = mc.getMainWindow().getScaledWidth() - 955;
        float width = 90;

        float height = Math.min(20 + (open ? 10 + (alts.size() + 1) * (17) : 0), 100);

        DisplayUtils.drawRoundedRect(this.x, this.y, width, height, new Vector4f(6, 6, 6, 6), ColorUtils.rgba(0, 0, 0, 255));
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, width - 16, height);
        DisplayUtils.drawImage(head, this.x + 4, this.y + 4, 9, 9, ColorUtils.rgb(255, 255, 255));
        Fonts.montserrat.drawText(stack, mc.session.getUsername(), this.x + 15, this.y + 6, ColorUtils.rgb(255,255,255), 7);
        Scissor.unset();
        Scissor.pop();
        if (open) {
            DisplayUtils.drawRectHorizontalW(this.x, this.y + 20, width, 5f, ColorUtils.rgba(0, 0, 0, 0), ColorUtils.rgba(0, 0, 0, 0));
            DisplayUtils.drawRectW(this.x, this.y + 20, width, 0.5f, ColorUtils.rgba(0, 0, 0, 150));
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y + 20, width, 100f - 20);
            float i = 0;
            for (Alt alt : alts) {
                DisplayUtils.drawRoundedRect(this.x + 5, this.y + 26 + i * 17 + scroll, width - 10, 15, 0, mc.session.getUsername().equals(alt.name) ? ColorUtils.rgba(0, 0, 0, 100) : ColorUtils.rgba(0, 0, 0, 128), true, false, true, false);
                DisplayUtils.drawImage(head, this.x + 8, this.y + 24 + i * 17 + 4 + scroll, 9, 9, ColorUtils.rgb(255, 255, 255));
                Fonts.montserrat.drawText(stack, alt.name, this.x + 20, this.y + 26 + i * 17 + 4 + scroll, ColorUtils.rgb(255,255,255), 6);
                i++;
            }
            if (!alts.isEmpty() && 20 + (open ? 10 + (alts.size() + 1) * (17) : 0) > 100)
                scrollPre = MathHelper.clamp(scrollPre, -i * 17 + 50, 0);
            else {
                scrollPre = 0;
            }
            String textToDraw = altName;

            if (!typing && altName.isEmpty()) {
                textToDraw = "Ваш никнейм";
            }

            DisplayUtils.drawRoundedRect(this.x + 5, this.y + 26 + i * 17 + scroll, width - 10,
                    15, 0, ColorUtils.rgba(0, 0, 0, 100), true, false, true, false);
            Fonts.montserrat.drawText(stack, textToDraw + (typing ? (System.currentTimeMillis() % 1000 > 500 ? "_" : "") : ""),
                    this.x + 10, this.y + 26 + i * 17 + 4 + scroll, ColorUtils.rgba(255, 255, 255, 255), 6);
            DisplayUtils.drawRoundedRect(this.x + 5 + 2, this.y + 26 + i * 17 + 2 + scroll,
                    Fonts.montserrat.getWidth(textToDraw + (typing ? (System.currentTimeMillis() % 1000 > 500 ? "_" : "") : ""),
                            6) + 7, 15 - 4, 2, ColorUtils.rgba(0, 0, 0, 100), true, false, true, false);
            Fonts.montserrat.drawText(stack, "+", this.x + width - 18,
                    this.y + 26 + i * 17 + 2 + scroll, ColorUtils.rgba(255,255,255, 255), 10);
            Scissor.unset();
            Scissor.pop();
        }
    }

    public void onChar(char typed) {
        if (typing) {
            if (Fonts.montserrat.getWidth(altName, 6f) < 145 - 50) {
                altName += typed;
            }
        }
    }

    public void onKey(int key) {
        boolean ctrlDown = GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
        if (typing) {
            if (ctrlDown && key == GLFW.GLFW_KEY_V) {
                try {
                    altName += GLFW.glfwGetClipboardString(mc.getMainWindow().getHandle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (key == GLFW.GLFW_KEY_BACKSPACE) {
                if (!altName.isEmpty()) {
                    altName = altName.substring(0, altName.length() - 1);
                }
            }
            if (key == GLFW.GLFW_KEY_ENTER) {
                if (altName.length() >= 3) {
                    alts.add(new Alt(altName));
                    AltConfig.updateFile();
                }
                typing = false;
            }
        }
    }

    public void click(int mouseX, int mouseY, int button) {
        float width = 145;

        if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, width, 20)) {
            open = !open;

            if (!open) {
                typing = false;
            }
        }
        if (!MathUtil.isHovered(mouseX, mouseY, this.x, this.y, width, 50)) {
            typing = false;
        }
        List<Alt> toRemove = new ArrayList<>();
        if (open) {
            float i = 0;
            for (Alt alt : alts) {

                if (MathUtil.isHovered(mouseX, mouseY, this.x + 5, this.y + 26 + i * 17 + scroll, width - 10, 15)) {
                    if (button == 0) {
                        AltConfig.updateFile();
                        mc.session = new Session(alt.name, UUID.randomUUID().toString(), "", "mojang");
                    } else {
                        toRemove.add(alt);
                        AltConfig.updateFile();
                    }
                }
                i++;
            }
            alts.removeAll(toRemove);
        }

        if (MathUtil.isHovered(mouseX, mouseY, this.x + 82, this.y + 26 + alts.size() * 17 + 2 + scroll, 10, 10)) {
            if (altName.length() >= 3) {
                alts.add(new Alt(altName));
                AltConfig.updateFile();
            }
            typing = false;
        }
        if (MathUtil.isHovered(mouseX, mouseY, this.x + 5, this.y + 26 + alts.size() * 17 + scroll, width - 10, 15)) {
            typing = !typing;
        }
    }

}
