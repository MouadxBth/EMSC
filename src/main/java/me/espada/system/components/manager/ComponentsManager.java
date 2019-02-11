package me.espada.system.components.manager;


import me.espada.Core;

import me.espada.system.components.Component;
import me.espada.system.components.utils.PriorityComparator;
import me.espada.system.utils.Utils;

import java.util.*;
import java.util.function.Consumer;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ComponentsManager {


    private Consumer<Component> validateLoad = (component -> {
        component.load();
        if (component.getComponentPriority() == null) {
            Core.getCoreLogger().log("&cCould not load a component with the name: &7" + component.getClass().getSimpleName());
        }
    });

    private Consumer<Component> validateEnable = (component -> {
        if (!component.enable()) {
            Core.getCoreLogger().log("&cCould not enable a component with the name: &7" + component.getClass().getSimpleName());
        }
    });

    private Consumer<Component> validateDisable = (component -> {
        if (!component.disable()) {
            Core.getCoreLogger().log("&cCould not disable a component with the name: &7" + component.getClass().getSimpleName());
        }
    });

    public Consumer<Core> load = (c) -> {
        if (!Utils.isEmpty.test(getClassComponentsList().get())) {
            getOrderdComponentsList().get().forEach(component -> {
                validateLoad.accept(component);
                validateEnable.accept(component);
            });
        }

        Core.getCoreLogger().log("&aAll components were loaded and enabled!");

    };

    public Consumer<Core> unload = (c) -> {
        if(!Utils.isEmpty.test(getClassComponentsList().get())) {
            getOrderdComponentsList().get().forEach(component -> {
                validateDisable.accept(component);
            });
        }

        Core.getCoreLogger().log("&aAll components were disabled!");
    };

    public Supplier<Set<Component>> getOrderdComponentsSet() {
        return () -> new HashSet<>(getOrderdComponentsList().get());
    }

    public Supplier<List<Component>> getOrderdComponentsList() {
        return () -> new PriorityComparator(getComponentsList().get()).getOrderdList();
    }

    public Supplier<Set<Component>> getComponentsSet() {
        return () -> new HashSet<>(getComponentsList().get());
    }

    public Supplier<List<Component>> getComponentsList() {
        return () -> getClassComponentsList().get().stream().map(e -> {
            try {
                return (Component) e.newInstance();
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    private Supplier<Set<Class<?>>> getClassComponentsSet() {
        return () -> new HashSet<>(getClassComponentsList().get());
    }

    private Supplier<List<Class<?>>> getClassComponentsList() {
        return () -> new ArrayList<>(Utils.getClasses(Core.getInstance().getJarFile(), Core.class.getPackage().getName(), Component.class));
    }

}
