package im.expensive.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.animations.impl.EaseInOutQuad;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;

import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
    private final CopyOnWriteArrayList<Notification> notifications = new CopyOnWriteArrayList();
    private MathUtil AnimationMath;
    private ImageType imageType;
    boolean state;

    public void add(String text, String content, int time, ImageType imageType) {
        this.notifications.add(new Notification(text, content, time, imageType));
    }

    public void draw(MatrixStack stack) {
        int yOffset = 4;
        for (Notification notification : this.notifications) {
            if (System.currentTimeMillis() - notification.getTime() <= (long)notification.time2 * 1000L - 300L) {
                notification.yAnimation.setDirection(Direction.FORWARDS);
            }
            notification.alpha = (float)notification.animation.getOutput();
            if (System.currentTimeMillis() - notification.getTime() > (long)notification.time2 * 1000L) {
                notification.yAnimation.setDirection(Direction.BACKWARDS);
            }
            if (notification.yAnimation.finished(Direction.BACKWARDS)) {
                this.notifications.remove(notification);
                continue;
            }
            float x = (float)IMinecraft.mc.getMainWindow().scaledWidth() - (Fonts.sfMedium.getWidth(notification.getText(), 7.0f) + 8.0f) - 10.0f;
            float y = IMinecraft.mc.getMainWindow().scaledHeight() - 140;
            notification.yAnimation.setEndPoint(yOffset);
            notification.yAnimation.setDuration(100);
            notification.setX(x);
            notification.setY(MathUtil.fast(notification.getY(), y -= (float)((double)notification.draw(stack) * notification.yAnimation.getOutput() + 3.0), 15.0f));
            ++yOffset;
        }
    }

    private class Notification {
        private float x = 0.0f;
        private float y = IMinecraft.mc.getMainWindow().scaledHeight() + 21;
        private String text;
        private String content;;
        private long time = System.currentTimeMillis();
        public Animation animation = new EaseInOutQuad(500, 1.0, Direction.FORWARDS);
        public Animation yAnimation = new EaseBackIn(500, 1.0, 1.0f);
        private ImageType imageType;
        float alpha;
        int time2 = 3;
        private boolean isState;
        private boolean state;

        public Notification(String text, String content, int time, ImageType imageType) {
            this.text = text;
            this.content = content;
            this.time2 = time;
            this.imageType = imageType;
        }


        public float draw(MatrixStack stack) {
                float width = Fonts.sfMedium.getWidth(this.text , 7.0f) + 8.0f;
                float width2 = 80.0f;
                DisplayUtils.drawRoundedRect(this.x - 447.0f + 50.0f + 0 , this.y - 25.0f , 27.0f + width , 13.0f , (int) 4.0f, ColorUtils.rgba(0 , 0 , 0 , 170));
                DisplayUtils.drawRoundedRect(this.x - 447.0f + 50.0f + 0 , this.y - 25.0f , 27.0f + width , 13.0f , (int) 4.0f, ColorUtils.rgba(0 , 0 , 0 , 170));
                DisplayUtils.drawRoundedRect(this.x - 433.0f + 50.0f + 0 , this.y - 23.0f , width2 + -79.0f , 8.0f , (int) 0.0f, ColorUtils.rgba(70 , 70 , 70 , 170));
                if (this.imageType == ImageType.FIRST_PHOTO) {
                    Fonts.nuralphaicons.drawText(stack , "K" , this.x - 444.0f + 50.0f + 0 , this.y - 22.5f , ColorUtils.rgba(255 , 255 , 255 , 255) , 8);
                } else {
                    Fonts.nuralphaicons.drawText(stack , "J" , this.x - 444.0f + 50.0f + 0, this.y - 22.5f , ColorUtils.rgba(255 , 255 , 255 , 255) , 8);
                }
                Fonts.sfMedium.drawText(stack , this.text , this.x - 427.0f + 50.0f + 0 , this.y - 22.0f , -1 , 7.0f , 0.05f);
            return 16.0f;
        }


        public float getX() {
            return this.x;
        }

        public float getY() {
            return this.y;
        }

        public void setX(float x) {
            this.x = x + -200;
        }

        public void setY(float y) {
            this.y = y + -0.4f;
        }

        public String getText() {
            return this.text;
        }

        public String getContent() {
            return this.content;
        }

        public long getTime() {
            return this.time;
        }
    }

    public static enum ImageType {
        FIRST_PHOTO,
        SECOND_PHOTO;
    }
}