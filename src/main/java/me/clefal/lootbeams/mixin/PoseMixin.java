package me.clefal.lootbeams.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.lootbeams.duck.PoseCopy;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PoseStack.Pose.class)
public abstract class PoseMixin implements PoseCopy {


    @Override
    public PoseStack.Pose loot_Beams_Refork$copy() {
        return PoseInvoker.createPose(((PoseStack.Pose)((Object) this)));
    }
}
