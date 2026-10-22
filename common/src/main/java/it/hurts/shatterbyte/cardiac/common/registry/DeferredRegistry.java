package it.hurts.shatterbyte.cardiac.common.registry;

import it.hurts.shatterbyte.cardiac.common.Cardiac;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DeferredRegistry<T> {
    @Getter
    private final ResourceKey<? extends Registry<T>> registryKey;

    private final List<Entry<T, ? extends T>> entries = new ArrayList<>();

    private DeferredRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    public static <T> DeferredRegistry<T> create(ResourceKey<? extends Registry<T>> registryKey) {
        return new DeferredRegistry<>(registryKey);
    }

    public <R extends T> Supplier<R> register(String name, Function<ResourceKey<T>, R> factory) {
        var entry = new Entry<T, R>(ResourceKey.create(registryKey, Identifier.fromNamespaceAndPath(Cardiac.MODID, name)), factory);

        entries.add(entry);

        return entry;
    }

    public void registerAll(BiConsumer<Identifier, T> registrar) {
        for (var entry : entries)
            registrar.accept(entry.key.identifier(), entry.create());
    }

    private static class Entry<T, R extends T> implements Supplier<R> {
        private final ResourceKey<T> key;
        private final Function<ResourceKey<T>, R> factory;

        private R value;

        private Entry(ResourceKey<T> key, Function<ResourceKey<T>, R> factory) {
            this.key = key;
            this.factory = factory;
        }

        private R create() {
            if (value == null)
                value = factory.apply(key);

            return value;
        }

        @Override
        public R get() {
            if (value == null)
                throw new IllegalStateException("Registry entry " + key.identifier() + " is not registered yet");

            return value;
        }
    }
}
