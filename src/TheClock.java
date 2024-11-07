import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TheClock extends JFrame {
    private JTextField textField1;
    private JPanel panel;
    private JButton resetButton;
    private JButton addButton;
    private JButton selectButton;
    private JCheckBox breakCheck;
    private JLabel whatLabel;
    private boolean color = true;
    //private boolean defaultTable = true;
    private ArrayList<LocalTime> breakTable = new ArrayList<>(Arrays.asList(
            LocalTime.of(7, 0),
            LocalTime.of(7, 55),
            LocalTime.of(8, 45),
            LocalTime.of(9, 50),
            LocalTime.of(10, 45),
            LocalTime.of(11, 40),
            LocalTime.of(12, 35),
            LocalTime.of(13, 25),
            LocalTime.of(14, 15)
            ));
    private ArrayList<LocalTime> timeTable = new ArrayList<>();
    private ArrayList<LocalTime> additionTable = new ArrayList<>();
    private ArrayList<LocalTime> lessonTable = new ArrayList<>(Arrays.asList(
            LocalTime.of(7, 45),  // 7:45
            LocalTime.of(8, 40),  // 8:40
            LocalTime.of(9, 30),  // 9:30
            LocalTime.of(10, 35), // 10:35
            LocalTime.of(11, 30), // 11:30
            LocalTime.of(12, 25), // 12:25
            LocalTime.of(13, 20), // 13:20
            LocalTime.of(14, 10), // 14:10
            LocalTime.of(15, 0)
    ));
    private LocalTime displayedTime;


    /*
* 1.More imaginatime
* 2.Makes you do better backflips
* No firearm!!
* 4.you can draw
* friend :)
*
*
* he kicked her in the head with big soccer shoes :(
* */





    public TheClock() {
        actuateTable(breakCheck.isSelected());
        displayedTime = getClosestTime();


        setContentPane(panel);
        setVisible(true);
        setTitle("TIME");
        setSize(875, 500);
        panel.setBackground(new Color(255,255,255));
        textField1.setBackground(new Color(255,255,255));
        textField1.setForeground(new Color(0,0,0));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textField1.setEditable(false);
        textField1.setPreferredSize(new Dimension(500,300)); // Width: 300px, Height: 50px
        textField1.setHorizontalAlignment(SwingConstants.CENTER);
        textField1.setFont(new Font("Arial", Font.ITALIC, 75));
        resetButton.addActionListener(e -> reset());
        addButton.addActionListener(e -> showTimeInput());
        selectButton.addActionListener(e ->selectTimeInput());
        breakCheck.addActionListener(e -> {

            actuateTable(breakCheck.isSelected());
            reset();
        });
        //timeTable = lessonTable;

    }
    public void actuateTable(boolean biss){
        if(!biss){
            timeTable = lessonTable;
            timeTable.addAll(additionTable);
            whatLabel.setText("Time till break");
        }
        else {
            timeTable = breakTable;
            timeTable.addAll(additionTable);
            whatLabel.setText("Time till lesson");
        }
    }
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        TheClock clock = new TheClock();
        clock.setVisible(true);

        // Schedule the task to run every 1 second (initial delay of 0 seconds)
        scheduler.scheduleAtFixedRate(clock::displayTime, 0,  250, TimeUnit.MILLISECONDS);
    }
    public void reset(){
        displayedTime = getClosestTime();
        resetColour(textField1);
    }

    public void resetColour(JTextField textField){
        panel.setBackground(new Color(255,255,255));
        textField.setBackground(new Color(255,255,255));
        textField.setForeground(new Color(0,0,0));
    }

    public LocalTime getClosestTime(){


        LocalTime currTime = LocalTime.now();
        for(LocalTime time : timeTable){
            if(time.isAfter(currTime)){
                return time;
            }
        }
        return null;
    }

    public void displayTime(){



        if (displayedTime != null) {
            Duration duration = Duration.between(LocalTime.now(),displayedTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;


            if(seconds >= 0) {
                if(hours > 0){
                    textField1.setText(convertTime(hours) + ":" + convertTime(minutes) + ":" + convertTime(seconds));

                }
                else textField1.setText(convertTime(minutes) + ":" + convertTime(seconds));
                /*if(seconds<=58){
                    blink(textField1, Color.BLACK, Color.ORANGE, true);
                }
                if(seconds<=10){
                    blink(textField1, Color.BLACK, Color.RED, true);
                }*/

            }
            else  {
                textField1.setText("GO GO GO");
                blink(textField1, new Color(238, 130, 238), Color.black, true);


            }
        }
        else {

            textField1.setText("GO TO SLEEP WEIRDO");

        }
    }
    public String convertTime(long time){
        if(time <10){
            return "0"+time;
        }
        else return String.valueOf(time);

    }
    public void blink(JTextField field, Color color1, Color color2, boolean on){
        if(on) {

            if (this.color) {
                panel.setBackground(color1);
                field.setBackground(color1);
                field.setForeground(color2);
            } else {
                panel.setBackground(color2);
                field.setBackground(color2);
                field.setForeground(color1);
            }
            this.color = !this.color;

    }else resetColour(textField1);
    }
    public void selectTimeInput(){
        JDialog dialog = new JDialog((Frame) null, "Enter Time", true);
        dialog.setSize(300, 500);
        dialog.setLayout(new FlowLayout());
        //actuateTable(breakCheck.isSelected());
        sortList(timeTable);
        for(LocalTime time: timeTable){
            JButton button = new JButton(String.valueOf(time));
            dialog.add(button);
            button.addActionListener(e -> {
                reset();
                displayedTime = time;

            });
        }

        JButton submitButton = new JButton("Done");
        submitButton.addActionListener(e -> dialog.dispose());
        dialog.add(submitButton);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }
    public void showTimeInput(){

        JDialog dialog = new JDialog((Frame) null, "Enter Time", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Time (HH:MM):");
        JTextField timeField = new JTextField(10);

        JButton submitButton = new JButton("Submit");


        submitButton.addActionListener(e -> {
                String enteredTime = timeField.getText();

                if(isValidTime(enteredTime)){
                    String[] block = enteredTime.split(":");
                    int h = Integer.parseInt(block[0]);
                    int m = Integer.parseInt(block[1]);
                    //int s = Integer.parseInt(block[2]);
                    System.out.println(h + " " + m /*+ " "+s*/);
                    additionTable.add(LocalTime.of(h,m/*,s*/));
                    actuateTable(breakCheck.isSelected());
                    System.out.println(LocalTime.of(h,m/*,s*/));
                }
                else {
                    System.out.println("Bad Time " + enteredTime);
                }


                dialog.dispose();

        });


        dialog.add(label);
        dialog.add(timeField);
        dialog.add(submitButton);


        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    public void sortList(ArrayList<LocalTime> list){
        //Comparator<LocalTime> default_compare = new Comparator<LocalTime>() {}
        list.sort((o1, o2) -> 0);
    }
    public boolean isValidTime(String time){
        //      Test 1
        if (time != null & time.length() < 8 & time.length() > 6) {
            System.out.println("Test1 failed");
            return false;
        }

        //      Test 2
        /*if (time.contains(":")) {
            System.out.println("Test2 failed");
            return false;
        }*/

        try {
            // Parse the hours and minutes
            String[] block = time.split(":");
            int hours = Integer.parseInt(block[0]);
            int minutes = Integer.parseInt(block[1]);
            //int sec = Integer.parseInt(block[3]);

        //      Test 4
            if (hours < 0 || hours > 23  || minutes < 0 || minutes > 59 /*|| sec >59*/) {
                System.out.println("Test4 failed");
                return false;
            }
        //      Test 3
        } catch (NumberFormatException e) {
            System.out.println("Test3 failed");
            return false;
        }


        return true;
    }


}
