package arpinum.boundedcontext;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Injector;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class Patcher {

    public Patcher(Injector injector, String name) {
        this.injector = injector;
        this.name = name;
    }

    public void run(String packageToScan) {
        ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
        new FastClasspathScanner(packageToScan)
                .matchClassesWithAnnotation(Patch.class, builder::add)
                .scan();
        ImmutableSet<Class<?>> patches = builder.build();
        patches.stream()
                .filter(c -> c.getPackage().getName().startsWith(packageToScan))
                .map(c -> {
                    final PatchNumber annotation = c.getAnnotation(PatchNumber.class);
                    return new PatchDescription(annotation.value(), (Class<? extends Patch>) c);
                }).sorted(Comparator.comparingInt(o -> o.number))
                .forEach(this::runIfNeeded);
    }

    private void runIfNeeded(PatchDescription patchDescription) {
        final Jongo jongo = injector.getInstance(Jongo.class);
        if (jongo.getCollection("patch_" + name).count("{_id:#}", patchDescription.number) == 0) {
            LOGGER.info("running {} ", patchDescription.clazz.getName());
            try {
                runPatch(patchDescription, jongo);
            } catch (Exception e) {
                LOGGER.error("Fail patch", e);
                return;
            }
        }
    }

    private void runPatch(PatchDescription patchDescription, Jongo jongo) {
        final Patch patch = injector.getInstance(patchDescription.clazz);
        patch.run();
        jongo.getCollection("patch_" + name).insert("{_id:#}", patchDescription.number);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Patcher.class);
    private final Injector injector;
    private final String name;

    private class PatchDescription {

        public PatchDescription(int number, Class<? extends Patch> clazz) {
            this.number = number;
            this.clazz = clazz;
        }

        int number;
        Class<? extends Patch> clazz;
    }
}
