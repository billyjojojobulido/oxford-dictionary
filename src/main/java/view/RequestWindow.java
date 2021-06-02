package view;

import controller.RequestWindowController;
import org.json.simple.JSONObject;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RequestWindow extends JFrame {
    static RequestWindow frame;
    private JPanel contentPane;
    private JButton check;
    private JButton send;
    private JTextArea area;
    private JTextField word;
    private RequestWindowController controller;

    class ThreadSender implements Runnable {
        @Override
        public void run() {
            controller.reportData("Your Search Results for " + word.getText(), area.getText());
            send.setEnabled(false);
        }

    }

    class ThreadChecker implements Runnable {
        @Override
        public void run() {
            String wordToCheck = word.getText();
            if (wordToCheck == null || wordToCheck.length() == 0) {
                JOptionPane.showMessageDialog(RequestWindow.this, "Please Enter A Word!");
                return;
            }
            boolean cached = controller.checkCache(wordToCheck);
            String ret = null;
            if (cached) {
                if (0 == JOptionPane.showConfirmDialog(RequestWindow.this, "This word has been cached before, would you like to read from the cache?", "Options", 0)) {
                    ret = controller.retrieveData(wordToCheck, true);
                    controller.updateView(ret);
                    send.setEnabled(true);
                    return;
                }
            }
            ret = controller.retrieveData(wordToCheck, false);
            controller.updateView(ret);
            send.setEnabled(true);
        }
    }


    /**
     * setting up the interface frame
     **/
    public RequestWindow(RequestWindowController controller) {
        this.controller = controller;

        /* setting the default value */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel statusLabel = new JLabel("Oxford Dictionary");

        statusLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));


        this.area = new JTextArea("NULL", 30, 30);
        this.area.setForeground(Color.BLACK);
        this.area.setFont(new Font("Arial", Font.BOLD, 13));
        this.area.setBackground(Color.WHITE);
        JScrollPane items = new JScrollPane(this.area);    // put the Text Area Component in Scroll Pane

        JLabel nameLabel = new JLabel("Enter a word");

        word = new JTextField();
        word.setColumns(10);


        this.check = new JButton("Look Up");
        this.check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread checkerThread = new Thread(new ThreadChecker());
                checkerThread.start();
            }
        });

        this.send = new JButton("Send Email");
        this.send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread senderThread = new Thread(new ThreadSender());
                senderThread.start();
            }
        });
        this.send.setEnabled(false);


        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(120)
                                                .addComponent(statusLabel))
                                        .addGap(50)
                                        .addComponent(items, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
                                        .addGap(100)
                                        .addComponent(nameLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(100)
                                                .addComponent(word, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                                        )

                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(140)
                                                .addComponent(check)
                                        )
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(133)
                                                .addComponent(send)
                                        )

                                        .addGap(60))
                                .addContainerGap(111, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(statusLabel)
                                .addGap(30)
                                .addComponent(items, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(nameLabel)
                                        .addComponent(word, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup())
                                        .addComponent(check)
                                )
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup())
                                        .addComponent(send)
                                )
                                .addContainerGap(51, Short.MAX_VALUE))
        );
        contentPane.setLayout(layout);
    }

    public void notify(String input) {
        this.area.setText(input);
    }

}
