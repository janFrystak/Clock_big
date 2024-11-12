import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Scraper {
    private String username;
    private String password;
    private String url = "https://bakalari.oauh.cz/bakaweb/Timetable/Public/Actual/Class/2W";

    public Scraper(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void Scrape() throws Exception {

        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("a");
        for (Element element : elements) {

        }
    }


}
