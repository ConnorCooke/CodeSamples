package gui;

import containers.DoctorSetAccess;
import entities.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorOpsPanel extends JPanel {

    /**
     * Create the panel for operations involving doctors. There is a button to add doctors, a field to access
     * specific doctors, a button to list all doctors, and an exit button to hide the window with this frame
     */
    public DoctorOpsPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // add a button to add a new doctor
        JButton addDoctorButton = new JButton("Add a Doctor");
        addDoctorButton.setMaximumSize(addDoctorButton.getPreferredSize());
        add(addDoctorButton);
        addDoctorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDoctorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DoctorAddFrame frame = new DoctorAddFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        // add a panel with a field to access a specific doctor
        JPanel accessDoctor = accessDoctor();
        accessDoctor.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(accessDoctor);
        add(Box.createVerticalGlue());

        //add a button to to display all the doctors
        JButton listDoctors = new JButton("List Doctors");
        listDoctors.setMaximumSize(listDoctors.getPreferredSize());
        add(listDoctors);
        listDoctors.setAlignmentX(Component.CENTER_ALIGNMENT);
        listDoctors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, DoctorSetAccess.dictionary().values());
            }
        });
        add(Box.createVerticalGlue());

        //add a button to exit from the window containing this panel
        final JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(exitButton.getPreferredSize());
        add(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
            }
        });
        add(Box.createVerticalGlue());
    }

    /**
     * The panel for accessing a specific doctor. It has a prompt label, and a text field for the entry of the
     * doctor's name. When the Enter key is pressed, the health number entered is used to create a new window
     * with the doctor's data and operations on the doctor. If their is no doctor with the entered name a brief message
     * detailing that is displayed.
     * @return an accessDoctor panel for entering a doctors name
     */
    private JPanel accessDoctor(){

        JPanel accessDoctor = new JPanel();
        accessDoctor.add(new JLabel("Add patients to this doc:"));

        final JTextField textField = new JTextField(10);
        accessDoctor.add(textField);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String doctorName = textField.getText();
                Doctor doctor = DoctorSetAccess.dictionary().get(doctorName);
                if(doctor == null) {
                    textField.setText("No doctor with that name");
                    textField.revalidate();
                    return;
                }
                DoctorFrame frame = new DoctorFrame(doctor);
                frame.setVisible(true);
                textField.setText("");
                textField.revalidate();
            }
        });

        return accessDoctor;
    }
}
