package com.skov.timeRegForrest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * yo
 */
public class Gui extends JPanel implements ActionListener {

    public static final String LIST_JIRAS_BUTTON_PRESSED = "listJirasButtonPressed";
    static JFrame frame;
    static HashMap<String, Integer> timeRegTimeMap = new HashMap<String, Integer>();
    static HashMap<String, JButton> timeRegNameMap = new HashMap<String, JButton>();
    static HashMap<String, JTextField> descriptionMap = new HashMap<String, JTextField>();
    static HashMap<String, JButton> timeRegSubmittedTimeMap = new HashMap<String, JButton>();
    static HashMap<String, JTextField> jiraNumbersMap = new HashMap<String, JTextField>();
    static JTextField txtFieldInOffice, txtFieldOutOffice;

    static JLabel timeInfoLabel;
    static JComboBox popupIntervalComboBox, submitDurationMinutesComboBox;
    static JCheckBox autoMinimizeCheckBox, autoUpdateOfficeOutCheckBox;
    static ArrayList<Integer> shortCutList = new ArrayList<Integer>();

    public static String FROKOST_PAUSER = "Frokost & pauser";
//    static HashMap<Integer, Integer> taskIds = new HashMap<Integer, Integer>();
//    static int taskIdNext = 0;


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public Gui() {
        super.setLayout(new GridLayout(0, 1));

        //--
        JPanel officeInPanel = new JPanel(new GridLayout(1,2));

        officeInPanel.add(new JLabel("Office IN:"));

        Calendar officeInCalendar = Calendar.getInstance();
        officeInCalendar.set(Calendar.HOUR_OF_DAY, 8);
        officeInCalendar.set(Calendar.MINUTE, 00);

        String officeInCalendarStr = sdf.format(officeInCalendar.getTime());

        txtFieldInOffice = new JTextField(officeInCalendarStr + " ?");
        txtFieldInOffice.setBackground(new Color(255, 255, 0));

        officeInPanel.add(txtFieldInOffice);
        officeInPanel.add(new JLabel(""));
        add(officeInPanel);

        //--

        JPanel officeOutPanel = new JPanel(new GridLayout(1,2));

        txtFieldOutOffice = new JTextField(sdf.format(Calendar.getInstance().getTime()));

        autoUpdateOfficeOutCheckBox = new JCheckBox("Office OUT:");
        autoUpdateOfficeOutCheckBox.setSelected(true);
        officeOutPanel.add(autoUpdateOfficeOutCheckBox);

        officeOutPanel.add(txtFieldOutOffice);
        officeOutPanel.add(new JLabel(""));
        add(officeOutPanel);

        //--

        String minutes = " minutes";
        String[] minutValuesArr = {"1" + minutes, "5" + minutes, "10" + minutes, "15" + minutes, "30" + minutes, "45" + minutes, "60" + minutes, "90" + minutes, "120 minute", "180" + minutes, "240" + minutes};
        popupIntervalComboBox = new JComboBox(minutValuesArr);
        popupIntervalComboBox.setSelectedIndex(4);

        JPanel popupIntervalPanel = new JPanel(new GridLayout());
        popupIntervalPanel.add(new JLabel("Gui popup interval: "));
        popupIntervalPanel.add(popupIntervalComboBox);
        popupIntervalPanel.add(new JLabel(""));
        add(popupIntervalPanel);

        //--

        shortCutList.add(KeyEvent.VK_0);
        shortCutList.add(KeyEvent.VK_1);
        shortCutList.add(KeyEvent.VK_2);
        shortCutList.add(KeyEvent.VK_3);
        shortCutList.add(KeyEvent.VK_4);
        shortCutList.add(KeyEvent.VK_5);
        shortCutList.add(KeyEvent.VK_6);
        shortCutList.add(KeyEvent.VK_7);
        shortCutList.add(KeyEvent.VK_8);
        shortCutList.add(KeyEvent.VK_9);
        shortCutList.add(KeyEvent.VK_A);
        shortCutList.add(KeyEvent.VK_B);
        shortCutList.add(KeyEvent.VK_C);
        shortCutList.add(KeyEvent.VK_D);
        shortCutList.add(KeyEvent.VK_E);
        shortCutList.add(KeyEvent.VK_F);
        shortCutList.add(KeyEvent.VK_G);
        shortCutList.add(KeyEvent.VK_H);
        shortCutList.add(KeyEvent.VK_I);
        shortCutList.add(KeyEvent.VK_J);
        shortCutList.add(KeyEvent.VK_K);
        shortCutList.add(KeyEvent.VK_L);
        shortCutList.add(KeyEvent.VK_M);
        shortCutList.add(KeyEvent.VK_N);

        //--
        submitDurationMinutesComboBox = new JComboBox(minutValuesArr);
        submitDurationMinutesComboBox.setSelectedIndex(3);

        JPanel submitDurationPanel = new JPanel(new GridLayout());
        submitDurationPanel.add(new JLabel("Submit duration: "));
        submitDurationPanel.add(submitDurationMinutesComboBox);
        submitDurationPanel.add(new JLabel(""));
        add(submitDurationPanel);
        //--

        //--
        autoMinimizeCheckBox = new JCheckBox("Auto minimize");
        autoMinimizeCheckBox.setSelected(true);
        add(autoMinimizeCheckBox);

        //--


        add(new JLabel(""));

        timeInfoLabel = new JLabel("Please set office int time");
        add(timeInfoLabel);



        addButton("M�der: Kanban", 1604);
        addButton("M�der: Agile (retrospective + ERFA)", 1608);
        addButton("M�der: Xportal (teamm�der mandag fredag)", 870);
        addButton("M�der: Afklaring", 870);
        addButton("");
        addButton("Egen administration", 1005);
        addButton("Ikke TK prioriterede opgaver", 878);
        addButton("Other", 885);
        addButton("");
        addButton("Daglig forvaltning", 864);
        addButton("Sagscontainer daglig forv.", 882);
        addButton("Driftstabilitet i Xportalen", 883);
        addButton("Documentation", 881);

        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");
        addButton("");

        addButton(FROKOST_PAUSER);

        add(new JLabel("github.com/tarcom/TimeRegForrest"));


    }

    protected static void updateTxtFieldOutOffice() {
        if (autoUpdateOfficeOutCheckBox.isSelected()) {
            txtFieldOutOffice.setText(sdf.format(Calendar.getInstance().getTime()));
        }
    }

    protected static int getPopupIntervalMinutes() {
        return Integer.valueOf(((String) Gui.popupIntervalComboBox.getSelectedItem()).replace(" minutes", "").trim());
    }

    protected static int getSubmitDurationMinutes() {
        return Integer.valueOf(((String) Gui.submitDurationMinutesComboBox.getSelectedItem()).replace(" minutes", "").trim());
    }

    void addButton(String name, int... jiraNumber) {

        JPanel rowPanel = new JPanel(new GridBagLayout());

        int shortcutKey = shortCutList.remove(0);
        String shortcutKeyStr = String.valueOf(shortcutKey);

        //--

        //todo: I wish I coult set default size for this...
        rowPanel.add(new JLabel(String.valueOf(" ALT+" + KeyEvent.getKeyText(shortcutKey) + " ")));

        //--

        JTextField descriptionTxtField = new JTextField(name, 20);
        if (FROKOST_PAUSER.equalsIgnoreCase(name)) {
            FROKOST_PAUSER = shortcutKeyStr; //well not nice, but it works
            descriptionTxtField.setBackground(new Color(150, 150, 150));
        }
        rowPanel.add(descriptionTxtField);

        descriptionMap.put(shortcutKeyStr, descriptionTxtField);

        //--

        String jiraNumberLink = "XP-";
        if (jiraNumber != null && jiraNumber.length == 1) {
            jiraNumberLink = "XP-" + jiraNumber[0];
        }

        JTextField jiraLinkField = new JTextField(jiraNumberLink, 5);

        String key = shortcutKeyStr;
        jiraNumbersMap.put(key, jiraLinkField);

        rowPanel.add(jiraLinkField);

        //--

        JButton listJirasButton = new JButton();
        listJirasButton.setPreferredSize(new Dimension(7,19));

        listJirasButton.setActionCommand(LIST_JIRAS_BUTTON_PRESSED + key);
        listJirasButton.setToolTipText("Shows which static jiras issues that you can use");
        listJirasButton.addActionListener(this);

        rowPanel.add(listJirasButton);

        //--

        JButton plusButton = new JButton("+");
        plusButton.setVerticalTextPosition(AbstractButton.CENTER);
        plusButton.setHorizontalTextPosition(AbstractButton.LEADING);
        plusButton.setMnemonic(KeyEvent.VK_D);

        plusButton.setActionCommand(shortcutKeyStr + "Plus");
        plusButton.setToolTipText("Submit 15 minutes");
        plusButton.addActionListener(this);
        plusButton.setMnemonic(shortcutKey);

        rowPanel.add(plusButton);

        timeRegNameMap.put(shortcutKeyStr, plusButton);

        //--

        JButton minusButton = new JButton("-");
        minusButton.setVerticalTextPosition(AbstractButton.CENTER);
        minusButton.setHorizontalTextPosition(AbstractButton.LEADING);
        minusButton.setMnemonic(KeyEvent.VK_D);

        minusButton.setActionCommand(shortcutKeyStr + "Minus");
        minusButton.setToolTipText("Submit 15 minutes");
        minusButton.addActionListener(this);

        rowPanel.add(minusButton);

        //--

        JButton timeSubmittedLabel = new JButton("           ");
        timeSubmittedLabel.setToolTipText("Click to open browser and submit time in JIRA " + jiraNumberLink);
        timeSubmittedLabel.setActionCommand("XP-" + shortcutKeyStr);
        timeSubmittedLabel.setBorderPainted(false);
        timeSubmittedLabel.addActionListener(this);
        rowPanel.add(timeSubmittedLabel);

        timeRegSubmittedTimeMap.put(shortcutKeyStr, timeSubmittedLabel);
        timeRegTimeMap.put(shortcutKeyStr, 0);

        //--

        add(rowPanel);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().endsWith("Plus")) {
            String plusName = e.getActionCommand().replace("Plus", "");

            timeRegTimeMap.put(plusName, timeRegTimeMap.get(plusName) + getSubmitDurationMinutes());
            String time = convertMinutesToHouersAndMinutes(timeRegTimeMap.get(plusName));
            String submitTimeTxt = "<html><FONT color=\"#000099\"><U>" + time + "</U></FONT></HTML>";


            timeRegSubmittedTimeMap.get(plusName).setText(submitTimeTxt);

            if (autoMinimizeCheckBox.isSelected() && getMinutesToSubmit() < getSubmitDurationMinutes()) {
                frame.setState(Frame.ICONIFIED);
            }

        } else if (e.getActionCommand().endsWith("Minus")) {
            String minusName = e.getActionCommand().replace("Minus", "");

            timeRegTimeMap.put(minusName, timeRegTimeMap.get(minusName) - getSubmitDurationMinutes());
            timeRegSubmittedTimeMap.get(minusName).setText(convertMinutesToHouersAndMinutes(timeRegTimeMap.get(minusName)));
        } else if (e.getActionCommand().startsWith("XP-") && e.getActionCommand().length() >= 4) {
            System.out.println(e.getActionCommand());
            String shortCutKey = e.getActionCommand();
            JTextField jTextField = jiraNumbersMap.get(shortCutKey.replace("XP-", ""));
            String jiraNumber = jTextField.getText().trim();
            openUri("http://features.nykreditnet.net/browse/" + jiraNumber);
        } else if (e.getActionCommand().startsWith(LIST_JIRAS_BUTTON_PRESSED)) {
            Object[] possibilities = ListOfIssues.getListOfInterestingJirasStr();
            String s = (String)JOptionPane.showInputDialog(
                    frame,
                    "Choose a task:",
                    "Task popup picker",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    possibilities[0]);

            JTextField jTextFieldJira = jiraNumbersMap.get(e.getActionCommand().replace(LIST_JIRAS_BUTTON_PRESSED , ""));
            jTextFieldJira.setText(ListOfIssues.getJiraFromStr(s));

            JTextField jTextFieldDesc = descriptionMap.get(e.getActionCommand().replace(LIST_JIRAS_BUTTON_PRESSED, ""));
            jTextFieldDesc.setText(ListOfIssues.getDescriptionFromStr(s));


        }



        handleSetTIme();
    }

    private static void openUri(String uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (Exception e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }

    protected static String convertMinutesToHouersAndMinutes(long minutes) {
        if (minutes < 0) {
            return String.valueOf(minutes);
        }

        String hours = String.valueOf(minutes / 60); //since both are ints, you get an int
        String minutesLeft = String.valueOf(minutes % 60);

        if (minutesLeft.length() == 1) {
            minutesLeft = "0" + minutesLeft;
        }

        if (hours.length() == 1) {
            hours = "0" + hours;
        }
        return hours + ":" + minutesLeft;
    }

    static protected void handleSetTIme() {
        Calendar dateTimeOfficeIn = getDateTimeOfficeIn();
        if (dateTimeOfficeIn == null) {
            return;
        }

        if (txtFieldInOffice.getText().contains("?")) {
            txtFieldInOffice.setBackground(new Color(255, 255, 0));
        } else {
            txtFieldInOffice.setBackground(new Color(0, 255, 0));
        }


        updateTimeInfoLabel();

        return;
    }

    /**
     * DO HANDLE NULL!!!
     * @return
     */
    private static Calendar getDateTimeOfficeOut() {
        Calendar dateTimeOfficeOut = Calendar.getInstance();
        try {
            Date dateTimeOfficeOutDate = sdf.parse(txtFieldOutOffice.getText().trim());
            dateTimeOfficeOut.setTime(dateTimeOfficeOutDate);

        } catch (ParseException e1) {
            System.out.println("no go getting office OUT: " + e1);

            e1.printStackTrace();
            txtFieldOutOffice.setBackground(new Color(255, 0, 0));
            return null;
        }
        txtFieldOutOffice.setBackground(new Color(255, 255, 255));
        return dateTimeOfficeOut;
    }

    /**
     * DO HANDLE NULL!!
     * @return
     */
    private static Calendar getDateTimeOfficeIn() {
        Calendar dateTimeOfficeIn = Calendar.getInstance();
        try {
            Date dateTimeOfficeInDate = sdf.parse(txtFieldInOffice.getText().trim());
            dateTimeOfficeIn.setTime(dateTimeOfficeInDate);

        } catch (ParseException e1) {
            System.out.println("no go getting office IN: " + e1);

            e1.printStackTrace();
            txtFieldInOffice.setBackground(new Color(255, 0, 0));
            return null;
        }
        return dateTimeOfficeIn;
    }

    private static void updateTimeInfoLabel() {
        long minutesToSubmit = getMinutesToSubmit();
        timeInfoLabel.setText("<html><font color='red'>Please submit " + convertMinutesToHouersAndMinutes(minutesToSubmit) +
                " minuts</font> (total: " + convertMinutesToHouersAndMinutes(getTotalSubmittedMinutesNotPauser()) + ")</html>");
    }

    protected static int getTotalSubmittedMinutes() {
        int totalSubmittedMinutes = 0;
        for (Integer t : timeRegTimeMap.values()) {
            totalSubmittedMinutes += t;
        }
        return totalSubmittedMinutes;
    }

    protected static int getTotalSubmittedMinutesNotPauser() {
        int totalSubmittedMinutes = 0;
        for (String s : timeRegTimeMap.keySet()) {
            if (!FROKOST_PAUSER.equalsIgnoreCase(s)) {
                totalSubmittedMinutes += timeRegTimeMap.get(s);
            }
        }
        return totalSubmittedMinutes;
    }

    protected static long getMinutesToSubmit() {
        Calendar now = getDateTimeOfficeOut();
        if (now == null) {
            return 0;
        }
        Calendar dateTimeOfficeIn = getDateTimeOfficeIn();
        if (dateTimeOfficeIn == null) {
            return -1l;
        }
        long minutesToSubmit = (now.getTimeInMillis() - dateTimeOfficeIn.getTimeInMillis()) / 1000 / 60;

        minutesToSubmit -= getAllreadySubmittetMinutes();
        return minutesToSubmit;
    }


    static long getAllreadySubmittetMinutes() {
        long duration = 0l;
        for (int time : timeRegTimeMap.values()) {
            duration += time;
        }

        return duration;
    }

    protected static void createGUI() {
        Gui buttonContentPane = new Gui();
        buttonContentPane.setOpaque(true);


        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("TimeRegForrest");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getRootPane().setDefaultButton(button);
        frame.setContentPane(buttonContentPane);

        frame.pack();
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null, "You normally do NOT want to exit this app, but just minimize it. Are you sure you want to exit? ", "Exit?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        try {
            File pathToFile = new File("C:\\projects\\TimeRegForrest\\doc\\icon.png");
            Image img = ImageIO.read(pathToFile);
            frame.setIconImage(img);
        } catch (Exception e) {
            System.out.println(e);
        }
        //frame.setSize(1000, 1400);

    }


    protected static void setGUIInForground() {
        frame.setVisible(true);
        int state = frame.getExtendedState();
        state &= ~JFrame.ICONIFIED;
        frame.setExtendedState(state);
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(false);
    }

    protected static void doBlink() throws InterruptedException {
        for (int i = 0 ; i < 5 ; i++) {
            txtFieldInOffice.setBackground(new Color(255, 0, 0));
            Thread.sleep(100);
            txtFieldInOffice.setBackground(new Color(0, 255, 0));
            Thread.sleep(100);
            txtFieldInOffice.setBackground(new Color(0, 255, 255));

            handleSetTIme();
        }
    }


}