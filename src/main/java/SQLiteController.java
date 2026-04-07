import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class SQLiteController {

    private Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");

    public SQLiteController() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS subjects (id INTEGER PRIMARY KEY AUTOINCREMENT, subject_name VARCHAR);";
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        stmt.close();

    }

    public void addSubject(List<Word> words, String subjectName) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS words (subject_id INTEGER, id INTEGER PRIMARY KEY AUTOINCREMENT, pronoun VARCHAR, word VARCHAR, translation VARCHAR, FOREIGN KEY (subject_id) REFERENCES subjects (id));";
        Statement stmt = conn.createStatement();
        stmt.execute(query);

        query = "INSERT INTO subjects (subject_name) VALUES (?);";
        java.sql.PreparedStatement stmt2 = conn.prepareStatement(query);
        stmt2.setString(1, subjectName);
        stmt2.executeUpdate();

        query = "SELECT id FROM subjects WHERE subject_name = ?;";
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

    public ArrayList<String> getSubjects() throws SQLException {
        ArrayList<String> subjects = new ArrayList<>();
        String query = "SELECT * FROM subjects;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            subjects.add(rs.getString(2));
        }
        return subjects;
    }

    public ArrayList<Integer> getSubIDs(ArrayList<String> subjectNames) throws SQLException {
        ArrayList<Integer> subIDs = new ArrayList<>();

        for (String subjectName : subjectNames) {
            String query = "SELECT id FROM subjects WHERE subject_name LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                subIDs.add(rs.getInt(1));
            }
        }

        return subIDs;
    }

    public ArrayList<Word> getWords(ArrayList<Integer> subIDs) throws SQLException {
        ArrayList<Word> words = new ArrayList<>();

        for(Integer subID : subIDs){
            String query = "SELECT pronoun, word, translation FROM words WHERE subject_id LIKE ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, subID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Word word = new Word(rs.getString(1), rs.getString(2), rs.getString(3));
                words.add(word);
            }
        }
        return words;
    }
}
