import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Devine mon nombre secret");
        mainFrame.setSize(400, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        Random randomInt = new Random();
        int secretNumber = randomInt.nextInt(5) + 1;

        JLabel label = new JLabel("Devine mon nombre entre 1 et 5");
        panel.add(label);

        for (int i = 1; i <= 5; i++){
            JButton guessButton = new JButton(String.valueOf(i));
            int guess = i;
            guessButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (guess == secretNumber){
                        JOptionPane.showMessageDialog(mainFrame, "Bravo");
                    }else {
                        JOptionPane.showMessageDialog(mainFrame, "non");
                    }

                }

            } );
            panel.add(guessButton);

        }
        JButton jokeButton = new JButton("Chuck Norris");
        jokeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String joke = fetchChuckNorrisJoke();
                if (joke != null) {
                    JOptionPane.showMessageDialog(mainFrame, joke);
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Impossible de récupérer une blague.");
                }
            }
        });
        panel.add(jokeButton);

        mainFrame.add(panel);
        mainFrame.setVisible(true);
    }

    private static String fetchChuckNorrisJoke(){
        try{
            URL url = new URL("https://api.chucknorris.io/jokes/random");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            String response = content.toString();
            JSONObject joke = new JSONObject(response);
            return joke.getString("value");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}