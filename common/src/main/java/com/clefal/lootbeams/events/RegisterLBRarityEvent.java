package com.clefal.lootbeams.events;

import com.clefal.lootbeams.data.rarity.ILBRarityApplier;
import lombok.Getter;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.function.Consumer;

@Getter
public abstract class RegisterLBRarityEvent extends Event {
    protected final List<ILBRarityApplier> gather;

    public RegisterLBRarityEvent(List<ILBRarityApplier> rarities) {
        this.gather = rarities;
    }

    public static class Pre extends RegisterLBRarityEvent {

        public Pre(List<ILBRarityApplier> rarities) {
            super(rarities);
        }

        public void register(ILBRarityApplier rarity) {
            gather.add(rarity);
        }

        public void register(List<ILBRarityApplier> rarities) {
            gather.addAll(rarities);
        }

    }

    public static class Post extends RegisterLBRarityEvent {
        public Post(List<ILBRarityApplier> rarities) {
            super(rarities);
        }

        //it could be safer to pass a comparator but whatever.
        //the first match rarity would be selected, so it's important for those developers who want their rarity to have higher priority. 
        public void sort(Consumer<List<ILBRarityApplier>> consumer) {
            consumer.accept(gather);
        }
    }
}
