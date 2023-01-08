import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;

// Read the saved file first
// Select doctor according specialization
// Consultation List editable

public class ConsultationGUI extends JFrame {

    static int cost;
    static LocalDate date;
    static LocalTime startTime;
    static int hours;
    static Doctor assignedDoc;
    static Key key;
    static String encryptedNote;
    static String decryptedString;


    ConsultationGUI(){

        JFrame consultationDetailsFrame = new JFrame();
        frameProperties(consultationDetailsFrame);
        consultationDetailsFrame.setVisible(false);

        //Frame 3
        JFrame statusNCostFrame = new JFrame();
        frameProperties(statusNCostFrame);
        statusNCostFrame.setVisible(false);

        //Frame 2
        JFrame patientDetailsFrame = new JFrame();
        frameProperties(patientDetailsFrame);
        patientDetailsFrame.setVisible(false); // make the frame invisible until button click event

        JFrame openFrame = new JFrame();
        frameProperties(openFrame);

        //Frame 1
        JFrame docCheckFrame = new JFrame();
        frameProperties(docCheckFrame);
        docAvailable(openFrame,docCheckFrame,patientDetailsFrame,statusNCostFrame,consultationDetailsFrame);
        docCheckFrame.setVisible(false);

        //Frame0
//        JFrame openFrame = new JFrame();
//        frameProperties(openFrame);
        openingFrame(openFrame,docCheckFrame,patientDetailsFrame,statusNCostFrame,consultationDetailsFrame);
        openFrame.setVisible(true);


    }
    void frameProperties(JFrame frame){
        frame.setSize(500,600); // set the size of the frame
        frame.setLocationRelativeTo(null);  // center the frame on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        frame.setTitle("Westminster Skin Care Centre");

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0,0,500,30);

        frame.add(topPanel);
    }

    void openingFrame(JFrame frame0, JFrame frame1,JFrame frame2,JFrame frame3, JFrame frame4){

        JPanel openPanel = new JPanel(new GridLayout(3,1));
        openPanel.setBounds(100,150,300,200);

        JButton addConsultationBtn = new JButton("Add Consultation");
        addConsultationBtn.setPreferredSize(new Dimension(100, 80));
        addConsultationBtn.addActionListener(e -> {
            frame1.setVisible(true);
            frame0.setVisible(false);
        });
        openPanel.add(addConsultationBtn);

        openPanel.add(new JLabel("<html><br></html>"));   //Newline

        JButton viewConsultationBtn = new JButton("View Consultation");
        viewConsultationBtn.setPreferredSize(new Dimension(100, 80));

        viewConsultationBtn.addActionListener(e -> {
            viewConsultationDetails(frame0,frame1, frame2, frame3, frame4);
            frame4.setVisible(true);
            frame0.setVisible(false);
        });
        openPanel.add(viewConsultationBtn);

        frame0.add(openPanel);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        frame0.add(freePanel);
    }

    /* <<< ------------------------------ Check Doctor Availability ------------------------------ >>> */
    void docAvailable(JFrame frame0, JFrame frame1,JFrame frame2,JFrame frame3, JFrame frame4){

        /* <<<--- Doctor Selection Pane --->>> */
        JPanel docTablePanel = new JPanel();
        docTablePanel.setBounds(0,70,500,130);

        String[] columnNames = {"Name", "Surname","Mobile No","Licence No","Specialisation"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 1);
        for (Doctor d : WestminsterSkinConsultationManager.doctorList) {    // for(i=0; i<WestminsterSkinConsultationManager.doctorList.size(); i++)
            dtm.addRow(new String[]{
                    d.getName(),
                    d.getSurname(),
                    String.valueOf(d.getMobilNo()),
                    String.valueOf(d.getLicenceNo()),
                    d.getSpecialisation()});
        }

        JTable docTable = new JTable(dtm);
        docTable.setRowHeight(25);  //set the row height


        TableRowSorter<DefaultTableModel> myTRS = new TableRowSorter<>(dtm);
        docTable.setRowSorter(myTRS);   //Sort the table

        docTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow to select a single row at a time
        JScrollPane scrollPane = new JScrollPane(docTable); //Make table scrollable
        scrollPane.setPreferredSize(new Dimension(480, 150));
        docTablePanel.add(scrollPane);

        frame1.add(docTablePanel);

        /* <<<--- Date Selection Pane --->>> */

        JPanel consultDatePanel = new JPanel(new GridLayout(5,2));
        consultDatePanel.setBounds(50,220,400,110);

        JLabel pickConsultDate = new JLabel("Consultation Date *");
        consultDatePanel.add(pickConsultDate);
        DatePicker consultationDate = new DatePicker();
        consultationDate.setDateToToday();
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
        consultDatePanel.add(consultHours);

        frame1.add(consultDatePanel);

        /* <<<--- Warning Panel --->>> */
        JPanel warningPanel = new JPanel();
        warningPanel.setBounds(0,390,500,20);
        JLabel warning = new JLabel();
        warning.setForeground(Color.RED);
        warningPanel.add(warning);
        frame1.add(warningPanel);


        /* <<<--- Availability status Panel --->>> */

        JPanel docStatusPanel = new JPanel(new FlowLayout());
        docStatusPanel.setLayout(new BoxLayout(docStatusPanel, BoxLayout.Y_AXIS));
        docStatusPanel.setBounds(110,440,500,65);
        docStatusPanel.setVisible(false);

        JLabel finalDoc = new JLabel();
        finalDoc.setFont(new Font(finalDoc.getFont().getName(), finalDoc.getFont().getStyle(), 18));
        docStatusPanel.add(finalDoc);

        docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel setDoc = new JLabel();
        setDoc.setFont(new Font(finalDoc.getFont().getName(), finalDoc.getFont().getStyle(), 15));
        docStatusPanel.add(setDoc);


        frame1.add(docStatusPanel);

        /* <<<--- Assigning Doc --->>> */
        JPanel assigningDocPanel = new JPanel();
        assigningDocPanel.setBounds(0,460,500,30);


        /* <<<--- Final Button --->>> */
        JButton confirmDocBtn = new JButton();
        confirmDocBtn.setPreferredSize(new Dimension(100, 50));
        confirmDocBtn.setVisible(false);
        confirmDocBtn.addActionListener(e -> {
            frame2.setVisible(true);
            frame1.setVisible(false);

            //<<<--- Clear user inputs for getting inputs next time --->>>
            docTable.clearSelection();
            consultationDate.setDateToToday();
            consultTime.setTime(null);
            docStatusPanel.setVisible(false);
            confirmDocBtn.setVisible(false);
            warningPanel.setVisible(false);
            setDoc.setText(null);
        });
        frame1.add(confirmDocBtn, BorderLayout.SOUTH);

        /* <<<--- Consultation Date and Hours --->>> */

        JPanel docSubmitPanel = new JPanel();
        docSubmitPanel.setBounds(0,350,500,40);

        JButton checkDocBtn = new JButton("Check availability");
        checkDocBtn.setPreferredSize(new Dimension(150, 35));
        checkDocBtn.addActionListener(e ->{

            date = consultationDate.getDate();  //Assign date after button clicked
            startTime = consultTime.getTime();   //Assign time after button clicked
            hours = ((Number) consultHours.getValue()).intValue();

            int index = docTable.getSelectedRow();

            if (index == 0 || date == null || startTime == null){
                warning.setText("Fill all the required fields that are marked with * ");
            }
            else {
              Object selectedCellValue = docTable.getValueAt(index, 3);    //Get the value of 4 th column
                //Table selected doctor object
                for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
                    if (String.valueOf(d.getLicenceNo()).equals(String.valueOf(selectedCellValue))) {
                        if (!WestminsterSkinConsultationManager.consultationList.isEmpty()) {
                            for (Consultation c : WestminsterSkinConsultationManager.consultationList) {
                                LocalTime endTime = startTime.plusHours(hours);
                                LocalTime oldConsultStartTime = c.getTime();
                                LocalTime oldConsultEndTime = oldConsultStartTime.plusHours(c.getConsultHour());
                                if (c.getDoctor().equals(d)
                                        && date.equals(c.getDate())
                                        // if startTime is equals or after oldStart time & statTime is before oldEnd time
                                        && ((startTime.equals(oldConsultStartTime) || startTime.isAfter(oldConsultStartTime)) && startTime.isBefore(oldConsultEndTime))
                                        || (endTime.isAfter(oldConsultStartTime) && endTime.isBefore(oldConsultEndTime))) {
                                    // Loop through the doctorList and check if each doctor is available at the selected date and time
                                    ArrayList<Doctor> availableDoc = new ArrayList<>();
                                    for (Doctor ad : WestminsterSkinConsultationManager.doctorList) {
                                        boolean isAvailable = true;
                                        for (Consultation ac : WestminsterSkinConsultationManager.consultationList) {
                                            if (ac.getDoctor().equals(ad) && date.equals(ac.getDate()) && startTime.equals(ac.getTime())) {
                                                isAvailable = false;
                                                break;
                                            }
                                        }
                                        if (isAvailable) {
                                            availableDoc.add(ad);
                                        }
                                    }
                                    if (availableDoc.isEmpty()) {    // If there are no available doctors, print a message and exit the method
                                        finalDoc.setText("No doc available at the moment");
                                        finalDoc.setForeground(Color.RED);
                                        //docStatusPanel.add(finalDoc);
                                        return;
                                    }
                                    finalDoc.setText("Dr." + c.getDoctor().getName() + " " + c.getDoctor().getSurname() + " is not available");
                                    finalDoc.setForeground(Color.RED);
                                    Collections.shuffle(availableDoc);  // Shuffle available doctors and select a random doctor
                                    assignedDoc = availableDoc.get(0);
                                    setDoc.setText("     Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname() + " is assigned");
                                    //docStatusPanel.add(setDoc);

                                    //System.out.println("Dr."+ d.getName() + " " + d.getSurname() + " is available");
                                } else {
                                    assignedDoc = d;
                                    finalDoc.setText("Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname() + " is available");

                                    finalDoc.setForeground(Color.GREEN);
                                    //docStatusPanel.add(finalDoc);
                                }
                                break;
                            }
                        }else{
                            assignedDoc = d;
                            cost = hours * 15;
                            finalDoc.setText("Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname() + " is available");
                            finalDoc.setForeground(Color.GREEN);
                        }
                    }
                }
                docStatusPanel.setVisible(true);
                confirmDocBtn.setVisible(true);
                warning.setVisible(false);
                confirmDocBtn.setText("Consult Dr." + assignedDoc.getName() + " " + assignedDoc.getSurname());

                patientDetails(frame0,frame1, frame2, frame3, frame4);
            }
        });
        docSubmitPanel.add(checkDocBtn);

        frame1.add(docSubmitPanel);


        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();
        frame1.add(freePanel);

    }

//    public static Key generateKey(char[] password, byte[] salt, int iterations, int keyLength)
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//
//        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
//
//        SecretKey pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec);
//        return new SecretKeySpec(pbeKey.getEncoded(), "AES");
//    }



    public static void patientDetails(JFrame frame0, JFrame frame1,JFrame frame2,JFrame frame3, JFrame frame4){
        /* <<<--- Top Pane is in frame properties --->>> */

        /* <<<--- Enter patient details --->>> */
        JPanel patientDetailsPanel = new JPanel(new GridLayout(11,2));
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

        //NIC label and text-field
        JLabel patientSurNICLabel = new JLabel("Patient NIC *");
        patientDetailsPanel.add(patientSurNICLabel);
        JTextField getPatientNICNo = new JTextField(20);
        patientDetailsPanel.add(getPatientNICNo);

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


        frame2.add(patientDetailsPanel);

        /* <<<--- Add extra patient details --->>> */
        JPanel extraDetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        extraDetPanel.setBounds(70,340,380,110);

        JLabel patientNotes = new JLabel("Add notes");
        extraDetPanel.add(patientNotes);

        JTextArea getPatientNotes = new JTextArea();
        getPatientNotes.setLineWrap(true);    //automatic break lines
        getPatientNotes.setWrapStyleWord(true);
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
        frame2.add(freePanel);

        JButton saveDetailsBtn = new JButton("Save Details");
        saveDetailsBtn.setPreferredSize(new Dimension(100, 50));
        saveDetailsBtn.addActionListener(e->{

            LocalDate dDate = pickPatientDOB.getDate();

            if (getPatientName.getText().isEmpty() || getPatientSurName.getText().isEmpty() || dDate == null || getPatientNICNo.getText().isEmpty()){
                warning.setText("Fill all the required fields that are marked with * ");
                warningPanel.add(warning);
                warning.setVisible(true);
            }else {
                for (Patient p : WestminsterSkinConsultationManager.patientList) {
                    if (getPatientNICNo.getText().equals(p.getPatientNIC())) {
                        cost = hours * 25;
                    } else {
                        cost = hours * 15;
                    }
                }

                int patientIDNo = WestminsterSkinConsultationManager.patientList.size() + 1;
                String patientID = "ID-" + patientIDNo;

                encrypt(getPatientNotes.getText());

                Patient patient = new Patient(getPatientName.getText(), getPatientSurName.getText(),
                        pickPatientDOB.getDate(), ((Number) getPatientMobNo.getValue()).intValue(), patientID, getPatientNICNo.getText());    //Create a new patient
                WestminsterSkinConsultationManager.patientList.add(patient);    //Assign the new patient in to the patient-list array
                WestminsterSkinConsultationManager.consultationList.add(new Consultation(
                        assignedDoc, patient, date, startTime, hours, cost,encryptedNote /*new String(encryptedMessage)*/)); //Add consultation;

                viewStatusNCost(frame0,frame1, frame2, frame3, frame4, cost);
                frame3.setVisible(true);
                frame2.setVisible(false);

                //<<<--- Clear user inputs for getting inputs next time --->>>
                getPatientName.setText(null);
                getPatientSurName.setText(null);
                pickPatientDOB.setDate(null);
                getPatientNICNo.setText(null);
                getPatientMobNo.setValue(0);
                getPatientNotes.setText(null);
                warning.setVisible(false);
            }
        });
        frame2.add(saveDetailsBtn, BorderLayout.SOUTH);
    }

    public static void encrypt(String input) {
        try {
            // Get user input
            // Set password, salt, iterations, and key length
            char[] password = "mypassword".toCharArray();
            byte[] salt = "mysalt".getBytes();
            int iterations = 1000;
            int keyLength = 128;

            // Generate key
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKey pbeKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec);
            key = new SecretKeySpec(pbeKey.getEncoded(), "AES");

            // Initialize Cipher for encryption
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Encrypt message
            byte[] cipherText = cipher.doFinal(input.getBytes());

            encryptedNote = Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            // handle exception
        }
    }

    public static void viewStatusNCost(JFrame frame0, JFrame frame1,JFrame frame2,JFrame frame3, JFrame frame4, int cost){

        /* <<<--- Display success --->>> */
        JPanel successPanel = new JPanel();
        successPanel.setBounds(50,70,400,250);

        JLabel successNoteLabel = new JLabel("Consultation added Successfully");
        successNoteLabel.setForeground(Color.GREEN);
        successNoteLabel.setFont(successNoteLabel.getFont().deriveFont(20.0f));
        successPanel.add(successNoteLabel);

        successPanel.add(new JLabel("<html><br><br><br></html>"));

        JLabel patientStatus = new JLabel();
        if (cost / hours == 25){
            patientStatus.setText("Patient is already in the system");
        }else {
            patientStatus.setText("This is patient's first consultation");
        }
        successPanel.add(patientStatus);

        successPanel.add(new JLabel("<html><br><br><br></html>"));

        JLabel totalCost = new JLabel();
        totalCost.setText("Consultation cost is $" + cost);
        totalCost.setFont(totalCost.getFont().deriveFont(15.0f));
        successPanel.add(totalCost);

        successPanel.add(new JLabel("<html><br><br><br></html>"));
        successPanel.add(new JLabel("<html><br><br><br></html>"));

        JButton viewDetails = new JButton("View All Consultations");
        viewDetails.setPreferredSize(new Dimension(250, 50));
        viewDetails.addActionListener(e ->{
            viewConsultationDetails(frame0,frame1, frame2, frame3, frame4);
            frame4.setVisible(true);
            frame3.setVisible(false);
        });
        successPanel.add(viewDetails);

        JButton addAnotherPatientBtn = new JButton("Add Another Patient");
        addAnotherPatientBtn.setPreferredSize(new Dimension(170, 50));
        addAnotherPatientBtn.addActionListener(e -> {
            frame1.setVisible(true);
            frame3.setVisible(false);
        });


        frame3.add(addAnotherPatientBtn, BorderLayout.SOUTH);
//        frame3.add(viewDetails, BorderLayout.SOUTH);

        frame3.add(successPanel);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();

        frame3.add(freePanel);
    }

    public static void viewConsultationDetails(JFrame frame0, JFrame frame1,JFrame frame2,JFrame frame3, JFrame frame4){

        /* <<<--- Consultation Table Panel --->>> */
        JPanel consultationTablePanel = new JPanel();
        consultationTablePanel.setBounds(0,30,500,200);
        //consultationTablePanel.setBackground(Color.LIGHT_GRAY);

        String[] columnNames = {"Patient ID", "Patient's Name","Doctor", "Date", "Time", "Cost (£)"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        for (Consultation c : WestminsterSkinConsultationManager.consultationList) {    // for(i=0; i<WestminsterSkinConsultationManager.doctorList.size(); i++)
            dtm.addRow(new String[]{
                    c.getPatient().getPatientID(),
                    c.getPatient().getName() + " " + c.getPatient().getSurname(),
                    c.getDoctor().getName() + " " + c.getDoctor().getSurname(),
                    String.valueOf(c.getDate()),
                    String.valueOf(c.getTime()),
                    String.valueOf(c.getCost()),
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

        TableRowSorter<DefaultTableModel> myTRS = new TableRowSorter<>(dtm);
        consultationDetailsTable.setRowSorter(myTRS);   //Sort the table

        consultationDetailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow to select a single row at a time

        JScrollPane scrollPane = new JScrollPane(consultationDetailsTable); //Make table scrollable
        scrollPane.setPreferredSize(new Dimension(480, 150));

        consultationTablePanel.add(scrollPane);

        /* <<<--- View More Details Panel --->>> */

        JPanel fullDetailVIewPanel = new JPanel(new GridLayout(10,2));
        fullDetailVIewPanel.setBounds(100,235,300,250);
        fullDetailVIewPanel.setVisible(false);

        JLabel patientFullName = new JLabel("Patient Full Name : ");
        fullDetailVIewPanel.add(patientFullName);
        JLabel pFullName = new JLabel();
        fullDetailVIewPanel.add(pFullName);

        JLabel patientDOB = new JLabel("Patient DOB : ");
        fullDetailVIewPanel.add(patientDOB);
        JLabel pDOB = new JLabel();
        fullDetailVIewPanel.add(pDOB);

        JLabel patientNIC = new JLabel("Patient NIC : ");
        fullDetailVIewPanel.add(patientNIC);
        JLabel pNIC = new JLabel();
        fullDetailVIewPanel.add(pNIC);

        JLabel patientMobNo = new JLabel("Patient Mobile No : ");
        fullDetailVIewPanel.add(patientMobNo);
        JLabel pMobNo = new JLabel();
        fullDetailVIewPanel.add(pMobNo);

        JLabel consultationDoctor = new JLabel("Consultation Doctor : ");
        fullDetailVIewPanel.add(consultationDoctor);
        JLabel pDoc = new JLabel();
        fullDetailVIewPanel.add(pDoc);

        JLabel consultDOcLNo = new JLabel("Doctor ID : ");
        fullDetailVIewPanel.add(consultDOcLNo);
        JLabel pDocLN = new JLabel();
        fullDetailVIewPanel.add(pDocLN);

        JLabel consultationDateTime = new JLabel("Consult Date Time : ");
        fullDetailVIewPanel.add(consultationDateTime);
        JLabel pDateTime = new JLabel();
        fullDetailVIewPanel.add(pDateTime);

        JLabel consultationHour = new JLabel("Consultation Hour : ");
        fullDetailVIewPanel.add(consultationHour);
        JLabel pHours = new JLabel();
        fullDetailVIewPanel.add(pHours);

        JLabel consultationCost = new JLabel("Consultation Cost : ");
        fullDetailVIewPanel.add(consultationCost);
        JLabel pCost = new JLabel();
        fullDetailVIewPanel.add(pCost);

        JLabel extraNotes = new JLabel("Extra Notes : ");
        fullDetailVIewPanel.add(extraNotes);
        JLabel pNotes = new JLabel();
        fullDetailVIewPanel.add(pNotes);

        JButton fullViewBtn = new JButton("View Full Details");
        fullViewBtn.addActionListener(e -> {
            try {
            int index = consultationDetailsTable.getSelectedRow();
            Object selectedCellValue = consultationDetailsTable.getValueAt(index, 0);
            for (Consultation c : WestminsterSkinConsultationManager.consultationList) {
                if (String.valueOf(c.getPatient().getPatientID()).equals(String.valueOf(selectedCellValue))) {
                    Object fName = c.getPatient().getName() + " " + c.getPatient().getSurname();
                    Object dFName = c.getDoctor().getName() + " " + c.getDoctor().getSurname();
                    Object cDateTime = c.getDate() + " " + c.getTime();

                    pFullName.setText(String.valueOf(fName));
                    pDOB.setText(String.valueOf(c.getPatient().getDateOfBirth()));
                    pNIC.setText(String.valueOf(c.getPatient().getPatientNIC()));
                    pMobNo.setText(String.valueOf(c.getPatient().getMobilNo()));
                    pDoc.setText(String.valueOf(dFName));
                    pDocLN.setText(String.valueOf(c.getDoctor().getLicenceNo()));
                    pDateTime.setText(String.valueOf(cDateTime));
                    pHours.setText(String.valueOf(c.getConsultHour()));
                    pCost.setText(String.valueOf(c.getCost()));
                    decrypt();
                    pNotes.setText(decryptedString);
                    break;
                }
            }
            fullDetailVIewPanel.setVisible(true);
        }catch (IndexOutOfBoundsException ex){
            }
                });
        consultationTablePanel.add(fullViewBtn);

        JButton deleteConsultationBtn = new JButton("Delete Consultation");
        deleteConsultationBtn.addActionListener(e ->{
            int index = consultationDetailsTable.getSelectedRow();
            try {
            Object selectedCellValue = consultationDetailsTable.getValueAt(index,0);
            if (WestminsterSkinConsultationManager.consultationList.size() > 0){
                if (WestminsterSkinConsultationManager.consultationList.removeIf(consultation -> String.valueOf(consultation.getPatient().getPatientID()).equals(selectedCellValue))) {
                    if (index >= 0) {
                        DefaultTableModel model = (DefaultTableModel) consultationDetailsTable.getModel();
                        model.removeRow(index);
                        consultationDetailsTable.revalidate();
                        consultationDetailsTable.repaint();
                    }else {
                        System.out.println("Wrong Input");
                    }
                }
            }
            }catch (IndexOutOfBoundsException ex){
            }
        });

        consultationTablePanel.add(deleteConsultationBtn);

        frame4.add(consultationTablePanel);

        JButton backToMenu = new JButton("Main Menu");
        backToMenu.setPreferredSize(new Dimension(170, 50));
        frame4.add(backToMenu, BorderLayout.SOUTH);
        backToMenu.addActionListener(e ->{
            fullDetailVIewPanel.setVisible(false);
            frame0.setVisible(true);
            frame4.setVisible(false);
        });

        frame4.add(fullDetailVIewPanel);

        /* <<<--- Free Panel --->>> */
        JPanel freePanel = new JPanel();

        frame4.add(freePanel);
    }

    public static void decrypt(){
        // Initialize Cipher for decryption
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println("Error initializing cipher: " + ex.getMessage());
            return;
        }
        byte[] decryptedMessage;
        try {
            decryptedMessage = cipher.doFinal(Base64.getDecoder().decode(encryptedNote));
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println("Error decrypting message: " + ex.getMessage());
            return;
        }
         decryptedString = new String(decryptedMessage);
    }

}
