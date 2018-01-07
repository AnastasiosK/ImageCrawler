import javax.swing.*;

/**
 * Created by Luigi on 29/12/2017.
 */
public class Ui extends JFrame {
    public void main() {
        JFrame window = new JFrame("Image downloader");
        JTextField textField = new JTextField("Please enter a website");
        window.setVisible(true);
        window.setSize(400, 400);
        window.add(textField);
        ImageCrawler a = new ImageCrawler();
    }
}
