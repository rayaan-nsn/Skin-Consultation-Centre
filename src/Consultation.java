import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Consultation {

    // Fields for the consultation date and time, cost, and notes
    private LocalDate date;

    private LocalTime time;
    private double cost;
    private String notes;
    private Doctor doctor;
    private Patient patient;


    public Consultation(Doctor doctor, Patient patient, LocalDate date, LocalTime time, double cost, String notes){
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.notes = notes;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime(){
        return time;
    }



    public double getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }
}
