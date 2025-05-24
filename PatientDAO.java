import java.sql.SQLException;
import java.util.List;

public interface PatientDAO {
    void addPatient(Patient patient) throws SQLException;
    List<Patient> getAllPatients() throws SQLException;
    void deletePatientById(int id) throws SQLException;
    void updatePatient(Patient patient) throws SQLException;
    Patient getPatientById(int id) throws SQLException;
    List<Patient> getPatientsByName(String name) throws SQLException;
}
