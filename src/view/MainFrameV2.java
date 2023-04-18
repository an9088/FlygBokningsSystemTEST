package view;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;


public class MainFrameV2 {
    private JPanel panelMain;

    public void setLookAndFeel() {
        try {

            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
        } catch (Exception exception) {

            //UIManager.setLookAndFeel(new SyntheticaBlueMoonLookAndFeel());


            exception.printStackTrace();
        }
    }

    public static void main (String[] args){

    }
}
