package gui;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    JButton patientOpsButton = new JButton("Patient Operations");
    JButton doctorOpsButton = new JButton("Doctor Operations");
    JButton wardInfoButton = new JButton("Ward Information");
    JButton exitButton = new JButton("End Program");

    /**
     * A panel for the primary functions of the system. Has a button to open a frame for operations on patients, a
     * button to open a frame for operations on doctors, a button to show ward information and assign or remove
     * patients from beds, and a button to end the program.
     */
    public MainMenuPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());

        JButton patientOpsButton = new JButton("Patient Operations");
        add(patientOpsButton);
        patientOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        patientOpsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                PatientOpsFrame frame = new PatientOpsFrame(); // Used for assign5
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        JButton doctorOpsButton = new JButton("Doctor Operations");
        add(doctorOpsButton);
        doctorOpsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doctorOpsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                DoctorOpsFrame frame = new DoctorOpsFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        JButton wardInfoButton = new JButton("Ward Information");
        wardInfoButton.setMaximumSize(wardInfoButton.getPreferredSize());
        add(wardInfoButton);
        wardInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        wardInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                WardFrame frame = new WardFrame();
                frame.setLocation(300, 300);
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        add(Box.createVerticalGlue());

        final JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(exitButton.getPreferredSize());
        add(exitButton);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        add(Box.createVerticalGlue());

    }

}
