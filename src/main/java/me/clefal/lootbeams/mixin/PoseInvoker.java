package me.clefal.lootbeams.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PoseStack.Pose.class)
public interface PoseInvoker {
    //? 1.20.1 {
    /*@Invoker("<init>")
    static PoseStack.Pose createPose(Matrix4f pose, Matrix3f normal) {
        throw new RuntimeException();
    }

    *///? } else {
    //? < 1.21.8 {
    @Invoker("<init>")
    static PoseStack.Pose createPose(PoseStack.Pose pose) {
        throw new RuntimeException();
    }
    //?}

    //?}

}
