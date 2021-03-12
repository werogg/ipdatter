import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class IpDatter {
    private JButton button1;
    private JPanel panel1;
    private JTextField textField1;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    public IpDatter() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JSONObject json = readJsonFromUrl("http://ip-api.com/json/" + textField1.getText());

                    JOptionPane.showMessageDialog(null, "Info about: " + textField1.getText() + "\n" +
                            "Status: " + json.getString("status") + "\n" +
                            "Country: " + json.getString("country") + "\n" +
                            "Region: " + json.getString("regionName") + "\n" +
                            "City: " + json.getString("city") + "\n" +
                            "Zip: " + json.getString("zip") + "\n" +
                            "Lat: " + json.getFloat("lat") + "\n" +
                            "Lon: " + json.getFloat("lon") + "\n" +
                            "ISP: " + json.getString("isp") + "\n"

                    );
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        JPanel ipDatter = new IpDatter().panel1;
        ipDatter.setSize(100, 400);
        frame.setContentPane(ipDatter);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
