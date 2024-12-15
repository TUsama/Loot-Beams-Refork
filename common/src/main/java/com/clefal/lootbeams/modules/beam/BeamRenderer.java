package com.clefal.lootbeams.modules.beam;

import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.configs.LightConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.modules.dynamicprovider.DynamicProvider;
import com.clefal.lootbeams.modules.dynamicprovider.DynamicProviderModule;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.vavr.control.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.item.ItemEntity;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class BeamRenderer {



    public static void renderLootBeam(PoseStack stack, MultiBufferSource buffer, float partialTick, LBItemEntity LBItemEntity) {
        ItemEntity itemEntity = LBItemEntity.item();
        Color color = LBItemEntity.rarity().color();
        int lifeTime = LBItemEntity.fadeIn();

        LightConfig.Beam beamConfig = LightConfig.lightConfig.beamSection;
        LightConfig.Glow glowConfig = LightConfig.lightConfig.glowSection;

        int fadeInTime = beamConfig.beam_fade_in_time.get();
        var fadeInFactor = 1.0f * lifeTime / fadeInTime;

        float R = color.getRed() / 255f;
        float G = color.getGreen() / 255f;
        float B = color.getBlue() / 255f;

        float preBeamAlpha = beamConfig.beam_alpha.get();

        double distance = Minecraft.getInstance().player.distanceTo(itemEntity);
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


        var beamAlpha = preBeamAlpha;
        Option<DynamicProvider> dynamicProvider1 = DynamicProviderModule.getDynamicProvider();
        if (dynamicProvider1.isDefined()) {
            beamAlpha *= Math.min(dynamicProvider1.get().getBeamLightFactor(), 1.0f);
            beamHeight += dynamicProvider1.get().getBeamLightFactor() - 0.3f;
            beamRadius += 0.005f * dynamicProvider1.get().getGlowFactor();
        }

        beamAlpha *= fadeInFactor;

        stack.pushPose();
        stack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        //Render main beam
        {
            stack.pushPose();
            stack.translate(0, yOffset + 1, 0);
            VertexConsumer buffer1 = buffer.getBuffer(BeamRenderType.LOOT_BEAM_RENDERTYPE);

            //beam
            {
                buffer1.vertex(stack.last().pose(), -beamRadius, -beamHeight, 0.01f).color(R, G, B, beamAlpha).uv(0, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), -beamRadius, beamHeight, 0.01f).color(R, G, B, 0).uv(0, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), beamRadius, beamHeight, 0.01f).color(R, G, B, 0).uv(1, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), beamRadius, -beamHeight, 0.01f).color(R, G, B, beamAlpha).uv(1, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
            }
            //shadow
            {
                float glowRadius = beamRadius * 1.35f;
                float glowAlpha = beamAlpha * 0.55f;
                buffer1.vertex(stack.last().pose(), -glowRadius, -beamHeight, 0.001f).color(R, G, B, glowAlpha).uv(0, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), -glowRadius, beamHeight, 0.001f).color(R, G, B, 0).uv(0, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), glowRadius, beamHeight, 0.001f).color(R, G, B, 0).uv(1, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), glowRadius, -beamHeight, 0.001f).color(R, G, B, glowAlpha).uv(1, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
            }


            stack.popPose();
        }

        stack.popPose();

        {

            if (glowConfig.enable_glow && itemEntity.onGround()) {

                stack.pushPose();
                stack.translate(0, 0.01, 0);
                float radius = glowConfig.glow_effect_radius.get();
                renderGlow(stack, buffer.getBuffer(BeamRenderType.GLOW), R, G, B, beamAlpha * 0.4f, radius);
                stack.popPose();
            }

            /*
            if (Configuration.PARTICLES.get()) {
                if (!Configuration.PARTICLE_RARE_ONLY.get()) {
                    renderParticles(pticks, itemEntity, (int) entityTime, R, G, B);
                } else {
                    boolean shouldRender1 = false;
                    shouldRender1 = compatRarityCheck(itemEntity, shouldRender1);
                    if (shouldRender1) {
                        renderParticles(pticks, itemEntity, (int) entityTime, R, G, B);
                    }
                }

            }*/
        }
        if (lifeTime < fadeInTime) {
            LBItemEntity.updateFade();
        }

        /*

        if (Configuration.RENDER_NAMETAGS.get()) {
            //TooltipRenderer.renderNameTag(stack, buffer, item, color);
        }

*/

    }


    private static void renderGlow(PoseStack stack, VertexConsumer builder, float red, float green, float blue, float alpha, float radius) {
        PoseStack.Pose matrixentry = stack.last();
        Matrix4f matrixpose = matrixentry.pose();
        Matrix3f matrixnormal = matrixentry.normal();
        // draw a quad on the xz plane facing up with a radius of 0.5
        builder.vertex(matrixpose, -radius, (float) 0, -radius).color(red, green, blue, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, -radius, (float) 0, radius).color(red, green, blue, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, radius, (float) 0, radius).color(red, green, blue, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, radius, (float) 0, -radius).color(red, green, blue, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
    }


}
