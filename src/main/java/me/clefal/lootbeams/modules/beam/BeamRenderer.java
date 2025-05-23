package me.clefal.lootbeams.modules.beam;

import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.modules.dynamicprovider.DynamicProvider;
import me.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.clefal.nirvana_lib.relocated.io.vavr.control.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.item.ItemEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.*;

public class BeamRenderer {



    public static void renderLootBeam(PoseStack stack, MultiBufferSource buffer, float partialTick, LBItemEntity LBItemEntity) {
        ItemEntity itemEntity = LBItemEntity.item();
        LBColor color = LBItemEntity.rarity().color();
        int lifeTime = LBItemEntity.fadeIn();

        LightConfig.Beam beamConfig = LightConfig.lightConfig.beam;
        LightConfig.Glow glowConfig = LightConfig.lightConfig.glow;

        int fadeInTime = beamConfig.beam_fade_in_time.get();
        var fadeInFactor = 1.0f * lifeTime / fadeInTime;
        int argb = color.argb();
        int R = FastColor.ARGB32.red(argb);
        int G = FastColor.ARGB32.green(argb);
        int B = FastColor.ARGB32.blue(argb);

        float preBeamAlpha = beamConfig.beam_alpha.get();

        LocalPlayer player = Minecraft.getInstance().player;
        double distance = player.distanceTo(itemEntity);
        float fadeDistance = beamConfig.beam_fade_in_distance.get();
        //Clefal: we don't actually need that much beamAlpha gimmick.
        //We should never cancel the beam, just make it hard to see.
        if (distance > fadeDistance) {
            float m = (float) distance - fadeDistance;

            preBeamAlpha *= 1 / Math.max(m / fadeDistance, 1.0f);
        }


        float beamRadius = 0.05f * beamConfig.beam_radius.get();
        float beamHeight = beamConfig.beam_height.get();
        float yOffset = beamConfig.beam_y_offset.get();
        if (beamConfig.common_shorter_beam) {
            if (LBItemEntity.isCommon()) {
                beamHeight *= 0.65f;
                yOffset -= yOffset;
            }
        }


        int beamAlpha = ((int) (preBeamAlpha * 255));
        Option<DynamicProvider> dynamicProvider1 = DynamicProviderModule.getDynamicProvider();
        if (dynamicProvider1.isDefined()) {
            beamAlpha *= Math.min(dynamicProvider1.get().getBeamLightFactor(), 1);
            beamHeight += dynamicProvider1.get().getBeamLightFactor() - 0.3f;
            beamRadius += 0.005f * dynamicProvider1.get().getGlowFactor();
        }

        beamAlpha *= fadeInFactor;
        Vector3f playerPos = player.getPosition(partialTick).toVector3f();
        Vector3f targetPos = itemEntity.getPosition(partialTick).toVector3f();
        Vector3f direction = targetPos.sub(playerPos).normalize();
        double v = Math.atan2(direction.x(), direction.z());

        stack.pushPose();
        stack.mulPose(Axis.YP.rotation((float) v));
        //Render main beam
        {
            stack.pushPose();
            stack.translate(0, yOffset + 1, 0);
            VertexConsumer buffer1 = buffer.getBuffer(BeamRenderType.LOOT_BEAM_RENDERTYPE);

            //beam
            {
                buffer1.addVertex(stack.last().pose(), -beamRadius, -beamHeight, 0.01f).setColor(R, G, B, beamAlpha).setUv(0, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), -beamRadius, beamHeight, 0.01f).setColor(R, G, B, 0).setUv(0, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), beamRadius, beamHeight, 0.01f).setColor(R, G, B, 0).setUv(1, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), beamRadius, -beamHeight, 0.01f).setColor(R, G, B, beamAlpha).setUv(1, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);
            }
            //shadow
            {
                float glowRadius = beamRadius * 1.35f;
                int glowAlpha = ((int) (beamAlpha * 0.55f));
                buffer1.addVertex(stack.last().pose(), -glowRadius, -beamHeight, 0.001f).setColor(R, G, B, glowAlpha).setUv(0, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), -glowRadius, beamHeight, 0.001f).setColor(R, G, B, 0).setUv(0, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), glowRadius, beamHeight, 0.001f).setColor(R, G, B, 0).setUv(1, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), glowRadius, -beamHeight, 0.001f).setColor(R, G, B, glowAlpha).setUv(1, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);
            }


            stack.popPose();
        }

        stack.popPose();

        {

            if (glowConfig.enable_glow && itemEntity.onGround()) {

                stack.pushPose();
                stack.translate(0, 0.01, 0);
                float radius = glowConfig.glow_effect_radius.get();
                renderGlow(stack, buffer.getBuffer(BeamRenderType.GLOW), R, G, B, ((int) (beamAlpha * 0.4f)), radius);
                stack.popPose();
            }

        }
        if (lifeTime < fadeInTime) {
            LBItemEntity.updateFade();
        }

    }


    private static void renderGlow(PoseStack stack, VertexConsumer builder, int red, int green, int blue, int alpha, float radius) {
        PoseStack.Pose matrixentry = stack.last();
        Matrix4f matrixpose = matrixentry.pose();
        // draw a quad on the xz plane facing up with a radius of 0.5
        builder.addVertex(matrixpose, -radius, (float) 0, -radius).setColor(red, green, blue, alpha).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, -radius, (float) 0, radius).setColor(red, green, blue, alpha).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, radius, (float) 0, radius).setColor(red, green, blue, alpha).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, radius, (float) 0, -radius).setColor(red, green, blue, alpha).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
    }


}
