import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

public class WSCFrame extends JFrame{

    WSCFrame(){
        //Frame 2
        JFrame dateTimeFrame = new JFrame("Westminster Skin Care Centre");
            frameProperties(dateTimeFrame);
            dateTimeFrame.setVisible(false); // make the frame invisible until button click event

        //Frame 1
        JFrame docTableFrame = new JFrame("Westminster Skin Care Centre");
            frameProperties(docTableFrame);
            docTableFrameFunctions(docTableFrame, dateTimeFrame);
            docTableFrame.setVisible(true); // make the frame visible

    }




    void frameProperties(JFrame frame){
        frame.setSize(500,500); // set the size of the frame
        frame.setLocationRelativeTo(null);  // center the frame on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        //frame.pack();   // Pack the frame to fit its contents
    }

    public void docTableFrameFunctions(JFrame frame1, JFrame frame2){
        ArrayList<Doctor> docData = (ArrayList<Doctor>) WestminsterSkinConsultationManager.doctorList.clone();
        String[] columnNames = {"Name", "Surname", "DOB","Mobile No","License No","Specialisation"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 1);
        for (Doctor d : docData) {
            dtm.addRow(new String[]{
                    d.getName(),
                    d.getSurname(),
                    String.valueOf(d.getDateOfBirth()),
                    String.valueOf(d.getMobilNo()),
                    String.valueOf(d.getLicenceNo()),
                    d.getSpecialisation()});
        }

        UtilDateModel udm = new UtilDateModel();
        JDatePicker datePicker = new JDatePicker(udm);

        JPanel dateTimePanel = new JPanel();
        dateTimePanel.setBounds(0,250,500,100);
        dateTimePanel.setBackground(Color.RED);
        dateTimePanel.add(datePicker);
        dateTimePanel.setVisible(false);
        frame1.add(dateTimePanel);

        JButton selectDocBtn = new JButton("Select Doctors");
            selectDocBtn.setBounds(150,100,200,50);
            frame1.add(selectDocBtn);
            selectDocBtn.addActionListener(e -> dateTimePanel.setVisible(true));


        JButton docTableBtn = new JButton("Consult Doctors");
            docTableBtn.setPreferredSize(new Dimension(100, 50));
            frame1.add(docTableBtn, BorderLayout.SOUTH);
            docTableBtn.addActionListener(e -> frame1.setVisible(false));
            docTableBtn.addActionListener(e -> frame2.setVisible(true));


        JTable docTable = new JTable(dtm);
            frame1.add(docTable);
            docTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Allow the user to only select a single row at a time



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
