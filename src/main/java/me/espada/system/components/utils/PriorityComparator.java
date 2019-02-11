package me.espada.system.components.utils;

import me.espada.system.components.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PriorityComparator implements Comparator<Component> {

    private List<Component> componentList;

    public PriorityComparator(List<Component> componentList) {
        this.componentList = componentList;
    }

    public List<Component> getOrderdList() {
        componentList.sort(this);
        return componentList;
    }

    public Set<Component> getOrderdSet() {
        componentList.sort(this);
        return new HashSet<>(componentList);
    }

    @Override
    public int compare(Component c, Component o) {
        return Integer.compare(c.getComponentPriority().getPower(), o.getComponentPriority().getPower());
    }
}
