package dette.boutique.core.database.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dette.boutique.core.database.DataBase;

public abstract class DataBasePostgreImpl<T> implements DataBase<T> {
    // URL de connexion pour PostgreSQL
    private final String URL = "jdbc:postgresql://localhost:5111/dette_boutique"; 
    private final String USER = "postgres";
    private final String PASSWORD = "";
    protected Connection connection = null;
    protected PreparedStatement ps = null;

    @Override
    public Connection connexion() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC PostgreSQL non trouvé.");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void deconnexion() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return ps.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        return ps.executeUpdate();
    }

    @Override
    public void init(String sql) throws SQLException {
        String sqlUpperCase = sql.toUpperCase().trim();

        if (sqlUpperCase.startsWith("INSERT")) {
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement(sql);
        }
    }
}
