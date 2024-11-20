package software.ulpgc.control.io;

import software.ulpgc.model.Title;

import java.io.File;
import java.sql.*;

public class SQLiteTitleReader implements TitleReader, AutoCloseable{
    private Connection connection;
    private String readTable = "SELECT * FROM titles";
    private ResultSet titles;
    private PreparedStatement statement;
    private String randomTitle;

    public SQLiteTitleReader(File dbFile){
        try{
            connection = openConnection(dbFile);
            statement = connection.prepareStatement(readTable);
            titles = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection openConnection(File dbFile) throws SQLException {
        String url = "jdbc:sqlite:"+dbFile;
        return DriverManager.getConnection(url);
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public Title read() {
        try{
            if(titles.next()){
                Title title = new Title(
                        titles.getString(1),
                        Title.TitleType.valueOf(titles.getString(2)),
                        titles.getString(3)
                );
                return title;
            }
        } catch (Exception e) {
            System.out.println("Error al leer de la tabla" + e.getMessage());
        }

        return null;
    }

    public String readRandomTitle() {
        String sql = "SELECT primaryTitle FROM titles ORDER BY RANDOM() LIMIT 1";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) randomTitle = resultSet.getString(1);
        } catch (SQLException e) {
            System.out.println("Error al leer aleatorio" + e.getMessage());
        }
        return randomTitle;
    }
}
