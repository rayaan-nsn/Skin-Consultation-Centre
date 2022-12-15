import javax.print.Doc;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    static ArrayList<Doctor> doctorList = new ArrayList<>();
    static ArrayList<Consultation> consultationList = new ArrayList<>();

    public static void main(String[] args) {
        doctorList.add(new Doctor("John", "Doe", 19800101, 123456, 12345, "Pediatrics"));
        doctorList.add(new Doctor("Jane", "Smith", 19700202, 234567, 23456, "Surgery"));
        doctorList.add(new Doctor("Robert", "Johnson", 19600303, 34567, 34567, "Orthopedics"));
        doctorList.add(new Doctor("Mary", "Williams", 19500404, 456789, 45678, "Cardiology"));
        doctorList.add(new Doctor("David", "Brown", 19400505, 567890, 56789, "Oncology"));

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
        int docLicenceNo = scanner.nextInt();
        //doctorList.removeIf(doctor -> doctor.getLicenceNo() == docLicenceNo);
        for (int i = 0; i <= doctorList.size(); i++) {
            if (doctorList.get(i).getLicenceNo() == docLicenceNo) {
                Doctor deletedDocCopy = doctorList.get(i);  //Get a copy of doctor element
                doctorList.remove(i);
                System.out.println("Successfully Removed Dr."+ deletedDocCopy.getName() +" " + deletedDocCopy.getSurname());
                System.out.println(doctorList.size() + " doctors available");
                break;
            }
        }
    }

    static void printDocList(){
        ArrayList<Doctor> doctorListCopy = new ArrayList<>(doctorList);
        System.out.println("    <---    Doctor List    --->    ");
        for (Doctor doctor : doctorList){
            System.out.println(
                "Name           : " + doctor.getName() + '\n' +
                "Surname        : " + doctor.getSurname() + '\n' +
                "Date of Birth  : " + doctor.getDateOfBirth() + '\n' +
                "Phone No       : " + doctor.getMobilNo() + '\n' +
                "Licence No     : " + doctor.getLicenceNo() + '\n' +
                "Specialisation : " + doctor.getSpecialisation()  + '\n' +
                "____________________________________"
            );

//            for (int i=0; i<doctorList.size(); i++){
//                ArrayList<Doctor> Passen = allQueues[i].getQueue();  //copy of Queue
//                ArrayList<String> customerNames = new ArrayList<>();
//                for (Doctor p : Passen) {
//                    customerNames.add(p.getSurname());
//                }
//                customerNames.sort(String.CASE_INSENSITIVE_ORDER);
//                System.out.println("Pump "+ (i+1) + " queue in alphabetical order = " + customerNames);
//            }
        }

    }
    static void saveInfo(){}
    static void readInfo(){}





}
