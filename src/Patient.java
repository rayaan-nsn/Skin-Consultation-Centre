import java.util.Date;

public class Patient extends Person{    //Subclass of person
    private String patientID;  // Field for the patient's unique ID

    public Patient(String name, String surname, int dateOfBirth, int mobilNo, String patientID) {
        super(name, surname, dateOfBirth, mobilNo);
        this.patientID = patientID;
    }

    // Getter and setter methods for the patient ID field

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }
}
