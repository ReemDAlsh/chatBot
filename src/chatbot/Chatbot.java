/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatbot;

/**
 *
 * @author reemy
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.lang.Math;

public class Chatbot extends JFrame implements KeyListener {

    JPanel p = new JPanel();
    JTextArea dialog = new JTextArea(20, 50);
    JTextArea input = new JTextArea(1, 50);
    JScrollPane scroll = new JScrollPane(dialog,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    String[][] chatBot = {
        //standard greetings
        {"hi", "hello", "hola", "ola", "howdy"},
        {"hi what you want me to call you"},
        //question greetings

        {"how are you", "how r you", "how r u", "how are u"},
        {"good", "doing well"},
        //introduce 
        {"i'm", "my name"},
        {"OK, it's my pleasure"},
        //default
        {"shut up", "you're bad", "noob", "stop talking",
            "(the ChatBot is unavailable, due to LOL)"},};

    String[][] verbs = {
        {"is", "'re"},
        {"was", "'re"},
        {"think", " think"},
        {"s", "'re"},
        {"'re", "'re"}
    };

    public static void main(String[] args) {
        new Chatbot();
        // TODO code application logic here
    }

    public Chatbot() {
        super("Chat Bot");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        dialog.setEditable(false);
        input.addKeyListener(this);

        p.add(scroll);
        p.add(input);
        p.setBackground(new Color(100, 100, 100));
        add(p);

        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(false);
            //-----grab quote-----------
            String quote = input.getText();
            input.setText("");
            addText("-->You:\t" + quote);
            quote.trim();
            while (quote.charAt(quote.length() - 1) == '!'
                    || quote.charAt(quote.length() - 1) == '.'
                    || quote.charAt(quote.length() - 1) == '?') {
                quote = quote.substring(0, quote.length() - 1);
            }
            quote.trim();
            byte response = 0;
            /*
			0:we're searching through chatBot[][] for matches
			1:we didn't find anything
			2:we did find something
             */
            //-----check for matches----
            int j = 0;//which group we're checking
            while (response == 0) {
                if (inArray(quote.toLowerCase(), chatBot[j * 2])) {
                    response = 2;
                    int r = (int) Math.floor(Math.random() * chatBot[(j * 2) + 1].length);
                    addText("\n-->Michael\t" + chatBot[(j * 2) + 1][r]);
                }
                j++;
                if (j * 2 == chatBot.length - 1 && response == 0) {
                    response = 1;
                }
            }

            if (response == 1) {
                String quoteWords[] = quote.split("[ ']");
                int c = counter(quoteWords);
                if (c != -1) {
                    String ext = quote.split(verbs[c][0])[1];
                    addText("\n-->My ChatBot:\t You" + verbs[c][1] + ext);
                    response = 2;
                }
            }

            //-----default--------------
            if (response == 1) {
                int r = (int) Math.floor(Math.random() * chatBot[chatBot.length - 1].length);
                addText("\n-->My ChatBot\t" + chatBot[chatBot.length - 1][r]);
            }
            addText("\n");
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            input.setEditable(true);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void addText(String str) {
        dialog.setText(dialog.getText() + str);
    }

    public boolean inArray(String in, String[] str) {
        boolean match = false;
        for (int i = 0; i < str.length; i++) {
            if (str[i].equals(in)) {
                match = true;
            }

        }
        return match;

    }

    public int counter(String str[]) {
        int verbID = -1;
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < verbs.length; j++) {
                if (str[i].equals(verbs[j][0])) {
                    verbID = j;

                }
            }
        }
        return verbID;
    }
}
