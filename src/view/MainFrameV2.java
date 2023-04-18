package view;


import javax.swing.*;


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
