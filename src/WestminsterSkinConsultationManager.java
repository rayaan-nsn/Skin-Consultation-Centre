import javax.print.Doc;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    static ArrayList<Doctor> doctorList = new ArrayList<>();
    static ArrayList<Consultation> consultationList = new ArrayList<>();

    public static void main(String[] args) {

        //Temporary doctors for testing
        doctorList.add(new Doctor("John", "Doe", 19800101, 123456, 12345, "Pediatrics"));
        doctorList.add(new Doctor("Jane", "Anderson", 19700202, 234567, 23456, "Surgery"));
        doctorList.add(new Doctor("Robert", "Johnson", 19600303, 34567, 34567, "Orthopedics"));
        doctorList.add(new Doctor("Mary", "Williams", 19500404, 456789, 45678, "Cardiology"));
        doctorList.add(new Doctor("David", "Brown", 19400505, 567890, 56789, "Oncology"));
        //Temporary doctors for testing
        //readInfo();
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
                    4. Save the information
                    5. Read the info
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
                    case 10 -> openGUI();
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
    }

    static void removeDoctor(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter doctor licence No to remove : ");
        int docLicenceNo = scanner.nextInt();
        try {
            //doctorList.removeIf(doctor -> doctor.getLicenceNo() == docLicenceNo);
            for (int i = 0; i <= doctorList.size(); i++) {
                if (doctorList.get(i).getLicenceNo() == docLicenceNo) {
                    Doctor deletedDocCopy = doctorList.get(i);  //Get a copy of doctor element
                    doctorList.remove(i);
                    System.out.println("Successfully Removed Dr." + deletedDocCopy.getName() + " " + deletedDocCopy.getSurname());
                    System.out.println(doctorList.size() + " doctors available");
                } else {
                    System.out.println("Licence No. not found");
                    break;
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("No such doctor");
        }
    }

    static void printDocList(){
        ArrayList<Doctor> doctorListCopy = (ArrayList<Doctor>) doctorList.clone();
        doctorListCopy.sort(Comparator.comparing(Doctor::getSurname));
        System.out.println("Name ---- Surname ---- DOB -------- Mobile ----- Licence ---- Specialisation");
        for (Doctor doc : doctorListCopy){
            System.out.printf("%-10s",doc.getName());
            System.out.printf("%-13s",doc.getSurname());
            System.out.printf("%-13d",doc.getDateOfBirth());
            System.out.printf("%-13d",doc.getMobilNo());
            System.out.printf("%-13d",doc.getLicenceNo());
            System.out.printf("%-13s \n",doc.getSpecialisation() );
        }
    }
    static void saveInfo() {
        try {
            // Create a FileOutputStream to write the object to a file
            FileOutputStream fos = new FileOutputStream("list.txt");

            // Create an ObjectOutputStream to write the object to the FileOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Write the ArrayList object to the file
            oos.writeObject(doctorList);
            System.out.println("Saved successfully");

            // Close the ObjectOutputStream and FileOutputStream
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

public static void readInfo() {
    try {
        FileInputStream fis = new FileInputStream("list.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        doctorList = (ArrayList<Doctor>) ois.readObject();
        ois.close();
        fis.close();
    } catch (IOException |ClassCastException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

/* <<< ------------------------------ GUI (Phase 3) ------------------------------ >>> */

    /*
To implement the GUI for a doctor-patient consultation software according to the requests specified above, you can follow these steps:

1.Create a table to display the list of doctors with their name, age, and other relevant information.
You can use the JTable component provided by the Java Swing library to create the table.

2.Implement a sorting function to allow the user to sort the list of doctors alphabetically.
You can use the Collections.sort method to sort the list of doctors, and then update the table with the sorted list.

3.Add a button or other UI element that allows the user to select a doctor and book a consultation.
When the user clicks this button, you can display a form that allows the user to enter the patient's information,
such as name, surname, date of birth, and mobile number.

4.Implement a function to check the availability of the doctor at the chosen date and time.
If the doctor is not available, you can use the Collections.shuffle method to randomly select an available doctor
from the list of all available doctors.

5.Implement a function to calculate the cost of the consultation based on the length of the consultation and
the rate specified in the request. You can use this function to display the cost to the user and
allow them to confirm the consultation.

6.Implement a function to encrypt the notes entered by the user using a suitable encryption algorithm.
You can use an available API such as the Java Cryptography Extension (JCE) to handle the encryption.

7.Implement a way for the user to view the stored information for a consultation, including the patient info,
cost, and encrypted notes. This can be done by displaying the information in a form or a separate window.
    */

public static void openGUI(){
        JFrame frame = new JFrame("Westminster Skin Care Centre");
        frame.setSize(500,500);
        frame.setVisible(true);
}



}
