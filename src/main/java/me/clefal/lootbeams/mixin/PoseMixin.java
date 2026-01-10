package me.clefal.lootbeams.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.clefal.lootbeams.duck.PoseCopy;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PoseStack.Pose.class)
public abstract class PoseMixin implements PoseCopy {


    @Shadow
    public abstract Matrix4f pose();

    @Shadow
    public abstract Matrix3f normal();

    @Override
    public PoseStack.Pose copy() {
        return PoseInvoker.createPose(this.pose(), this.normal());
    }
}
