package gui;

import commands.AddDoctorCommand;
import commands.AddPatientCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorAddPanel extends JPanel {

    /* Declare the components of the panel needed by inner classes. */

    /**
     * A text area to be used to display an error if one should occur.
     */
    JTextArea error = null;

    /**
     * A panel for the entry of the name of a new patient.
     */
    ValueEntryPanel namePanel;

    /**
     * A panel for the entry of the health number of a new patient.
     */
    ValueEntryPanel isSurgeon;

    /**
     * Create the panel to obtain data for the creation of a doctor, and to cause the doctor to be
     * created.
     */
    public DoctorAddPanel() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        // add a label with a prompt to enter the doctors data
        JLabel prompt = new JLabel("Enter Doctor Information");
        prompt.setMaximumSize(prompt.getPreferredSize());
        add(prompt);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        // add a panel with the field for the entry of the doctor's name
        namePanel = new ValueEntryPanel("Name");
        namePanel.setMaximumSize(namePanel.getPreferredSize());
        add(namePanel);
        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        //add a panel for checking if the doctor is a surgeon
        isSurgeon = new ValueEntryPanel("Surgeon(y/n)");
        isSurgeon.setMaximumSize(isSurgeon.getPreferredSize());
        add(isSurgeon);
        isSurgeon.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());

        // add a button to submit the information and create the doctor
        JButton submitButton = new JButton("Submit");
        submitButton.setMaximumSize(submitButton.getPreferredSize());
        add(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(new DoctorAddPanel.SubmitListener());
        add(Box.createVerticalGlue());
    }

    /**
     * The class listening for the press of the submit button. It accesses the name and is a surgeon
     * data entered, and uses them to add a new Doctor to the system.
     */
    private class SubmitListener implements ActionListener {
        /**
         * When the submit button is pressed, access the name and if they are a surgeon, and use
         * them to add a new Doctor to the system.
         */
        public void actionPerformed(ActionEvent event) {
            if (error != null) {
                // remove error from the previous submission
                remove(error);
                error = null;
            }
            String name = namePanel.getValueAsString();
            String surgeonInput = isSurgeon.getValueAsString();

            boolean surgeon = false;
            if(surgeonInput == "yes" || surgeonInput == "Yes" || surgeonInput == "y") {
                surgeon = true;
            }
            AddDoctorCommand addDoctor = new AddDoctorCommand();
            addDoctor.addDoctor(name, surgeon);

            if (addDoctor.wasSuccessful()) {
                getTopLevelAncestor().setVisible(false);

            } else {
                error = new JTextArea(SplitString.at(addDoctor.getErrorMessage(), 40));
                error.setMaximumSize(error.getPreferredSize());
                add(error);
                error.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(Box.createVerticalGlue());
                revalidate(); // redraw the window as it has changed
            }
        }
    }
}
