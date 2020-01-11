package gui;

import commands.AssignDoctorCommand;
import commands.DropAssocCommand;
import containers.PatientSetAccess;
import entities.Doctor;
import entities.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The panel to display the information for a doctor, and accept operations on the doctor. The
 * panel gives the doctor's name and if they are a surgeon. It has a panel containing a text field that allows access
 * to a specific patient The user also has the option to add another patient or remove a patient.
 */
public class DoctorPanel extends JPanel {
    public DoctorPanel(Doctor doctor){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Name: " + doctor.getName()));

        //Panel for adding a connection between a patient and the doctor
        JPanel addPatientPanel = addPatientPanel(doctor);
        add(addPatientPanel);
        addPatientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addPatientPanel.setMaximumSize(addPatientPanel.getPreferredSize());
        add(new JLabel("  ")); // blank line in the panel for spacing

        //Panel for removing a connection between a patient and the doctor
        JPanel removePatientPanel = removePatientPanel(doctor);
        add(removePatientPanel);
        removePatientPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        removePatientPanel.setMaximumSize(removePatientPanel.getPreferredSize());
        add(new JLabel("  ")); // blank line in the panel for spacing

        //Panel for accessing a specific patient
        PatientAccessPanel accessPanel = new PatientAccessPanel();
        add(accessPanel);
        accessPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        accessPanel.setMaximumSize(accessPanel.getPreferredSize());
        add(new JLabel("  ")); // blank line in the panel for spacing

        final JButton exitButton = new JButton("Exit");
        add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                exitButton.getTopLevelAncestor().setVisible(false);
            }
        });
        exitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * A panel to add a doctor-patient association for this doctor. The panel has a prompt to enter
     * the patient's health number, and a field to enter the health number.
     * @param doctor the current doctor
     * @return a panel to associate a patient with this doctor
     */
    private JPanel addPatientPanel(final Doctor doctor) {
        JPanel addPatientPanel = new JPanel();
        addPatientPanel.add(new JLabel("Add patient"));
        final JTextField textField = new JTextField(10);
        addPatientPanel.add(textField);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                int healthNum = -1;
                try {
                    healthNum = Integer.parseInt(valueAsString);
                } catch (NumberFormatException e) {
                    textField.setText("Not an int: " + textField.getText());
                    textField.revalidate();
                    return;
                }
                AssignDoctorCommand assignDoc = new AssignDoctorCommand();
                assignDoc.assignDoctor(doctor.getName(), healthNum);
                if(assignDoc.wasSuccessful()){
                    textField.setText("");
                    revalidate();
                }
                else{
                    textField.setText(assignDoc.getErrorMessage());
                    textField.revalidate();
                    return;
                }

            }
        });
        return addPatientPanel;
    }

    /**
     * A panel to remove a doctor-patient association for this doctor. The panel has a prompt to enter
     * the patient's health number, and a field to enter the health number.
     *
     * @param doctor the current doctor
     * @return a panel to associate a patient with this doctor
     */
    private JPanel removePatientPanel(final Doctor doctor) {
        JPanel removePatientPanel = new JPanel();
        removePatientPanel.add(new JLabel("Remove patient"));
        final JTextField textField = new JTextField(10);
        removePatientPanel.add(textField);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String valueAsString = textField.getText();
                int healthNum = -1;
                try {
                    healthNum = Integer.parseInt(valueAsString);
                } catch (NumberFormatException e) {
                    textField.setText("Not an int: " + textField.getText());
                    textField.revalidate();
                    return;
                }
                DropAssocCommand dropAssoc = new DropAssocCommand();
                dropAssoc.dropAssociation(doctor.getName(), healthNum);
                if (dropAssoc.wasSuccessful()) {
                    // recreate the panel as it has changed
                    textField.setText("");
                    textField.revalidate();
                } else {
                    textField.setText(dropAssoc.getErrorMessage());
                    textField.revalidate();
                    return;
                }
            }
        });
        return removePatientPanel;
    }

}
