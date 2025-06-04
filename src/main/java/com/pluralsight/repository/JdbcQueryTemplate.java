package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcQueryTemplate<T> extends AbstractDAO {

    // This class pattern will Convert a query to a template method
    // which makes the code not cluttered, messy and reduce duplication codes

    public JdbcQueryTemplate() {

    }

    public List<T> queryForList(String sql) {
        List<T> items = new ArrayList<>();

        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next()) {items.add(mapItem(resultSet));}
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return items;
    }

    public abstract T mapItem (ResultSet resultSet) throws SQLException;

}
