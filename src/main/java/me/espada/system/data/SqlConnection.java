package me.espada.system.data;

import me.espada.system.components.Component;
import me.espada.system.components.utils.ComponentPriority;

import java.sql.Connection;

public class SqlConnection extends Component {

    private SqlAcces sqlAcces;

    @Override
    public void load() {
        setComponentPriority(ComponentPriority.HIGHEST);
    }

    @Override
    public boolean enable() {
        if(sqlAcces == null) {
            sqlAcces = new SqlAcces();
        }
        return sqlAcces.init();
    }

    @Override
    public boolean disable() {
        return sqlAcces.shutdown();
    }


    public Connection getConnection() {
        return sqlAcces.getConnection();
    }
}
