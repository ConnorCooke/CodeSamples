package gui;

import entities.Doctor;

import javax.swing.*;

public class DoctorFrame extends JFrame {
    /** The standard width for the frame. */
    public static final int DEFAULT_WIDTH = 350;

    /** The standard height for the frame. */
    public static final int DEFAULT_HEIGHT = 280;

    /**
     * Creates a frame for a doctor panel to perform commands on the given doctor
     * @param doctor a doctor for use in the doctor panels commands
     */
    public DoctorFrame(Doctor doctor){
        setTitle("Doctor Panel for " + doctor.getName());
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        DoctorPanel panel = new DoctorPanel(doctor);
        add(panel);
    }
}
