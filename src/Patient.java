import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Patient extends Person implements Serializable {    //Subclass of person
    private String patientID;  // Field for the patient's unique ID
    private String patientNIC;

    public Patient(String name, String surname, LocalDate dateOfBirth, int mobilNo, String patientID, String patientNIC) {
        super(name, surname, dateOfBirth, mobilNo);
        this.patientID = patientID;
        this.patientNIC = patientNIC;
    }

    // Getter and setter methods for the patient ID field

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientNIC(String patientNIC) {
        this.patientNIC = patientNIC;
    }

    public String getPatientNIC() {
        return patientNIC;
    }
}
