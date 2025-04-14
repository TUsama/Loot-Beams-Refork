package com.clefal.lootbeams.modules.compat.photon;

import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.gameobject.IFXObject;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DeepCopyEntityEffect extends EntityEffect {
    public DeepCopyEntityEffect(FX fx, Level level, Entity entity) {
        super(fx, level, entity);
    }

    @Override
    public void start() {
        if (this.entity.isAlive()) {
            List<EntityEffect> effects = CACHE.computeIfAbsent(this.entity, (p) -> new ArrayList<>());
            if (!this.allowMulti) {
                Iterator<EntityEffect> iter = effects.iterator();

                label32:
                while(true) {
                    EntityEffect effect;
                    boolean removed;
                    do {
                        if (!iter.hasNext()) {
                            break label32;
                        }

                        effect = (EntityEffect)iter.next();
                        removed = false;
                        if (effect.getRuntime() != null && !effect.getRuntime().isAlive()) {
                            iter.remove();
                            removed = true;
                        }
                    } while(!effect.fx.equals(this.fx) && !Objects.equals(effect.fx.getFxLocation(), this.fx.getFxLocation()));

                    if (!removed) {
                        return;
                    }
                }
            }

            this.runtime = this.fx.createRuntime(true);
            IFXObject root = this.runtime.getRoot();
            root.updatePos(this.entity.getPosition(0.0F).toVector3f().add(this.offset.x, this.offset.y, this.offset.z));
            root.updateRotation(this.rotation);
            root.updateScale(this.scale);
            this.runtime.emmit(this);
            effects.add(this);
        }

    }
}
