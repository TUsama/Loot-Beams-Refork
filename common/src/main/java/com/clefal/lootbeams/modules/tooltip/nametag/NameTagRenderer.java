package com.clefal.lootbeams.modules.tooltip.nametag;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.data.LBItemEntity;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NameTagRenderer {

    public static void renderNameTag(PoseStack stack, MultiBufferSource buffer, LBItemEntity LBItemEntity) {
        ItemEntity item = LBItemEntity.item();
        if (Minecraft.getInstance().player.isCrouching() || ((((Boolean) ConfigurationManager.request(Config.RENDER_NAMETAGS_ONLOOK)) && isLookingAt(Minecraft.getInstance().player, item, ConfigurationManager.request(Config.NAMETAG_LOOK_SENSITIVITY))))) {

            Color color = LBItemEntity.rarity().color();

            //If player is crouching or looking at the item
            if (Minecraft.getInstance().player.isCrouching() || (ConfigurationManager.<Boolean>request(Config.RENDER_NAMETAGS_ONLOOK) && isLookingAt(Minecraft.getInstance().player, item, ConfigurationManager.request(Config.NAMETAG_LOOK_SENSITIVITY)))) {
                float foregroundAlpha = ConfigurationManager.request(Double.class, Config.NAMETAG_TEXT_ALPHA).floatValue();
                float backgroundAlpha = ConfigurationManager.request(Double.class, Config.NAMETAG_BACKGROUND_ALPHA).floatValue();
                double yOffset = ConfigurationManager.request(Double.class, Config.NAMETAG_Y_OFFSET);
                int foregroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * foregroundAlpha)).getRGB();
                int backgroundColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (255 * backgroundAlpha)).getRGB();
                stack.pushPose();

                //Render nametags at heights based on player distance
                stack.translate(0.0D, Math.min(1D, Minecraft.getInstance().player.distanceToSqr(item) * 0.025D) + yOffset, 0.0D);
                stack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());

                float nametagScale = ConfigurationManager.<Double>request(Config.NAMETAG_SCALE).floatValue();
                stack.scale(-0.02F * nametagScale, -0.02F * nametagScale, 0.02F * nametagScale);

                //Render stack counts on nametag
                Font fontrenderer = Minecraft.getInstance().font;
                TooltipsGatherNameAndRarityEvent tooltipsGatherNameAndRarityEvent = new TooltipsGatherNameAndRarityEvent(LBItemEntity);
                Constants.EVENT_BUS.post(tooltipsGatherNameAndRarityEvent);
                List<Component> nameAndRarity = new ArrayList<>(tooltipsGatherNameAndRarityEvent.gather.values());

                System.out.println(nameAndRarity);
                stack.translate(0, 2, -10);

                for (Component c : nameAndRarity) {
                    String s = c.getString();
                    if (s.isBlank()) continue;
                    renderText(fontrenderer, stack, buffer, s, foregroundColor, backgroundColor, backgroundAlpha);
                    stack.translate(0, Minecraft.getInstance().font.lineHeight, 0.0f);

                }


                stack.popPose();


            }
        }

    }

    private static void renderText(Font fontRenderer, PoseStack stack, MultiBufferSource buffer, String text, int foregroundColor, int backgroundColor, float backgroundAlpha) {

        if (ConfigurationManager.<Boolean>request(Config.BORDERS)) {
            float w = -fontRenderer.width(text) / 2f;
            int bg = new Color(0, 0, 0, (int) (255 * backgroundAlpha)).getRGB();
            Component comp = Component.literal(text);
            fontRenderer.drawInBatch8xOutline(comp.getVisualOrderText(), w, 0f, foregroundColor, bg, stack.last().pose(), buffer, LightTexture.FULL_BRIGHT);
        } else {
            fontRenderer.drawInBatch(text, (float) (-fontRenderer.width(text) / 2), 0f, foregroundColor, false, stack.last().pose(), buffer, Font.DisplayMode.NORMAL, backgroundColor, 15728864);
        }
    }


    /**
     * Checks if the player is looking at the given entity, accuracy determines how close the player has to look.
     */
    public static boolean isLookingAt(LocalPlayer player, Entity target, double accuracy) {
        Vec3 difference = new Vec3(target.getX() - player.getX(), target.getEyeY() - player.getEyeY(), target.getZ() - player.getZ());
        double length = difference.length();
//        double dot = player.getViewVector(1.0F).normalize().dot(difference.normalize());
        double dot = Minecraft.getInstance().getCameraEntity().getLookAngle().normalize().dot(difference.normalize());
        return dot > 1.0D - accuracy / length && !target.isInvisible();
    }
}
