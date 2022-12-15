import javax.print.Doc;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    static ArrayList<Doctor> doctorList = new ArrayList<>();
    static ArrayList<Consultation> consultationList = new ArrayList<>();

    public static void main(String[] args) {
            displayMenu();
    }

    static void displayMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                --------------------------------------------------------------
                    Westminster Skin Consultation Centre Management System
                --------------------------------------------------------------
                Please select an option from the menu below:
                    1. Add a new doctor
                    2. Delete a doctor
                    3. Print the list of doctors
                    4. Save the information entered so far
                    5. Read the information from the file
                    0. Exit the system
                --------------------------------------------------------------
                """);
        while (true) {
            System.out.print("Enter option : ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> addDoctor();
                    case 2 -> removeDoctor();
                    case 3 -> printDocList();
                    case 4 -> saveInfo();
                    case 5 -> readInfo();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid selection, Try again...");
                }
        }
    }

    static void addDoctor(){

        if (doctorList.size()<=10) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter doctor name : ");
            String docName = scanner.next();
            System.out.print("Enter doctor surname : ");
            String docSurName = scanner.next();
            System.out.print("Enter doctor DOB : ");
            int docDOB = scanner.nextInt();
            System.out.print("Enter doctor Mobile Number : ");
            int docPNo = scanner.nextInt();
            System.out.print("Enter doctor License Number : ");
            int docLicenseNo = scanner.nextInt();
            System.out.print("Enter doctor specialisation : ");
            String docSpecialisation = scanner.next();

            doctorList.add(new Doctor(docName, docSurName, docDOB, docPNo, docLicenseNo, docSpecialisation));
        } else {
            System.out.println("Doctor List is full");
        }

//        doctorList.add(Doctor doctor);
    }
    static void removeDoctor(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor licence No to remove : ");
        String docLicenceNo = scanner.next();
        doctorList.remove(doctorList.indexOf(docLicenceNo));
    }
    static void printDocList(){
        for (int i=0; i<=doctorList.size(); i++){
            System.out.println(doctorList.get(i));
        }
        
    }
    static void saveInfo(){}
    static void readInfo(){}





}
