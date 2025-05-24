import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PatientManagementGUI {
    private PatientDAO patientDAO;

    public PatientManagementGUI(PatientDAO dao) {
        this.patientDAO = dao;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Patient Management");
        frame.setSize(400, 520);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // --- Input Fields ---
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(20, 20, 80, 25);
        frame.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(100, 20, 250, 25);
        frame.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 80, 25);
        frame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 60, 250, 25);
        frame.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 80, 25);
        frame.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(100, 100, 250, 25);
        frame.add(ageField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(20, 140, 80, 25);
        frame.add(genderLabel);

        JTextField genderField = new JTextField();
        genderField.setBounds(100, 140, 250, 25);
        frame.add(genderField);

        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setBounds(20, 180, 80, 25);
        frame.add(diseaseLabel);

        JTextField diseaseField = new JTextField();
        diseaseField.setBounds(100, 180, 250, 25);
        frame.add(diseaseField);

        // --- Buttons ---
        JButton submitButton = new JButton("Add Patient");
        submitButton.setBounds(20, 220, 150, 30);
        frame.add(submitButton);

        JButton listButton = new JButton("List Patients");
        listButton.setBounds(200, 220, 150, 30);
        frame.add(listButton);

        JButton deleteButton = new JButton("Delete by ID");
        deleteButton.setBounds(20, 260, 150, 30);
        frame.add(deleteButton);

        JButton updateButton = new JButton("Update Patient");
        updateButton.setBounds(200, 260, 150, 30);
        frame.add(updateButton);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(110, 300, 150, 30);
        frame.add(searchButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(125, 440, 130, 30);
        frame.add(exitButton);

        // --- Text Area to Display Patients ---
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 340, 340, 90);
        frame.add(scrollPane);

        // --- Event Listeners ---

        // Exit
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
                System.exit(0);
            }
        });

        // Add
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = genderField.getText().trim();
            String disease = diseaseField.getText().trim();

            if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields except ID are required.");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                Patient patient = new Patient(name, age, gender, disease);
                patientDAO.addPatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient added successfully.");
                nameField.setText("");
                ageField.setText("");
                genderField.setText("");
                diseaseField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age must be a valid number.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // List
        listButton.addActionListener(e -> {
            try {
                List<Patient> patients = patientDAO.getAllPatients();
                outputArea.setText("");
                if (patients.isEmpty()) {
                    outputArea.setText("No patients found.");
                } else {
                    for (Patient p : patients) {
                        outputArea.append(p.toString() + "\n");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error fetching patients: " + ex.getMessage());
            }
        });

        // Delete
        deleteButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter Patient ID to delete.");
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                patientDAO.deletePatientById(id);
                JOptionPane.showMessageDialog(frame, "Patient deleted successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID must be a number.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting patient: " + ex.getMessage());
            }
        });

        // Update
        updateButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = genderField.getText().trim();
            String disease = diseaseField.getText().trim();

            if (idText.isEmpty() || name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields including ID are required for update.");
                return;
            }

            try {
                int id = Integer.parseInt(idText);
                int age = Integer.parseInt(ageText);
                Patient patient = new Patient(id, name, age, gender, disease);
                patientDAO.updatePatient(patient);
                JOptionPane.showMessageDialog(frame, "Patient updated successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID and Age must be valid numbers.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error updating patient: " + ex.getMessage());
            }
        });

        // Search
        searchButton.addActionListener(e -> {
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            outputArea.setText("");

            try {
                if (!idText.isEmpty()) {
                    int id = Integer.parseInt(idText);
                    Patient p = patientDAO.getPatientById(id);
                    if (p != null) {
                        outputArea.append(p.toString());
                    } else {
                        outputArea.setText("No patient found with ID: " + id);
                    }
                } else if (!name.isEmpty()) {
                    List<Patient> patients = patientDAO.getPatientsByName(name);
                    if (patients.isEmpty()) {
                        outputArea.setText("No patients found with name: " + name);
                    } else {
                        for (Patient p : patients) {
                            outputArea.append(p.toString() + "\n");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Enter ID or Name to search.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID must be a number.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error searching patients: " + ex.getMessage());
            }
        });

        // Show the GUI
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.connect();
            PatientDAO dao = new PatientDAOImpl(conn);
            new PatientManagementGUI(dao);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }
}
