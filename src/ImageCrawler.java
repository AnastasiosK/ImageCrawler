import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Luigi on 28/12/2017.
 */
public class ImageCrawler extends JFrame {
    private static String downloadfolder = "D:/images";

    public static void main(String[] args) throws IOException {

        JFrame f = new JFrame("Image downloader");
        JPanel panel = new JPanel();
        JButton button = new JButton("Download now");
        final JTextArea textField = new JTextArea(30, 60);

        f.setLayout(new BorderLayout());
        f.setSize(600, 600);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        button.setSize(50, 50);
        button.setVisible(true);
        panel.add(textField);
        f.add(panel);
        panel.add(button);
        textField.setText("Please give a correct url");


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strURL = textField.getText();
                Document document = null;
                try {
                    document = Jsoup
                            .connect(strURL)
                            .timeout(10 * 1000)
                            .get();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//select all img tags
                Elements imageElements = document.select("img");

                //iterate over each image
                for (Element imageElement : imageElements) {

                    //make sure to get the absolute URL using abs: prefix
                    String strImageURL = imageElement.attr("abs:src");

                    //download image one by one
                    downloadImage(strImageURL);

                }

            }

            private void downloadImage(String strImageURL) {

                //get file name from image path
                String strImageName =
                        strImageURL.substring(strImageURL.lastIndexOf("/") + 1);

                System.out.println("Saving: " + strImageName + ", from: " + strImageURL);

                try {

                    //open the stream from URL
                    URL urlImage = new URL(strImageURL);
                    InputStream in = urlImage.openStream();

                    byte[] buffer = new byte[4096];
                    int n = -1;

                    OutputStream os =
                            new FileOutputStream(downloadfolder + "/" + strImageName);

                    //write bytes to the output stream
                    while ((n = in.read(buffer)) != -1) {
                        os.write(buffer, 0, n);
                    }

                    //close the stream
                    os.close();

                    System.out.println("Image saved");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}