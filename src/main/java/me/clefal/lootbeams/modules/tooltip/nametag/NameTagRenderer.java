package me.clefal.lootbeams.modules.tooltip.nametag;

import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.LootInfomationConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
        LootInfomationConfig.nameTagSection nameTagSection = LootInfomationConfig.lootInfomationConfig.nameTag;

        //If player is crouching or looking at the item
        if (Minecraft.getInstance().player.isCrouching() || ((nameTagSection.render_name_tag_on_look && isLookingAt(Minecraft.getInstance().player, item, nameTagSection.name_tag_look_sensitivity.get())))) {
            LBColor color = LBItemEntity.rarity().color();
            float foregroundAlpha = nameTagSection.name_tag_text_alpha.get();
            float backgroundAlpha = nameTagSection.name_tag_background_alpha.get();
            double yOffset = nameTagSection.name_tag_y_offset.get();
            int foregroundColor = color.changeA(((int) (foregroundAlpha * 255))).argb();
            int backgroundColor = color.changeA(((int) (foregroundAlpha * 255))).argb();
            stack.pushPose();
            //Render nametags at heights based on player distance
            stack.translate(0.0D, Math.min(1D, Minecraft.getInstance().player.distanceToSqr(item) * 0.025D) + yOffset, 0.0D);
            stack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            stack.mulPose(Axis.YP.rotationDegrees(180));

            float nametagScale = nameTagSection.name_tag_scale.get();
            stack.scale(-0.02F * nametagScale, -0.02F * nametagScale, 0.02F * nametagScale);

            //Render stack counts on nametag
            Font fontrenderer = Minecraft.getInstance().font;
            TooltipsGatherNameAndRarityEvent tooltipsGatherNameAndRarityEvent = new TooltipsGatherNameAndRarityEvent(LBItemEntity);
            LootBeamsConstants.EVENT_BUS.post(tooltipsGatherNameAndRarityEvent);
            List<Component> nameAndRarity = new ArrayList<>(tooltipsGatherNameAndRarityEvent.gather.values());

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

    private static void renderText(Font fontRenderer, PoseStack stack, MultiBufferSource buffer, String text, int foregroundColor, int backgroundColor, float backgroundAlpha) {

        if (LootInfomationConfig.lootInfomationConfig.nameTag.add_text_border) {
            float w = -fontRenderer.width(text) / 2f;
            int bg = new Color(0, 0, 0, (int) (255 * backgroundAlpha)).getRGB();
            Component comp = Component.literal(text);
            fontRenderer.drawInBatch8xOutline(comp.getVisualOrderText(), w, 0f, foregroundColor, bg, stack.last().pose(), buffer, LightTexture.FULL_BRIGHT);
        } else {
            fontRenderer.drawInBatch(text, (float) (-fontRenderer.width(text) / 2), 30f, foregroundColor, false, stack.last().pose(), buffer, Font.DisplayMode.NORMAL, backgroundColor, 15728864);
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
