package dev.luvbeeq.baritone.selection;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.event.events.RenderEvent;
import dev.luvbeeq.baritone.api.event.listener.AbstractGameEventListener;
import dev.luvbeeq.baritone.api.selection.ISelection;
import dev.luvbeeq.baritone.utils.IRenderer;
import net.minecraft.util.math.AxisAlignedBB;
import net.mojang.blaze3d.matrix.MatrixStack;

public class SelectionRenderer implements IRenderer, AbstractGameEventListener {

    public static final double SELECTION_BOX_EXPANSION = .005D;

    private final SelectionManager manager;

    SelectionRenderer(Baritone baritone, SelectionManager manager) {
        this.manager = manager;
        baritone.getGameEventHandler().registerEventListener(this);
    }

    public static void renderSelections(MatrixStack stack, ISelection[] selections) {
        float opacity = settings.selectionOpacity.value;
        boolean ignoreDepth = settings.renderSelectionIgnoreDepth.value;
        float lineWidth = settings.selectionLineWidth.value;

        if (!settings.renderSelection.value || selections.length == 0) {
            return;
        }

        IRenderer.startLines(settings.colorSelection.value, opacity, lineWidth, ignoreDepth);

        for (ISelection selection : selections) {
            IRenderer.emitAABB(stack, selection.aabb(), SELECTION_BOX_EXPANSION);
        }

        if (settings.renderSelectionCorners.value) {
            IRenderer.glColor(settings.colorSelectionPos1.value, opacity);

            for (ISelection selection : selections) {
                IRenderer.emitAABB(stack, new AxisAlignedBB(selection.pos1(), selection.pos1().add(1, 1, 1)));
            }

            IRenderer.glColor(settings.colorSelectionPos2.value, opacity);

            for (ISelection selection : selections) {
                IRenderer.emitAABB(stack, new AxisAlignedBB(selection.pos2(), selection.pos2().add(1, 1, 1)));
            }
        }

        IRenderer.endLines(ignoreDepth);
    }

    @Override
    public void onRenderPass(RenderEvent event) {
        renderSelections(event.getModelViewStack(), manager.getSelections());
    }
}
