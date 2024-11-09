package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraftforge.eventbus.api.Event;

@FunctionRegister(name = "Hotbar", type = Category.Render)
public class HotBar extends Function {

    public static HotBar hotbar;
    public boolean state;

    @Subscribe
    public void onRender(Event event) {

    }
}