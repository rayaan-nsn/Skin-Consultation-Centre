import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultationGUI extends JFrame {

    static int cost;
    static LocalDate date;
    static LocalTime time;
    static Doctor asignedDoc;

    ConsultationGUI(){
        //Frame 2
        JFrame patientDetails = new JFrame("Westminster Skin Care Centre");
        //patientDetailFrame(patientDetails);
        frameProperties(patientDetails);
        patientDetails.setVisible(false); // make the frame invisible until button click event

        //Frame 1
        JFrame docCheckFrame = new JFrame("Westminster Skin Care Centre");
        frameProperties(docCheckFrame);
        docAvailable(docCheckFrame,patientDetails);
        docCheckFrame.setVisible(true);
    }
    void frameProperties(JFrame frame){
        frame.setSize(500,600); // set the size of the frame
        frame.setLocationRelativeTo(null);  // center the frame on the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Exit out of application
        //frame.pack();   // Pack the frame to fit its contents
    }

    void docAvailable(JFrame frame1, JFrame frame2){
        /* <<<------------------ Top Pane ------------------>>> */
        JPanel topPanel = new JPanel();
        //topPanel.setBackground(Color.GREEN);
        topPanel.setBounds(0,0,500,70);

        JLabel topic = new JLabel();
        topic.setText("Westminster Skin Care Center");

        topPanel.add(topic);

//        Image image = new ImageIcon("logo.svg").getImage();
//
//        JLabel label = new JLabel();
//        label.setIcon(new ImageIcon(image));
//
//        label.setPreferredSize(new Dimension(500, 50));
//
//        topPanel.add(label);
        frame1.add(topPanel);

        /* <<<--- Doctor Selection Pane --->>> */
        JPanel docTablePanel = new JPanel();
        docTablePanel.setBounds(0,70,500,130);
        //docTablePanel.setBackground(Color.LIGHT_GRAY);

        String[] columnNames = {"Name", "Surname","Mobile No","Licence No","Specialisation"};
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
        for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
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

        JLabel pickConsultDate = new JLabel("Consultation Date");
        consultDatePanel.add(pickConsultDate);
        DatePicker consultationDate = new DatePicker();
//        consultationDate.setPreferredSize(new Dimension(50, 50));
        consultDatePanel.add(consultationDate);

        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline
        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel pickConsultTime = new JLabel("Consultation Time");
        consultDatePanel.add(pickConsultTime);
        TimePicker consultTime = new TimePicker();
        consultDatePanel.add(consultTime);

        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline
        consultDatePanel.add(new JLabel("<html><br></html>"));   //Newline

        JLabel pickHours = new JLabel("Consultation Hours");
        consultDatePanel.add(pickHours);

        SpinnerModel model = new SpinnerNumberModel(1, 1, 5, 1);
        JSpinner consultHours = new JSpinner(model);
//        consultHours.setPreferredSize(new Dimension(100, 50));
        consultDatePanel.add(consultHours);

        frame1.add(consultDatePanel);

        /* <<<--- Availability status Panel --->>> */

        JPanel docStatusPanel = new JPanel();
        docStatusPanel.setLayout(new BoxLayout(docStatusPanel, BoxLayout.Y_AXIS));
        docStatusPanel.setBounds(0,420,500,70);
        //docStatusPanel.setBackground(Color.YELLOW);
        docStatusPanel.setVisible(false);

        JLabel finalDoc = new JLabel("Doctor is available");
        finalDoc.setFont(new Font(finalDoc.getFont().getName(), finalDoc.getFont().getStyle(), 18));
        docStatusPanel.add(finalDoc);

        docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline
        docStatusPanel.add(new JLabel("<html><br></html>"));   //Newline


        JLabel showCost = new JLabel();
        docStatusPanel.add(showCost);

        frame1.add(docStatusPanel);

        /* <<<--- Final Button --->>> */
        JButton confirmDocBtn = new JButton("Consult");
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
            docStatusPanel.setVisible(true);
            confirmDocBtn.setVisible(true);

            calcCost(consultHours, showCost);
            date = consultationDate.getDate();  //Assign date after button clicked
            time = consultTime.getTime();   //Assign time after button clicked

            int index = docTable.getSelectedRow();
            Object selectedCellValue = docTable.getValueAt(index, 3);    //Get the value of 4 th column

            //Table selected doctor object
            for (Doctor d : WestminsterSkinConsultationManager.doctorList) {
                if (String.valueOf(d.getLicenceNo()).equals(String.valueOf(selectedCellValue))) {
                    for (Consultation c : WestminsterSkinConsultationManager.consultationList){
                        if (c.getDoctor().equals(d) && date.equals(c.getDate()) && time.equals(c.getTime())) {
                            System.out.println("Doctor is not available");
                            break;
                        }
                        asignedDoc = d;
                        System.out.println("Dr."+ d.getName() + " " + d.getSurname() + " is available");
                        break;
                    }
                    break;
                }
            }



            //Disable selection options to prevent edit
            disableOptions(consultationDate,consultHours,docTable,consultTime,checkDocBtn);
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
}



