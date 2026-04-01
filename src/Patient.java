import java.time.LocalDate;

public class Patient {
    private int patientId;
    private String patientFirstName;
    private String patientLastName;
    private LocalDate patientDOB;

    public Patient(int id, String firstName, String lastName, LocalDate dob) {
        this.patientId = id;
        this.patientFirstName = firstName;
        this.patientLastName = lastName;
        this.patientDOB = dob;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public LocalDate getPatientDOB() {
        return patientDOB;
    }

    // format: id|firstName|lastName|dob
    public String toFileLine() {
        return patientId + "|" + clean(patientFirstName) + "|" + clean(patientLastName) + "|" + patientDOB;
    }

    public static Patient fromFileLine(String line) {
        String[] parts = line.split("\\|");

        if (parts.length != 4) {
            System.out.println("Invalid line: " + line);
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String firstName = parts[1];
            String lastName = parts[2];
            LocalDate dob = LocalDate.parse(parts[3]);

            return new Patient(id, firstName, lastName, dob);
        } catch (Exception e) {
            System.out.println("Error reading line: " + line);
            return null;
        }
    }

    @Override
    public String toString() {
        return patientFirstName + " " + patientLastName + " (ID: " + patientId + ")";
    }

    private String clean(String text) {
        if (text == null) return "";
        return text.replace("|", "/");
    }
}