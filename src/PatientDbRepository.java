import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PatientDbRepository {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public PatientDbRepository(String url, String user, String pass) {
        this.dbUrl = url;
        this.dbUser = user;
        this.dbPassword = pass;
    }

    public void save(Patient p) {
        String sql = "INSERT INTO patients (patient_id, patient_first_name, patient_last_name, patient_dob) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getPatientId());
            ps.setString(2, p.getPatientFirstName());
            ps.setString(3, p.getPatientLastName());
            ps.setDate(4, Date.valueOf(p.getPatientDOB()));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving patient");
        }
    }

    public ArrayList<Patient> findAll() {
        ArrayList<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM patients ORDER BY patient_id";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("patient_id");
                String first = rs.getString("patient_first_name");
                String last = rs.getString("patient_last_name");
                LocalDate dob = rs.getDate("patient_dob").toLocalDate();

                Patient p = new Patient(id, first, last, dob);
                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error reading patients");
        }

        return list;
    }
}