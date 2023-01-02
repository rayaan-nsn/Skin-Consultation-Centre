import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

// Read the saved file first
// Handle public interface SkinConsultationManager
// If its first time or second time, calculate according to that
// Need to get patient NIC no. Not create patient ID
// Encrypt patient details
// Able to go through consultation hours and show not available
// Show consultation List

public class ConsultationGUI extends JFrame {

    static int cost;
    static LocalDate date;
    static LocalTime time;
    static Doctor assignedDoc;


    ConsultationGUI(){

        JFrame consultationDetailsFrame = new JFrame("Westminster Skin Care Centre");
        consultationDetailsFrame.setSize(700,600); // set the size of the frame
        consultationDetailsFrame.setLocationRelativeTo(null);  // center the frame on the screen
        consultationDetailsFrame.setResizable(false);
        consultationDetailsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        //viewConsultationDetails(consultationDetailsFrame);
        consultationDetailsFrame.setVisible(false);

        //Frame 3
        JFrame finalFrame = new JFrame("Westminster Skin Care Centre");
        frameProperties(finalFrame);
        viewPatientDetails(finalFrame, consultationDetailsFrame);
        finalFrame.setVisible(false);

        //Frame 2
        JFrame patientDetailsFrame = new JFrame("Westminster Skin Care Centre");
        frameProperties(patientDetailsFrame);
        patientDetails(patientDetailsFrame, finalFrame);
        patientDetailsFrame.setVisible(false); // make the frame invisible until button click event

        //Frame 1
        JFrame docCheckFrame = new JFrame("Westminster Skin Care Centre");
        frameProperties(docCheckFrame);
        docAvailable(docCheckFrame,patientDetailsFrame);
        docCheckFrame.setVisible(true);
    }
    void frameProperties(JFrame frame){
        frame.setSize(500,600); // set the size of the frame
        frame.setLocationRelativeTo(null);  // center the frame on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        //frame.pack();   // Pack the frame to fit its contents

        JPanel topPanel = new JPanel();
        //topPanel.setBackground(Color.GREEN);
        topPanel.setBounds(0,0,500,70);

        JLabel topic = new JLabel();
        topic.setText("Westminster Skin Care Center");

        topPanel.add(topic);
        frame.add(topPanel);
    }

    void docAvailable(JFrame frame1, JFrame frame2){
        /* <<<--- Top Pane is in frame properties --->>> */

//        Image image = new ImageIcon("logo.svg").getImage();
//
//        JLabel label = new JLabel();
//        label.setIcon(new ImageIcon(image));
//
//        label.setPreferredSize(new Dimension(500, 50));
//
//        topPanel.add(label);

        /* <<<--- Doctor Selection Pane --->>> */
        JPanel docTablePanel = new JPanel();
        docTablePanel.setBounds(0,70,500,130);
        //docTablePanel.setBackground(Color.LIGHT_GRAY);

        String[] columnNames = {"Name", "Surname","Mobile No","Licence No","Specialisation"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        for (Doctor d : WestminsterSkinConsultationManager.doctorList) {    // for(i=0; i<WestminsterSkinConsultationManager.doctorList.size(); i++)
            dtm.addRow(new String[]{
                    d.getName(),
                    d.getSurname(),
                    String.valueOf(d.getMobilNo()),
                    String.valueOf(d.getLicenceNo()),
                    d.getSpecialisation()});
        }

        JTable docTable = new JTable(dtm);

        TableRowSorter<DefaultTableModel> myTRS = new TableRowSorter<>(dtm);
        docTable.setRowSorter(myTRS);   //Sort the table

        docTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow to select a single row at a time
        JScrollPane scrollPane = new JScrollPane(docTable); //Make table scrollable
        scrollPane.setPreferredSize(new Dimension(480, 150));
        docTablePanel.add(scrollPane);

        frame1.add(docTablePanel);

        /* <<<--- Date Selection Pane --->>> */

        JPanel consultDatePanel = new JPanel(new GridLayout(5,2));
        //consultDatePanel.setBackground(Color.ORANGE);
        consultDatePanel.setBounds(50,220,400,110);

        JLabel pickConsultDate = new JLabel("Consultation Date *");
        consultDatePanel.add(pickConsultDate);
        DatePicker consultationDate = new DatePicker();
//        consultationDate.setPreferredSize(new Dimension(50, 50));
        consultDatePanel.add(consultationDate);

        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline
        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel pickConsultTime = new JLabel("Consultation Time *");
        consultDatePanel.add(pickConsultTime);
        TimePicker consultTime = new TimePicker();
        consultDatePanel.add(consultTime);

        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline
        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel pickHours = new JLabel("Consultation Hours *");
        consultDatePanel.add(pickHours);

        SpinnerModel model = new SpinnerNumberModel(1, 1, 5, 1);
        JSpinner consultHours = new JSpinner(model);
//        consultHours.setPreferredSize(new Dimension(100, 50));
        consultDatePanel.add(consultHours);

        frame1.add(consultDatePanel);

        /* <<<--- Warning Panel --->>> */
        JPanel warningPanel = new JPanel();
        //warningPanel.setBackground(Color.cyan);
        warningPanel.setBounds(0,390,500,20);

        JLabel warning = new JLabel();
        warning.setForeground(Color.RED);
        warningPanel.add(warning);

        frame1.add(warningPanel);


        /* <<<--- Availability status Panel --->>> */

        JPanel docStatusPanel = new JPanel(new FlowLayout());
        docStatusPanel.setLayout(new BoxLayout(docStatusPanel, BoxLayout.Y_AXIS));
        docStatusPanel.setBounds(110,440,500,65);
        //docStatusPanel.setBackground(Color.YELLOW);
        docStatusPanel.setVisible(false);

        JLabel finalDoc = new JLabel();
        finalDoc.setFont(new Font(finalDoc.getFont().getName(), finalDoc.getFont().getStyle(), 18));
        docStatusPanel.add(finalDoc);

        docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline
        //docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel setDoc = new JLabel();
        setDoc.setFont(new Font(finalDoc.getFont().getName(), finalDoc.getFont().getStyle(), 15));
        docStatusPanel.add(setDoc);

        //docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline

        //docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline
        //docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline


        //JLabel showCost = new JLabel();
        //docStatusPanel.add(showCost);

        frame1.add(docStatusPanel);

        /* <<<--- Assigning Doc --->>> */
        JPanel assigningDocPanel = new JPanel();
        assigningDocPanel.setBounds(0,460,500,30);


        /* <<<--- Final Button --->>> */
        JButton confirmDocBtn = new JButton();
        confirmDocBtn.setPreferredSize(new Dimension(100, 50));
        confirmDocBtn.setVisible(false);
        confirmDocBtn.addActionListener(e -> frame2.setVisible(true));
        frame1.add(confirmDocBtn, BorderLayout.SOUTH);

        /* <<<--- Consultation Date and Hours --->>> */

        JPanel docSubmitPanel = new JPanel();
        docSubmitPanel.setBounds(0,350,500,40);
        //docSubmitPanel.setBackground(Color.RED);

        JButton checkDocBtn = new JButton("Check availability");
        checkDocBtn.setPreferredSize(new Dimension(150, 35));
        checkDocBtn.addActionListener(e ->{

//            docStatusPanel.setVisible(true);
//            confirmDocBtn.setVisible(true);

            //calcCost(consultHours, showCost);
            date = consultationDate.getDate();  //Assign date after button clicked
            time = consultTime.getTime();   //Assign time after button clicked

            int index = docTable.getSelectedRow();



            if (index == 0 || date == null || time == null){
                warning.setText("Fill all the required fields that are marked with * ");
                //warningPanel.add(warning);
                //warning.setVisible(true);

            }
            else {
                Object selectedCellValue = docTable.getValueAt(index, 3);    //Get the value of 4 th column
                //Table selected doctor object
                for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
                    if (String.valueOf(d.getLicenceNo()).equals(String.valueOf(selectedCellValue))) {
                        for (Consultation c : WestminsterSkinConsultationManager.consultationList){
                            if (c.getDoctor().equals(d) && date.equals(c.getDate()) && time.equals(c.getTime())) {
                                // Loop through the doctorList and check if each doctor is available at the selected date and time
                                ArrayList<Doctor> availableDoc = new ArrayList<>();
                                for (Doctor ad : WestminsterSkinConsultationManager.doctorList){
                                    boolean isAvailable = true;
                                    for (Consultation ac : WestminsterSkinConsultationManager.consultationList){
                                        if (ac.getDoctor().equals(ad) && date.equals(ac.getDate()) && time.equals(ac.getTime())){
                                            isAvailable = false;
                                            break;
                                        }
                                    }
                                    if (isAvailable){
                                        availableDoc.add(ad);
                                    }
                                }
                                if (availableDoc.isEmpty()){    // If there are no available doctors, print a message and exit the method
                                    finalDoc.setText("No doc available at the moment");
                                    finalDoc.setForeground(Color.RED);
                                    //docStatusPanel.add(finalDoc);
                                    return;
                                }
                                finalDoc.setText("Dr." + c.getDoctor().getName() + " " + c.getDoctor().getSurname() + " is not available");
                                finalDoc.setForeground(Color.RED);
                                Collections.shuffle(availableDoc);  // Shuffle available doctors and select a random doctor
                                assignedDoc = availableDoc.get(0);
                                setDoc.setText("     Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname() + " is assigned" );
                                //docStatusPanel.add(setDoc);

                                //System.out.println("Dr."+ d.getName() + " " + d.getSurname() + " is available");
                                break;
                            }
                            assignedDoc = d;
                            finalDoc.setText("Dr."+ assignedDoc.getName() + " " + assignedDoc.getSurname() + " is available");
                            finalDoc.setForeground(Color.GREEN);
                            //docStatusPanel.add(finalDoc);
                            break;
                        }
                        break;
                    }
                }
            docStatusPanel.setVisible(true);
            confirmDocBtn.setVisible(true);
            warning.setVisible(false);
            confirmDocBtn.setText("Consult Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname());
            //System.out.println("Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname() );
            //Disable selection options to prevent edit
            disableOptions(consultationDate,consultHours,docTable,consultTime,checkDocBtn);
            }
        });
        docSubmitPanel.add(checkDocBtn);

        frame1.add(docSubmitPanel);


        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        //freePanel.setBackground(Color.pink);
        frame1.add(freePanel);

    }
    private void calcCost(JSpinner hours, JLabel costLabel) {
        Object value = hours.getValue();
//        System.out.println(value);
        int intValue = (int) value;
        if (intValue != 1){
            cost = 25 + (intValue-1)*15;
        }else {
            cost = 15;
        }
        costLabel.setText("Consultation Cost : £" + cost);

    }
    private void disableOptions(DatePicker date, JSpinner hours, JTable table, TimePicker time, JButton docBtn) {
        date.setEnabled(false);
        hours.setEnabled(false);
        table.setEnabled(false);
        time.setEnabled(false);
        docBtn.setEnabled(false);
    }

    public static void patientDetails(JFrame frame2, JFrame frame3){
        /* <<<--- Top Pane is in frame properties --->>> */

        /* <<<--- Enter patient details --->>> */
        JPanel patientDetailsPanel = new JPanel(new GridLayout(9,2));
        //patientDetailsPanel.setBackground(Color.YELLOW);
        patientDetailsPanel.setBounds(75,80,350,250);

        // Name label and text-field
        JLabel patientNameLabel = new JLabel("Patient Name *");
        patientDetailsPanel.add(patientNameLabel);
        JTextField getPatientName = new JTextField(20);
        patientDetailsPanel.add(getPatientName);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));
        patientDetailsPanel.add(new JLabel("<html><br></html>"));

        // Surname label and text-field
        JLabel patientSurNameLabel = new JLabel("Patient Surname *");
        patientDetailsPanel.add(patientSurNameLabel);
        JTextField getPatientSurName = new JTextField(20);
        patientDetailsPanel.add(getPatientSurName);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));
        patientDetailsPanel.add(new JLabel("<html><br></html>"));

        //DOB label and text-field
        JLabel patientDOB = new JLabel("Patient DOB *");
        patientDetailsPanel.add(patientDOB);
        DatePicker pickPatientDOB = new DatePicker();
        patientDetailsPanel.add(pickPatientDOB);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));
        patientDetailsPanel.add(new JLabel("<html><br></html>"));

        //Mobile no. and text-field
        JLabel patientMobNo = new JLabel("Patient mobile No.");
        patientDetailsPanel.add(patientMobNo);
        JSpinner getPatientMobNo = new JSpinner();
        patientDetailsPanel.add(getPatientMobNo);

        patientDetailsPanel.add(new JLabel("<html><br></html>"));
        patientDetailsPanel.add(new JLabel("<html><br></html>"));

        //Automatic create a unique patientID using the ArrayList index
        int patientIDNo = WestminsterSkinConsultationManager.patientList.size() + 1;
        String patientID = "ID-" + patientIDNo;

        // Create a JLabel
        JLabel label = new JLabel("Upload an image");
        patientDetailsPanel.add(label);
        //label.setBounds(0, 0, 400, 280);

        // Create a JButton
        JButton button = new JButton("Choose Image");
        patientDetailsPanel.add(button);
        //button.setBounds(0, 280, 400, 20);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();

                // Show the file chooser and get the user's selection
                int result = fileChooser.showOpenDialog(frame2);
            }
        });
        frame2.add(patientDetailsPanel);

        /* <<<--- Add extra patient details --->>> */
        JPanel extraDetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //extraDetPanel.setBackground(Color.cyan);
        extraDetPanel.setBounds(70,340,380,110);

        JLabel patientNotes = new JLabel("Add notes");
        extraDetPanel.add(patientNotes);

        JTextArea getPatientNotes = new JTextArea();
        getPatientNotes.setLineWrap(true);    //automatic break lines
        getPatientNotes.setWrapStyleWord(true);
        //getPatientNotes.setBackground(Color.LIGHT_GRAY);
        getPatientNotes.setPreferredSize(new Dimension(350, 80));
        getPatientNotes.setBorder(BorderFactory.createLoweredBevelBorder());
        extraDetPanel.add(getPatientNotes);

        frame2.add(extraDetPanel);

        /* <<<--- Warning Panel --->>> */
        JPanel warningPanel = new JPanel();
        warningPanel.setBounds(50,490,400,25);
        JLabel warning = new JLabel();
        warning.setForeground(Color.RED);
        warning.setVisible(false);

        frame2.add(warningPanel);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        //freePanel.setBackground(Color.pink);

        frame2.add(freePanel);


        JButton saveDetailsBtn = new JButton("Save Details");
        saveDetailsBtn.setPreferredSize(new Dimension(100, 50));
        saveDetailsBtn.addActionListener(e->{

            LocalDate dDate = pickPatientDOB.getDate();

            if (getPatientName.getText().isEmpty() || getPatientSurName.getText().isEmpty() || dDate == null){
                warning.setText("Fill all the required fields that are marked with * ");
                warningPanel.add(warning);
                warning.setVisible(true);
            }else {
                Patient patient = new Patient(getPatientName.getText(), getPatientSurName.getText(),
                        pickPatientDOB.getDate(), ((Number) getPatientMobNo.getValue()).intValue(), patientID);    //Create a new patient
                WestminsterSkinConsultationManager.patientList.add(patient);    //Assign the new patient in to the patient-list array
                WestminsterSkinConsultationManager.consultationList.add(new Consultation(
                        assignedDoc, patient, date, time, cost, getPatientNotes.getText())); //Add consultation

                frame3.setVisible(true);
            }
        });
        frame2.add(saveDetailsBtn, BorderLayout.SOUTH);

    }

    public static void viewPatientDetails(JFrame frame3, JFrame frame4){

        /* <<<--- Display success --->>> */
        JPanel successPanel = new JPanel();
        successPanel.setBounds(50,70,400,100);
        successPanel.setBackground(Color.cyan);

        JLabel successNoteLabel = new JLabel("Consultation added Successfully");
        successNoteLabel.setForeground(Color.GREEN);
        successNoteLabel.setFont(successNoteLabel.getFont().deriveFont(20.0f));
        successPanel.add(successNoteLabel);

        successPanel.add(new JLabel("<html><br><br><br></html>"));

        JButton viewDetails = new JButton("View Details");
        viewDetails.setPreferredSize(new Dimension(170, 40));
        successPanel.add(viewDetails);
        viewDetails.addActionListener(e ->{
            viewConsultationDetails(frame4);
            frame4.setVisible(true);
        });

        frame3.add(successPanel);

        /* <<<--- Show Details Panel --->>> */

        JPanel detailsShow = new JPanel();
        detailsShow.setBackground(Color.YELLOW);
        detailsShow.setBounds(0,170,500,250);

        //Patient p = WestminsterSkinConsultationManager.consultationList.get(WestminsterSkinConsultationManager.consultationList.size()).getPatient();

        JLabel patientName = new JLabel();


        frame3.add(detailsShow);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        freePanel.setBackground(Color.pink);

        frame3.add(freePanel);
    }


    public static void viewConsultationDetails(JFrame frame4){
        JPanel consultationTablePanel = new JPanel();
        consultationTablePanel.setBounds(0,70,700,200);
        consultationTablePanel.setBackground(Color.LIGHT_GRAY);

        String[] columnNames = {"Patient ID", "Patient's Name","Doctor", "Date", "Time", "Cost (£)","Patient DOB", "Notes"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        for (Consultation c : WestminsterSkinConsultationManager.consultationList) {    // for(i=0; i<WestminsterSkinConsultationManager.doctorList.size(); i++)
            dtm.addRow(new String[]{
                    c.getPatient().getPatientID(),
                    c.getPatient().getName() + " " + c.getPatient().getSurname(),
                    c.getDoctor().getName() + " " + c.getDoctor().getSurname(),
                    String.valueOf(c.getDate()),
                    String.valueOf(c.getTime()),
                    String.valueOf(c.getCost()),
                    String.valueOf(c.getPatient().getDateOfBirth()),
                    c.getNotes()
            });
        }

        JTable consultationDetailsTable = new JTable(dtm);
        consultationDetailsTable.setRowHeight(30);  //set the row height
        consultationDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(35);  //column width
        consultationDetailsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        consultationDetailsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        consultationDetailsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        consultationDetailsTable.getColumnModel().getColumn(4).setPreferredWidth(40);
        consultationDetailsTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        consultationDetailsTable.getColumnModel().getColumn(6).setPreferredWidth(80);

        TableRowSorter<DefaultTableModel> myTRS = new TableRowSorter<>(dtm);
        consultationDetailsTable.setRowSorter(myTRS);   //Sort the table

        consultationDetailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow to select a single row at a time

        JScrollPane scrollPane = new JScrollPane(consultationDetailsTable); //Make table scrollable
        scrollPane.setPreferredSize(new Dimension(650, 190));

        consultationTablePanel.add(scrollPane);

        frame4.add(consultationTablePanel);


        //JTable docTable = new JTable(dtm);

        //TableRowSorter<DefaultTableModel> myTRS = new TableRowSorter<>(dtm);
        //docTable.setRowSorter(myTRS);   //Sort the table

        //docTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow to select a single row at a time
        //JScrollPane scrollPane = new JScrollPane(docTable); //Make table scrollable
        //scrollPane.setPreferredSize(new Dimension(480, 150));
        //docTablePanel.add(scrollPane);

        //frame1.add(docTablePanel);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        //freePanel.setBackground(Color.pink);
        frame4.add(freePanel);

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