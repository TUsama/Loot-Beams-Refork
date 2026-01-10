package me.clefal.lootbeams.modules.beam;

import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBColor;
import me.clefal.lootbeams.data.new_render.LootBeamRenderState;
import me.clefal.lootbeams.duck.PoseCopy;
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
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BeamRenderer {

    public static void renderLootBeam(PoseStack stack, MultiBufferSource buffer, float partialTick, LBItemEntity LBItemEntity){
        PoseCopy last = (PoseCopy) ((Object) stack.last());
        renderLootBeam(buffer, LootBeamRenderState.BeamRenderState.fromLBEntity(LBItemEntity, last.copy(), partialTick));
    }

    public static void renderLootBeam(MultiBufferSource buffer, LootBeamRenderState.BeamRenderState renderState) {
        LBColor color = renderState.rarity.color();
        int lifeTime = renderState.fadeIn;

        PoseStack.Pose pose = renderState.poseStack;
        PoseStack stack = new PoseStack();
        stack.last().pose().set(pose.pose());
        stack.last().normal().set(pose.normal());



        LightConfig.Beam beamConfig = LightConfig.lightConfig.beam;
        LightConfig.Glow glowConfig = LightConfig.lightConfig.glow;
        int fadeInTime = beamConfig.beam_fade_in_time.get();

        var fadeInFactor = 1.0f * lifeTime / fadeInTime;
        int R = color.red();
        int G = color.green();
        int B = color.blue();

        float preBeamAlpha = beamConfig.beam_alpha.get();

        LocalPlayer player = Minecraft.getInstance().player;
        double distance = Mth.sqrt((float) player.distanceToSqr(renderState.location));
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
            if (renderState.rarity.absoluteOrdinal() <= 0) {
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
        beamHeight *= fadeInFactor;
        Vector3f playerPos = player.getPosition(renderState.partialTick).toVector3f();
        Vector3f targetPos = renderState.location.toVector3f();
        Vector3f sub = targetPos.sub(playerPos);
        Vector3f direction = sub.normalize();
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
                //? if !=1.20.1 {
                buffer1.addVertex(stack.last().pose(), -beamRadius, -beamHeight, 0.01f).setColor(R, G, B, beamAlpha).setUv(0, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), -beamRadius, beamHeight, 0.01f).setColor(R, G, B, 0).setUv(0, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), beamRadius, beamHeight, 0.01f).setColor(R, G, B, 0).setUv(1, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), beamRadius, -beamHeight, 0.01f).setColor(R, G, B, beamAlpha).setUv(1, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);
                //?} else {

                /*buffer1.vertex(stack.last().pose(), -beamRadius, -beamHeight, 0.01f).color(R, G, B, beamAlpha).uv(0, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), -beamRadius, beamHeight, 0.01f).color(R, G, B, 0).uv(0, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), beamRadius, beamHeight, 0.01f).color(R, G, B, 0).uv(1, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), beamRadius, -beamHeight, 0.01f).color(R, G, B, beamAlpha).uv(1, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                *///?}
            }
            //shadow
            {
                float glowRadius = beamRadius * 1.35f;
                int glowAlpha = ((int) (beamAlpha * 0.55f));
                //? if !=1.20.1 {
                
                buffer1.addVertex(stack.last().pose(), -glowRadius, -beamHeight, 0.001f).setColor(R, G, B, glowAlpha).setUv(0, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), -glowRadius, beamHeight, 0.001f).setColor(R, G, B, 0).setUv(0, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), glowRadius, beamHeight, 0.001f).setColor(R, G, B, 0).setUv(1, 1).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);

                buffer1.addVertex(stack.last().pose(), glowRadius, -beamHeight, 0.001f).setColor(R, G, B, glowAlpha).setUv(1, 0).setLight(15728880).setNormal(stack.last(), 0.0F, 1.0F, 0.0F);
                //?} else {

                /*buffer1.vertex(stack.last().pose(), -glowRadius, -beamHeight, 0.001f).color(R, G, B, glowAlpha).uv(0, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), -glowRadius, beamHeight, 0.001f).color(R, G, B, 0).uv(0, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), glowRadius, beamHeight, 0.001f).color(R, G, B, 0).uv(1, 1).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                buffer1.vertex(stack.last().pose(), glowRadius, -beamHeight, 0.001f).color(R, G, B, glowAlpha).uv(1, 0).uv2(15728880).normal(stack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();

                *///?}
            }


            stack.popPose();
        }

        stack.popPose();

        {

            if (glowConfig.enable_glow && renderState.onGround) {

                stack.pushPose();
                stack.translate(0, 0.01, 0);
                float radius = glowConfig.glow_effect_radius.get();
                renderGlow(stack, buffer.getBuffer(BeamRenderType.GLOW), R, G, B, ((int) (beamAlpha * 0.4f)), radius);
                stack.popPose();
            }

        }

    }


    private static void renderGlow(PoseStack stack, VertexConsumer builder, int red, int green, int blue, int alpha, float radius) {
        PoseStack.Pose matrixentry = stack.last();
        Matrix4f matrixpose = matrixentry.pose();
        // draw a quad on the xz plane facing up with a radius of 0.5
        //? if !=1.20.1 {
        builder.addVertex(matrixpose, -radius, (float) 0, -radius).setColor(red, green, blue, alpha).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, -radius, (float) 0, radius).setColor(red, green, blue, alpha).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, radius, (float) 0, radius).setColor(red, green, blue, alpha).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        builder.addVertex(matrixpose, radius, (float) 0, -radius).setColor(red, green, blue, alpha).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(matrixentry, 0.0F, 1.0F, 0.0F);
        //?} else {

        /*Matrix3f matrixnormal = matrixentry.normal();
        builder.vertex(matrixpose, -radius, (float) 0, -radius).color(red, green, blue, alpha).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, -radius, (float) 0, radius).color(red, green, blue, alpha).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, radius, (float) 0, radius).color(red, green, blue, alpha).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();
        builder.vertex(matrixpose, radius, (float) 0, -radius).color(red, green, blue, alpha).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(matrixnormal, 0.0F, 1.0F, 0.0F).endVertex();

        *///?}
    }


}
