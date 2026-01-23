package me.clefal.lootbeams.data.new_render;

import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.lootbeams.LootBeamsConstants;
import me.clefal.lootbeams.config.configs.LightConfig;
import me.clefal.lootbeams.config.configs.LootInfomationConfig;
import me.clefal.lootbeams.data.lbitementity.LBItemEntity;
import me.clefal.lootbeams.data.lbitementity.rarity.LBRarity;
import me.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class LootBeamRenderState {

    public final LBRarity rarity;
    public final Vec3 location;
    public final PoseStack.Pose poseStack;


    private LootBeamRenderState(
            LBRarity rarity,
            Vec3 location,
            PoseStack.Pose poseStack
    ) {
        this.rarity = rarity;
        this.location = location;

        this.poseStack = poseStack;
    }


    public static class NameTagRenderState extends LootBeamRenderState {
        public final List<Component> nameAndRarity;
        public final boolean isLookingAtThis;

        public NameTagRenderState(LBRarity rarity, Vec3 location, PoseStack.Pose poseStack, List<Component> nameAndRarity) {
            super(rarity, location, poseStack);
            this.nameAndRarity = nameAndRarity;
            this.isLookingAtThis = isLookingAt(Minecraft.getInstance().player, location, LootInfomationConfig.lootInfomationConfig.nameTag.name_tag_look_sensitivity.get());
        }


        public static NameTagRenderState fromLBEntity(LBItemEntity lbItemEntity, PoseStack.Pose stack){
            ItemEntity item = lbItemEntity.item();
            TooltipsGatherNameAndRarityEvent tooltipsGatherNameAndRarityEvent = new TooltipsGatherNameAndRarityEvent(lbItemEntity);
            LootBeamsConstants.EVENT_BUS.post(tooltipsGatherNameAndRarityEvent);
            List<Component> nameAndRarity = new ArrayList<>(tooltipsGatherNameAndRarityEvent.gather.values());
            return new NameTagRenderState(lbItemEntity.rarity(), new Vec3(item.getX(), item.getY(), item.getZ()), stack, nameAndRarity);
        }

        /**
         * Checks if the player is looking at the given entity, accuracy determines how close the player has to look.
         */
        public static boolean isLookingAt(LocalPlayer player, Vec3 position, double accuracy) {
            Vec3 difference = new Vec3(position.x - player.getX(), position.y - player.getEyeY(), position.z - player.getZ());
            double length = difference.length();
//        double dot = player.getViewVector(1.0F).normalize().dot(difference.normalize());
            double dot = Minecraft.getInstance().getCameraEntity().getLookAngle().normalize().dot(difference.normalize());
            return dot > 1.0D - accuracy / length;
        }
    }


    public static class BeamRenderState extends LootBeamRenderState {
        public final int fadeIn;
        public final float partialTick;
        public final boolean onGround;
        public final boolean isShaderOn;

        public BeamRenderState(LBRarity rarity, Vec3 location, PoseStack.Pose poseStack, int fadeIn, float partialTick, boolean onGround, boolean isShaderOn) {
            super(rarity, location, poseStack);
            this.fadeIn = fadeIn;
            this.partialTick = partialTick;
            this.onGround = onGround;
            this.isShaderOn = isShaderOn;
        }

        public static BeamRenderState make(LBItemEntity lbItemEntity, PoseStack.Pose stack, float partialTick, boolean isShaderOn){
            ItemEntity item = lbItemEntity.item();
            BeamRenderState beamRenderState = new BeamRenderState(lbItemEntity.rarity(), new Vec3(item.getX(), item.getY(), item.getZ()), stack, lbItemEntity.fadeIn(), partialTick, lbItemEntity.item().onGround(), isShaderOn);
            if (lbItemEntity.fadeIn() < LightConfig.lightConfig.beam.beam_fade_in_time.get()) lbItemEntity.updateFade();
            return beamRenderState;
        }
    }
}
