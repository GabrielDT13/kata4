package software.ulpgc.control.io;

import software.ulpgc.model.Title;

import java.io.File;
import java.sql.*;

public class SQLiteTitleWriter implements TitleWriter, AutoCloseable{
    private Connection connection;
    private static final String createTableSql = """
			CREATE TABLE IF NOT EXISTS titles (
				id TEXT PRIMARY KEY,
				type TEXT,
				primaryTitle TEXT
			);
			""";
    private static final String insertStatement = "INSERT INTO titles(id, type, primaryTitle) VALUES(?,?,?)";

    public SQLiteTitleWriter(File dbFile){
        try{
            prepareDatabase(dbFile);
            createTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        try(Statement stm = connection.createStatement()){
            stm.execute(createTableSql);
            System.out.println("Tabla creada o ya existía");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla " + e.getMessage());
        }
    }

    private void prepareDatabase(File dbFile) throws SQLException {
        connection = openConnection(dbFile);
        connection.setAutoCommit(false);
    }

    private Connection openConnection(File dbFile) throws SQLException {
        String url = "jdbc:sqlite:" + dbFile;
        return DriverManager.getConnection(url);
    }

    @Override
    public void close() throws Exception {
        connection.commit();
        connection.close();
    }

    @Override
    public void write(Title title) {
        try(PreparedStatement statement = connection.prepareStatement(insertStatement)){
            statement.setString(1, title.id());
            statement.setString(2, String.valueOf(title.titleType()));
            statement.setString(3, title.primaryTitle());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al escribir en la tabla" + e.getMessage());
        }

    }

    public boolean isEmpty() {
        String sql = "SELECT COUNT(*) AS total FROM titles";
        try(Statement statement =connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                int total = resultSet.getInt("total");
                return total == 0;
            }
        } catch (Exception e) {
            System.out.println("Error al comprobar tamaño tabla" + e.getMessage());
        }

        return true;
    }
}
