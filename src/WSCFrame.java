import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class WSCFrame extends JFrame{

    WSCFrame(){
        //Frame 2
        JFrame patientDetails = new JFrame("Westminster Skin Care Centre");
        patientDetailFrame(patientDetails);
        frameProperties(patientDetails);
        patientDetails.setVisible(true); // make the frame invisible until button click event

        //Frame 1
        JFrame docTableFrame = new JFrame("Westminster Skin Care Centre");
//        frameProperties(docTableFrame);
        docTableFrameFunctions(docTableFrame, patientDetails);
        docTableFrame.setVisible(false); // make the frame visible --------------------------------------Make this true at the end

    }


    void frameProperties(JFrame frame){
        frame.setSize(500,500); // set the size of the frame
        frame.setLocationRelativeTo(null);  // center the frame on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        //frame.pack();   // Pack the frame to fit its contents
    }

    void patientDetailFrame(JFrame frame2){



        // Create a panel with a GridLayout to hold the label and text field
        JPanel patientDetailsPanel = new JPanel(new GridLayout(4,2));
        patientDetailsPanel.setBounds(0,50,500,150);
        patientDetailsPanel.setBackground(Color.cyan);

        JLabel topic = new JLabel("Patient details");

        JLabel end = new JLabel("end");

        // Name label and text-field
        JLabel patientNameLabel = new JLabel("Enter your name:");
        patientDetailsPanel.add(patientNameLabel);
        JTextField inputPatientName = new JTextField(30);
        patientDetailsPanel.add(inputPatientName);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));   //Newline

        // Surname label and text-field
        JLabel patientSurNameLabel = new JLabel("Enter your surname:");
        patientDetailsPanel.add(patientSurNameLabel);
        JTextField inputPatientSurName = new JTextField(30);
        patientDetailsPanel.add(inputPatientSurName);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));   //Newline

        //DOB label and text-field
        JLabel patientDOB = new JLabel("Select DOB");
        patientDetailsPanel.add(patientDOB);
        DatePicker pickPatientDOB = new DatePicker();
        patientDetailsPanel.add(pickPatientDOB);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel patientMobNo = new JLabel("Enter mobile no.");
        patientDetailsPanel.add(patientMobNo);
        JSpinner inputPatientMobNo = new JSpinner();
        patientDetailsPanel.add(inputPatientMobNo);

        //Automatic create a unique patientID using the ArrayList index
        String patientID = "ID-" + WestminsterSkinConsultationManager.patientList.size()+1;

        int tempInt = 1;

        JButton submitPatientDetails = new JButton("Submit Details");
        submitPatientDetails.setPreferredSize(new Dimension(100, 50));
        submitPatientDetails.addActionListener(e -> {
            WestminsterSkinConsultationManager.patientList.add(new Patient(inputPatientName.getText(),inputPatientSurName.getText(),tempInt,(int) inputPatientMobNo.getValue(),patientID));
            
                });












        frame2.add(patientDetailsPanel);
        frame2.add(submitPatientDetails, BorderLayout.SOUTH);
//        frame2.add(topic, BorderLayout.NORTH);
        frame2.add(end);


    }

    public void docTableFrameFunctions(JFrame frame1, JFrame frame2){
        String[] columnNames = {"Name", "Surname", "DOB","Mobile No","License No","Specialisation"};

        DefaultTableModel dtm = new DefaultTableModel(columnNames, 1);
        for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
            dtm.addRow(new String[]{
                    d.getName(),
                    d.getSurname(),
                    String.valueOf(d.getDateOfBirth()),
                    String.valueOf(d.getMobilNo()),
                    String.valueOf(d.getLicenceNo()),
                    d.getSpecialisation()});
        }
        JTable docTable = new JTable(dtm);
        docTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow the user to only select a single row at a time

        DatePicker datePicker = new DatePicker();
        TimePicker timePicker = new TimePicker();

        JPanel dateTimePanel = new JPanel();
        dateTimePanel.setBounds(0,250,500,100);
        dateTimePanel.setBackground(Color.RED);
        dateTimePanel.add(datePicker);  //Pick the consultation date
        dateTimePanel.add(timePicker);  //Pick the consultation time
        dateTimePanel.setVisible(false);

        JButton selectDocBtn = new JButton("Select Doctors");
        selectDocBtn.setBounds(150,100,200,50);
        selectDocBtn.addActionListener(e -> {
            int index = docTable.getSelectedRow();
            Object selectedCellValue = docTable.getValueAt(index, 4);    //Get the value of 5 th column
            for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
                if (String.valueOf(d.getLicenceNo()).equals(String.valueOf(selectedCellValue))) {
                    System.out.println(d.getName());
                    break;
                }
            }
            dateTimePanel.setVisible(true);
            ;});


        JButton consultDocBtn = new JButton("Consult Doctors");
        consultDocBtn.setPreferredSize(new Dimension(100, 50));
        consultDocBtn.addActionListener(e -> {
            LocalDate selectedDate = datePicker.getDate();
            LocalTime selectedTime = timePicker.getTime();
            System.out.println(selectedDate);
            System.out.println(selectedTime);
            frame1.setVisible(false);
            frame2.setVisible(true);
        });


        frame1.add(dateTimePanel);
        frame1.add(selectDocBtn);
        frame1.add(consultDocBtn, BorderLayout.SOUTH);
        frame1.add(docTable);

    }
}


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
