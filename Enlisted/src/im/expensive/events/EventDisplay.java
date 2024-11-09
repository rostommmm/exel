package im.expensive.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDisplay {
    MatrixStack matrixStack;
    float partialTicks;
    Type type;

    public EventDisplay(MatrixStack matrixStack, float partialTicks) {
        this.matrixStack = matrixStack;
        this.partialTicks = partialTicks;
    }

    public void fill(float rectX, float rectY, float v, float v1, int rgb) {

    }

    public enum Type {
        PRE, POST, HIGH
    }
}
