import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TheClock extends JFrame {
    private JTextField textField1;
    private JPanel panel;
    private boolean color = true;
    /*private boolean toRepeat = true;

    public boolean isToRepeat() {
        return toRepeat;
    }

    public void setToRepeat(boolean toRepeat) {
        this.toRepeat = toRepeat;
    }*/
    public TheClock(){
        setContentPane(panel);
        setVisible(true);
        setTitle("TIME");
        setSize(875, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textField1.setEditable(false);
        textField1.setPreferredSize(new Dimension(500, 100)); // Width: 300px, Height: 50px
        textField1.setHorizontalAlignment(SwingConstants.CENTER);
        textField1.setFont(new Font("Arial", Font.PLAIN, 75));
    }

    public LocalTime getClosestTime(){
        LocalTime[] timeTable = {
                LocalTime.of(7, 45),  // 7:45
                LocalTime.of(8, 40),  // 8:40
                LocalTime.of(9, 30),  // 9:30
                LocalTime.of(10, 35), // 10:35
                LocalTime.of(11, 30), // 11:30
                LocalTime.of(12, 25), // 12:25
                LocalTime.of(13, 20), // 13:20
                LocalTime.of(14, 10), // 14:10
                LocalTime.of(15, 0)
        };

        LocalTime currTime = LocalTime.now();
        for(LocalTime time : timeTable){
            if(time.isAfter(currTime)){
                return time;
            }
        }
        return null;
    }
    public void displayTime(){
        LocalTime displayedTime = getClosestTime();
        if (displayedTime != null) {
            Duration duration = Duration.between(LocalTime.now(),displayedTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60; // Remainder of minutes after hours
            long seconds = duration.getSeconds() % 60; // Remainder of seconds after minutes

            if(displayedTime == LocalTime.of(0, 0, 0)) {
                textField1.setText("GO GO GO");

            }
            textField1.setText(hours + ":" + minutes + ":" + seconds);

        }
        else {

            textField1.setText("GO TO SLEEP WEIRDO");
            if (color){
                setBackground(new Color(238, 130, 238));
            }
            else {
                setBackground(new Color(255, 255, 255));
            }
            color = !color;
        }
    }
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        TheClock clock = new TheClock();
        clock.setVisible(true);

        // Schedule the task to run every 1 second (initial delay of 0 seconds)
        scheduler.scheduleAtFixedRate(clock::displayTime, 0, 1, TimeUnit.SECONDS);


    }
}
