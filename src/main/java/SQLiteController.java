import java.sql.*;
import java.util.List;
import java.sql.Connection;

public class SQLiteController {

    private Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");

    public SQLiteController() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS subject (id INTEGER PRIMARY KEY AUTOINCREMENT, subject_name VARCHAR);";
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        stmt.close();

    }

    public void addSubject(List<Word> words, String subjectName) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS words (subject_id INTEGER, id INTEGER PRIMARY KEY AUTOINCREMENT, pronoun VARCHAR, word VARCHAR, translation VARCHAR, FOREIGN KEY (subject_id) REFERENCES subject (id));";
        Statement stmt = conn.createStatement();
        stmt.execute(query);

        query = "INSERT INTO subject (subject_name) VALUES (?);";
        java.sql.PreparedStatement stmt2 = conn.prepareStatement(query);
        stmt2.setString(1, subjectName);
        stmt2.executeUpdate();

        query = "SELECT id FROM subject WHERE subject_name = ?;";
        stmt2 = conn.prepareStatement(query);
        stmt2.setString(1, subjectName);
        ResultSet rs = stmt2.executeQuery();

        for(Word word : words){
            query = "INSERT INTO words (subject_id, pronoun, word, translation) VALUES (?, ?, ?, ?);";
            stmt2 = conn.prepareStatement(query);
            stmt2.setString(1, rs.getString(1));
            stmt2.setString(2, word.getPronoun());
            stmt2.setString(3, word.getWord());
            stmt2.setString(4, word.getTranslation());
            stmt2.executeUpdate();
        }
    }
}
