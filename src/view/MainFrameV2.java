package view;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/*
public class MainFrameV2 extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu options;
    JMenu help;
    JMenuItem openItem;
    JMenuItem closeItem;
    JMenuItem exitItem;
    JMenuItem toolTipsItem;
    JMenuItem musicItem;
    JMenuItem homePage;
    JMenuItem about;
    private JPanel mainPanel;
    private NavigationPanel navigationPanel;
    public MainFrameV2() {
        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 900);

        //Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(25,55,109));
        getContentPane().add(mainPanel);

        // Create a navigation panel
        navigationPanel = new NavigationPanel();
        mainPanel.add(navigationPanel, BorderLayout.NORTH);




        // Create menu bar and icons
        JMenuBar menuBar = new JMenuBar();

        // Create menu items for file menu
        fileMenu = new JMenu("File");
         openItem = new JMenuItem("Open");
         closeItem = new JMenuItem("Close");
         exitItem = new JMenuItem("Exit");
        fileMenu.add(openItem);
        fileMenu.add(closeItem);
        fileMenu.add(exitItem);

        // Create menu items for options menu
        options = new JMenu("Options");
        toolTipsItem = new JCheckBoxMenuItem("Enable Tool Tips");
        musicItem = new JCheckBoxMenuItem("Disable/Enable music");
        options.add(toolTipsItem);
        options.add(musicItem);

        // Create menu items for help menu
        help = new JMenu("Help");
        homePage = new JMenuItem("Homepage");
        about = new JMenuItem("About");
        help.add(homePage);
        help.add(about);

        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(options);
        menuBar.add(help);
        setJMenuBar(menuBar);

        // Add action listeners to menu items
        openItem.addActionListener(this);
        closeItem.addActionListener(this);
        exitItem.addActionListener(this);
        toolTipsItem.addActionListener(this);
        musicItem.addActionListener(this);
        homePage.addActionListener(this);
        about.addActionListener(this);

        // Show window
        this.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==exitItem){
            System.exit(0);
        }

        if(e.getSource()==openItem){
            System.out.println("You opened a file!");
        }

        if (e.getSource() == toolTipsItem) {
            boolean isSelected = toolTipsItem.isSelected();


        }

        if (e.getSource() == musicItem) {

            boolean isSelected = musicItem.isSelected();




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

    public static void main (String[] args) throws ParseException {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            EventQueue.invokeLater(() -> new MainFrameV2());

        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

 */
