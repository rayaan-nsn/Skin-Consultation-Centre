import java.util.Date;

public class Patient extends Person{    //Subclass of person
    private int patientID;  // Field for the patient's unique ID

    public Patient(String name, String surname, Date dateOfBirth, int mobilNo, int patientID) {
        super(name, surname, dateOfBirth, mobilNo);
        this.patientID = patientID;
    }

    // Getter and setter methods for the patient ID field

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getPatientID() {
        return patientID;
    }
}
