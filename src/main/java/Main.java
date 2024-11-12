import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        TheClock clock = new TheClock();
        clock.setVisible(true);

        // Schedule the task to run every 1 second (initial delay of 0 seconds)
        scheduler.scheduleAtFixedRate(clock::displayTime, 0,  250, TimeUnit.MILLISECONDS);
    }
}
