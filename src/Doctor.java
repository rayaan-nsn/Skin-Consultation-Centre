import java.io.Serializable;
import java.util.Date;

public class Doctor extends Person implements Serializable {  //Subclass of person

    // Fields for the doctor's medical license number and specialization
    private int licenceNo;
    private String specialisation;

    public Doctor(String name, String surname, int dateOfBirth, int mobilNo, int licenceNo, String specialisation) {
        super(name, surname, dateOfBirth, mobilNo);
        this.specialisation = specialisation;
        this.licenceNo = licenceNo;
    }

    // Setter methods for the license number and specialization fields

    public void setLicenceNo(int licenceNo) {
        this.licenceNo = licenceNo;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    // Getter methods for the license number and specialization fields

    public int getLicenceNo() {
        return licenceNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }
}
