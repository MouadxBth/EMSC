package me.espada.system.components.collection;

import me.espada.system.components.Component;

public class TestClass extends Component {



    @Override
    public boolean enable() {
        System.out.println("ENABLED");
        return true;
    }

    @Override
    public boolean disable() {
        System.out.println("DISABLED");
        return true;
    }
}
