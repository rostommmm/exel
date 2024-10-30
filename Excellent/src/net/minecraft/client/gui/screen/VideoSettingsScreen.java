package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.GPUWarning;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.util.text.*;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.optifine.Config;
import net.optifine.Lang;
import net.optifine.gui.*;
import net.optifine.shaders.gui.GuiShaders;
import net.optifine.util.GuiUtils;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class VideoSettingsScreen extends GuiScreenOF {
    private final Screen parentGuiScreen;
    private final GameSettings guiGameSettings;
    private static final AbstractOption[] videoOptions = new AbstractOption[]{AbstractOption.GRAPHICS, AbstractOption.RENDER_DISTANCE, AbstractOption.AO, AbstractOption.FRAMERATE_LIMIT, AbstractOption.AO_LEVEL, AbstractOption.VIEW_BOBBING, AbstractOption.GUI_SCALE, AbstractOption.ENTITY_SHADOWS, AbstractOption.GAMMA, AbstractOption.ATTACK_INDICATOR, AbstractOption.DYNAMIC_LIGHTS, AbstractOption.DYNAMIC_FOV};
    private final GPUWarning field_241604_x_;
    private static final ITextComponent field_241598_c_ = (new TranslationTextComponent("options.graphics.fabulous")).mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_241599_p_ = new TranslationTextComponent("options.graphics.warning.message", field_241598_c_, field_241598_c_);
    private static final ITextComponent field_241600_q_ = (new TranslationTextComponent("options.graphics.warning.title")).mergeStyle(TextFormatting.RED);
    private static final ITextComponent field_241601_r_ = new TranslationTextComponent("options.graphics.warning.accept");
    private static final ITextComponent field_241602_s_ = new TranslationTextComponent("options.graphics.warning.cancel");
    private static final ITextComponent field_241603_t_ = new StringTextComponent("\n");
    private final TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
    private final List<Widget> buttonList = this.buttons;
    private Widget buttonGuiScale;

    public VideoSettingsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
        super(new TranslationTextComponent("options.videoTitle"));
        this.parentGuiScreen = parentScreenIn;
        this.guiGameSettings = gameSettingsIn;
        this.field_241604_x_ = this.parentGuiScreen.minecraft.getGPUWarning();
        this.field_241604_x_.func_241702_i_();

        if (this.guiGameSettings.graphicFanciness == GraphicsFanciness.FABULOUS) {
            this.field_241604_x_.func_241698_e_();
        }
    }

    public void init() {
        this.buttonList.clear();

        for (int i = 0; i < videoOptions.length; ++i) {
            AbstractOption abstractoption = videoOptions[i];

            if (abstractoption != null) {
                int j = this.width / 2 - 155 + i % 2 * 160;
                int k = this.height / 6 + 21 * (i / 2) - 12;
                Widget widget = this.addButton(abstractoption.createWidget(this.minecraft.gameSettings, j, k, 150));

                if (abstractoption == AbstractOption.GUI_SCALE) {
                    this.buttonGuiScale = widget;
                }
            }
        }

        int l = this.height / 6 + 21 * (videoOptions.length / 2) - 12;
        int i1 = 0;
        i1 = this.width / 2 - 155;
        this.addButton(new GuiScreenButtonOF(231, i1, l, Lang.get("of.options.shaders")));
        i1 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(202, i1, l, Lang.get("of.options.quality")));
        l = l + 21;
        i1 = this.width / 2 - 155;
        this.addButton(new GuiScreenButtonOF(201, i1, l, Lang.get("of.options.details")));
        i1 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(212, i1, l, Lang.get("of.options.performance")));
        l = l + 21;
        i1 = this.width / 2 - 155;
        this.addButton(new GuiScreenButtonOF(211, i1, l, Lang.get("of.options.animations")));
        i1 = this.width / 2 - 155 + 160;
        this.addButton(new GuiScreenButtonOF(222, i1, l, Lang.get("of.options.other")));
        l = l + 21;
        this.addButton(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done")));
    }

    protected void actionPerformed(Widget p_actionPerformed_1_) {
        if (p_actionPerformed_1_ == this.buttonGuiScale) {
            this.updateGuiScale();
        }

        this.checkFabulousWarning();

        if (p_actionPerformed_1_ instanceof GuiButtonOF guibuttonof) {
            this.actionPerformed(guibuttonof, 1);
        }
    }

    private void checkFabulousWarning() {
        if (this.field_241604_x_.func_241700_g_()) {
            List<ITextProperties> list = Lists.newArrayList(field_241599_p_, field_241603_t_);
            String s = this.field_241604_x_.func_241703_j_();

            if (s != null) {
                list.add(field_241603_t_);
                list.add((new TranslationTextComponent("options.graphics.warning.renderer", s)).mergeStyle(TextFormatting.GRAY));
            }

            String s1 = this.field_241604_x_.func_241705_l_();

            if (s1 != null) {
                list.add(field_241603_t_);
                list.add((new TranslationTextComponent("options.graphics.warning.vendor", s1)).mergeStyle(TextFormatting.GRAY));
            }

            String s2 = this.field_241604_x_.func_241704_k_();

            if (s2 != null) {
                list.add(field_241603_t_);
                list.add((new TranslationTextComponent("options.graphics.warning.version", s2)).mergeStyle(TextFormatting.GRAY));
            }

            this.minecraft.displayScreen(new GPUWarningScreen(field_241600_q_, list, ImmutableList.of(new GPUWarningScreen.Option(field_241601_r_, (p_lambda$checkFabulousWarning$0_1_) ->
            {
                this.guiGameSettings.graphicFanciness = GraphicsFanciness.FABULOUS;
                Minecraft.getInstance().worldRenderer.loadRenderers();
                this.field_241604_x_.func_241698_e_();
                this.minecraft.displayScreen(this);
            }), new GPUWarningScreen.Option(field_241602_s_, (p_lambda$checkFabulousWarning$1_1_) ->
            {
                this.field_241604_x_.func_241699_f_();
                this.minecraft.displayScreen(this);
            }))));
        }
    }

    protected void actionPerformedRightClick(Widget p_actionPerformedRightClick_1_) {
        if (p_actionPerformedRightClick_1_ == this.buttonGuiScale) {
            AbstractOption.GUI_SCALE.setValueIndex(this.guiGameSettings, -1);
            this.updateGuiScale();
        }
    }

    private void updateGuiScale() {
        this.minecraft.updateWindowSize();
        MainWindow mainwindow = this.minecraft.getMainWindow();
        int i = GuiUtils.getWidth(this.buttonGuiScale);
        int j = GuiUtils.getHeight(this.buttonGuiScale);
        int k = this.buttonGuiScale.x + (i - j);
        int l = this.buttonGuiScale.y + j / 2;
        GLFW.glfwSetCursorPos(mainwindow.getHandle(), (double) k * mainwindow.getScaleFactor(), (double) l * mainwindow.getScaleFactor());
    }

    private void actionPerformed(GuiButtonOF p_actionPerformed_1_, int p_actionPerformed_2_) {
        if (p_actionPerformed_1_.active) {
            if (p_actionPerformed_1_.id == 200) {
                this.minecraft.gameSettings.saveOptions();
                this.minecraft.displayScreen(this.parentGuiScreen);
            }

            if (p_actionPerformed_1_.id == 201) {
                this.minecraft.gameSettings.saveOptions();
                GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayScreen(guidetailsettingsof);
            }

            if (p_actionPerformed_1_.id == 202) {
                this.minecraft.gameSettings.saveOptions();
                GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF(this, this.guiGameSettings);
                this.minecraft.displayScreen(guiqualitysettingsof);
            }

            if (p_actionPerformed_1_.id == 211) {
                this.minecraft.gameSettings.saveOptions();
                GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayScreen(guianimationsettingsof);
            }

            if (p_actionPerformed_1_.id == 212) {
                this.minecraft.gameSettings.saveOptions();
                GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayScreen(guiperformancesettingsof);
            }

            if (p_actionPerformed_1_.id == 222) {
                this.minecraft.gameSettings.saveOptions();
                GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF(this, this.guiGameSettings);
                this.minecraft.displayScreen(guiothersettingsof);
            }

            if (p_actionPerformed_1_.id == 231) {
                if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
                    return;
                }

                if (Config.isGraphicsFabulous()) {
                    Config.showGuiMessage(Lang.get("of.message.shaders.gf1"), Lang.get("of.message.shaders.gf2"));
                    return;
                }

                this.minecraft.gameSettings.saveOptions();
                GuiShaders guishaders = new GuiShaders(this, this.guiGameSettings);
                this.minecraft.displayScreen(guishaders);
            }
        }
    }

    public void onClose() {
        this.minecraft.gameSettings.saveOptions();
        super.onClose();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.minecraft.fontRenderer, this.title, this.width / 2, 15, 16777215);
        String s = Config.getVersion();
        String s1 = "HD_U";

        if (s1.equals("HD")) {
            s = "OptiFine HD G8";
        }

        if (s1.equals("HD_U")) {
            s = "OptiFine HD G8 Ultra";
        }

        if (s1.equals("L")) {
            s = "OptiFine G8 Light";
        }

        drawString(matrixStack, this.minecraft.fontRenderer, s, 2, this.height - 10, 8421504);
        String s2 = "Minecraft 1.16.5";
        int i = this.minecraft.fontRenderer.getStringWidth(s2);
        drawString(matrixStack, this.minecraft.fontRenderer, s2, this.width - i - 2, this.height - 10, 8421504);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(matrixStack, mouseX, mouseY, this.buttonList);
    }

    public static String getGuiChatText(ChatScreen p_getGuiChatText_0_) {
        return p_getGuiChatText_0_.inputField.getText();
    }
}
