package com.clefal.lootbeams.modules.tooltip.overlay;

import com.clefal.lootbeams.Constants;
import com.clefal.lootbeams.config.Config;
import com.clefal.lootbeams.config.ConfigurationManager;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.clefal.lootbeams.data.LBItemEntity;
import com.clefal.lootbeams.data.LBItemEntityCache;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.mojang.blaze3d.platform.Window;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdvanceTooltipOverlay {
    public static final AdvanceTooltipOverlay INSTANCE = new AdvanceTooltipOverlay();

    public static EntityHitResult getEntityItem(Player player) {
        Minecraft mc = Minecraft.getInstance();
        double distance = player.getBlockReach();
        float partialTicks = mc.getDeltaFrameTime();
        Vec3 position = player.getEyePosition(partialTicks);
        Vec3 view = player.getViewVector(partialTicks);
        if (mc.hitResult != null && mc.hitResult.getType() != HitResult.Type.MISS)
            distance = mc.hitResult.getLocation().distanceTo(position);
        return getEntityItem(player, position, position.add(view.x * distance, view.y * distance, view.z * distance));

    }

    public static EntityHitResult getEntityItem(Player player, Vec3 position, Vec3 look) {
        Vec3 include = look.subtract(position);
        List<Entity> list = player.level().getEntities(player, player.getBoundingBox().expandTowards(include.x, include.y, include.z));
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity instanceof ItemEntity item) {
                AABB axisalignedbb = item.getBoundingBox().inflate(0.5).inflate(0.0, 0.5, 0.0);
                Optional<Vec3> vec = axisalignedbb.clip(position, look);
                if (vec.isPresent())
                    return new EntityHitResult(item, vec.get());
                else if (axisalignedbb.contains(position))
                    return new EntityHitResult(item);
            }
        }
        return null;
    }

    public static boolean checkCrouch() {
        Boolean request = ConfigurationManager.<Boolean>request(Config.SCREEN_TOOLTIPS_REQUIRE_CROUCH);
        return !request || Minecraft.getInstance().player.isCrouching();
    }

    public Vector2f transformToScreenCoordinate(Vector3f worldCoordinate, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPosition = camera.getPosition();

        Vector3f position = new Vector3f((float) (cameraPosition.x - worldCoordinate.x), (float) (cameraPosition.y - worldCoordinate.y), (float) (cameraPosition.z - worldCoordinate.z));
        Quaternionf cameraRotation = camera.rotation();
        cameraRotation.conjugate();
        cameraRotation.transform(position);

        // Account for view bobbing
        if (mc.options.bobView.get() && mc.getCameraEntity() instanceof Player) {
            Player player = (Player) mc.getCameraEntity();
            float playerStep = player.walkDist - player.walkDistO;
            float stepSize = -(player.walkDist + playerStep * partialTicks);
            float viewBob = Mth.lerp(partialTicks, player.oBob, player.bob);

            Quaternionf bobXRotation = Axis.XP.rotationDegrees(Math.abs(Mth.cos(stepSize * (float) Math.PI - 0.2f) * viewBob) * 5f);
            Quaternionf bobZRotation = Axis.ZP.rotationDegrees(Mth.sin(stepSize * (float) Math.PI) * viewBob * 3f);
            bobXRotation.conjugate();
            bobZRotation.conjugate();
            bobXRotation.transform(position);
            bobZRotation.transform(position);
            position.add(Mth.sin(stepSize * (float) Math.PI) * viewBob * 0.5f, Math.abs(Mth.cos(stepSize * (float) Math.PI) * viewBob), 0f);
        }

        Window window = mc.getWindow();
        float screenSize = window.getGuiScaledHeight() / 2f / position.z() / (float) Math.tan(Math.toRadians(mc.gameRenderer.getFov(camera, partialTicks, true) / 2f));
        position.mul(-screenSize, -screenSize, 1f);
        position.add(window.getGuiScaledWidth() / 2f, window.getGuiScaledHeight() / 2f, 0f);

        return new Vector2f(position.x, position.y);
    }

    public void render(GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        //cannot request this when register overlay, so I have to put it at here.
        if (ConfigurationManager.request(Config.ENABLE_TOOLTIPS) != TooltipsEnableStatus.TooltipsStatus.NAME_RARITY_TOOLTIPS) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return;
        EntityHitResult entityItem = getEntityItem(mc.player);
        if (entityItem == null) return;
        ItemEntity itemEntity = ((ItemEntity) entityItem.getEntity());
        LBItemEntity ask = LBItemEntityCache.ask(itemEntity);
        Vector2f vector2f = this.transformToScreenCoordinate(itemEntity.position().toVector3f(), partialTick);

        if (checkCrouch()) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, itemEntity.getItem(), (int) vector2f.x, (int) vector2f.y);
        } else {
            TooltipsGatherNameAndRarityEvent tooltipsGatherNameAndRarityEvent = new TooltipsGatherNameAndRarityEvent(ask);
            Constants.EVENT_BUS.post(tooltipsGatherNameAndRarityEvent);
            List<Component> nameAndRarity = new ArrayList<>(tooltipsGatherNameAndRarityEvent.gather.values());

            guiGraphics.renderTooltip(Minecraft.getInstance().font, nameAndRarity, itemEntity.getItem().getTooltipImage(), itemEntity.getItem(), (int) vector2f.x, (int) vector2f.y);
        }

    }
}
