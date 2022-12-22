import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Doctor extends Person implements Serializable {  //Subclass of person

    // Fields for the doctor's medical license number and specialization
    private String licenceNo;
    private String specialisation;

    public Doctor(String name, String surname, LocalDate dateOfBirth, int mobilNo, String licenceNo, String specialisation) {
        super(name, surname, dateOfBirth, mobilNo);
        this.specialisation = specialisation;
        this.licenceNo = licenceNo;
    }

    // Setter methods for the license number and specialization fields

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    // Getter methods for the license number and specialization fields

    public String getLicenceNo() {
        return licenceNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }
}
