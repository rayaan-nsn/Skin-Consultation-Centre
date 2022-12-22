import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Person implements Serializable {   //Super Class
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private int mobilNo;

    public Person(String name, String surname, LocalDate dateOfBirth, int mobilNo){
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mobilNo = mobilNo;
    }



    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getMobilNo() {
        return mobilNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setMobilNo(int mobilNo) {
        this.mobilNo = mobilNo;
    }
}
