package me.espada.system.components;


import me.espada.system.components.utils.ComponentPriority;


public abstract class Component {

    private ComponentPriority componentPriority;

   public void load() {
       setComponentPriority(ComponentPriority.NORMAL);
   }

   public abstract boolean enable();

   public abstract boolean disable();

    public ComponentPriority getComponentPriority() {
        return componentPriority;
    }

    public void setComponentPriority(ComponentPriority componentPriority) {
        this.componentPriority = componentPriority;
    }
}
