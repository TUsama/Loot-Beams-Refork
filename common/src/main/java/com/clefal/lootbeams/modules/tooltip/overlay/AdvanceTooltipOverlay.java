package com.clefal.lootbeams.modules.tooltip.overlay;

import com.clefal.lootbeams.LootBeamsConstants;
import com.clefal.lootbeams.config.configs.TooltipsConfig;
import com.clefal.lootbeams.data.lbitementity.LBItemEntity;
import com.clefal.lootbeams.data.lbitementity.LBItemEntityCache;
import com.clefal.lootbeams.events.TooltipsGatherNameAndRarityEvent;
import com.clefal.lootbeams.modules.tooltip.TooltipsEnableStatus;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdvanceTooltipOverlay {
    public static final AdvanceTooltipOverlay INSTANCE = new AdvanceTooltipOverlay();

    public static EntityHitResult getEntityItem(Player playerm, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        double distance = 6.0d;
        Vec3 position = player.getEyePosition(partialTick);
        Vec3 view = player.getViewVector(partialTick);
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
        return !TooltipsConfig.tooltipsConfig.tooltipsSection.render_tooltips_on_crouch || Minecraft.getInstance().player.isCrouching();
    }

    public Vector2f transformToScreenCoordinate(Vector3f worldCoordinate, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        /*Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 cameraPosition = camera.getPosition();

        Vector3f position = new Vector3f((float) (cameraPosition.x - worldCoordinate.x), (float) (cameraPosition.y - worldCoordinate.y), (float) (cameraPosition.z - worldCoordinate.z));
        Quaternionf cameraRotation = camera.rotation();
        cameraRotation.conjugate();
        cameraRotation.transform(position);

        // Account for view bobbing
        if (mc.options.bobView().get() && mc.getCameraEntity() instanceof Player) {
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
        position.add(window.getGuiScaledWidth() / 2f, window.getGuiScaledHeight() / 2f, 0f);*/
        Window window = mc.getWindow();
        return new Vector2f(window.getGuiScaledWidth() / 2f, window.getGuiScaledHeight() / 2f);
    }

    public void render(GuiGraphics guiGraphics, DeltaTracker tracker) {
        //cannot request this when register overlay, so I have to put it at here.
        if (TooltipsConfig.tooltipsConfig.tooltips_enable_status != TooltipsEnableStatus.TooltipsStatus.NAME_AND_RARITY_IN_TOOLTIPS)
            return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return;
        EntityHitResult entityItem = getEntityItem(mc.player, tracker.getGameTimeDeltaPartialTick(true));
        if (entityItem == null) return;
        ItemEntity itemEntity = ((ItemEntity) entityItem.getEntity());
        LBItemEntity ask = LBItemEntityCache.ask(itemEntity);
        Vector2f vector2f = this.transformToScreenCoordinate(itemEntity.position().toVector3f(), tracker.getGameTimeDeltaTicks());

        if (checkCrouch()) {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, itemEntity.getItem(), (int) vector2f.x, (int) vector2f.y);
        } else {
            TooltipsGatherNameAndRarityEvent tooltipsGatherNameAndRarityEvent = new TooltipsGatherNameAndRarityEvent(ask);
            LootBeamsConstants.EVENT_BUS.post(tooltipsGatherNameAndRarityEvent);
            List<Component> nameAndRarity = new ArrayList<>(tooltipsGatherNameAndRarityEvent.gather.values());

            guiGraphics.renderTooltip(Minecraft.getInstance().font, nameAndRarity, itemEntity.getItem().getTooltipImage(), (int) vector2f.x, (int) vector2f.y);
        }

    }
}
