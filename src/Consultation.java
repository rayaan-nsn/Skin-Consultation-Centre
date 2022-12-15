import java.util.Date;

public class Consultation {

    // Fields for the consultation date and time, cost, and notes
    private Date date;
    private double cost;
    private String notes;


    public Consultation(Date date, double cost, String notes){
        this.date = date;
        this.cost = cost;
        this.notes = notes;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public String getNotes() {
        return notes;
    }
}
