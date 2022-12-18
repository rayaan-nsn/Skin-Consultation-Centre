import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    static ArrayList<Doctor> doctorList = new ArrayList<>();
    static ArrayList<Consultation> consultationList = new ArrayList<>();

    public static void main(String[] args) {

        //Temporary doctors for testing
//        doctorList.add(new Doctor("John", "Doe", 19800101, 123456, 12345, "Pediatrics"));
//        doctorList.add(new Doctor("Jane", "Anderson", 19700202, 234567, 23456, "Surgery"));
//        doctorList.add(new Doctor("Robert", "Johnson", 19600303, 34567, 34567, "Orthopedics"));
//        doctorList.add(new Doctor("Mary", "Williams", 19500404, 456789, 45678, "Cardiology"));
//        doctorList.add(new Doctor("David", "Brown", 19400505, 567890, 56789, "Oncology"));
        //Temporary doctors for testing
readInfo();
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
                    3. Add a consultation
                    4. Cancel a consultation
                    5. Print the list of doctors
                    6. Save the information entered so far
                    7. Read the information from the file
                    0. Exit the system
                --------------------------------------------------------------
                """);
        while (true) {
            System.out.print("Enter option : ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> addDoctor();
                    case 2 -> removeDoctor();
                    //case 3 -> addConsultation();
                    //case 4 -> cancelConsultation();
                    case 5 -> printDocList();
                    case 6 -> saveInfo();
                    case 7 -> readInfo();
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
                }
                break;
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("No such doctor");
        }
    }

    static void printDocList(){
//        doctorList.sort(new Comparator<Doctor>() {
//            @Override
//            public int compare(Doctor d1, Doctor d2) {
//                return d1.getSurname().compareTo(d2.getSurname());
//            }
//        });
        doctorList.sort(Comparator.comparing(Doctor::getSurname));
        System.out.println("    <---    Doctor List    --->    ");
        for (Doctor doctor : doctorList){
            System.out.println(
                "Name           : " + doctor.getName() + '\n' +
                "Surname        : " + doctor.getSurname() + '\n' +
                "Date of Birth  : " + doctor.getDateOfBirth() + '\n' +
                "Phone No       : " + doctor.getMobilNo() + '\n' +
                "Licence No     : " + doctor.getLicenceNo() + '\n' +
                "Specialisation : " + doctor.getSpecialisation()  + '\n' +
                "____________________________________");
        }
    }
    static void saveInfo(){
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

//        //write to file
//        try {
//            FileOutputStream fos = new FileOutputStream("output");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(doctorList); // write MenuArray to ObjectOutputStream
//            oos.close();
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
        }

    static void readInfo(){
//        ArrayList<String> list = null;

        try (FileInputStream fis = new FileInputStream("list.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            doctorList = (ArrayList<Doctor>)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doctorList != null) {
            for (Doctor s : doctorList) {
                System.out.println(s);
            }
        }
    }


}
