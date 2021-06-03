package view;

import controller.RequestWindowController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutionException;

/**
 * VIEW in MVC: The GUI module that present the data.
 * */
public class RequestWindow extends JFrame {
    static RequestWindow frame;
    private JPanel contentPane;
    private JButton check;
    private JButton send;
    private JTextArea area;
    private JTextField word;
    private RequestWindowController controller;


    /**
     * Constructor.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     *
     * @param controller an instance of RequestWindowController class. May not be NULL
     */
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
                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        String wordToCheck = word.getText();
                        if (wordToCheck == null || wordToCheck.length() == 0) {
                            JOptionPane.showMessageDialog(RequestWindow.this, "Please Enter A Word!");
                            return "No Result Found";
                        }
                        boolean cached = controller.checkCache(wordToCheck);
                        String ret = null;
                        if (cached) {
                            if (0 == JOptionPane.showConfirmDialog(RequestWindow.this, "This word has been cached before, would you like to read from the cache?", "Options", 0)) {
                                ret = controller.retrieveData(wordToCheck, true);
                                return ret;
                            }
                        }
                        return controller.retrieveData(wordToCheck, false);

                    }

                    @Override
                    protected void done() {
                        String text;
                        try {
                            // Retrieve the return value of doInBackground.
                            text = get();
                            controller.updateView(text);
                            send.setEnabled(true);
                        } catch (InterruptedException e) {
                            controller.updateView("No Result Found");
                        } catch (ExecutionException e) {
                            controller.updateView("No Result Found");
                        }
                    }
                };
                worker.execute();
            }
        });

        this.send = new JButton("Send Email");
        this.send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        controller.reportData("Your Search Results for " + word.getText(), area.getText());
                        return true;
                    }

                    @Override
                    protected void done() {
                        boolean status = false;
                        try{
                            status = get();
                        } catch (InterruptedException e){
                            JOptionPane.showMessageDialog(RequestWindow.this, "Failed To Send Email");
                        } catch (ExecutionException e){
                            JOptionPane.showMessageDialog(RequestWindow.this, "Failed To Send Email");
                        }
                        send.setEnabled(false);
                    }
                };
                worker.execute();
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

    /**
     * update the data presentation in the GUI.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * the input string will be displayed on the text area of the GUI
     *
     * @param input text that want to display on the GUI. May not be NULL, but can be empty
     */
    public void notify(String input) {
        if (input == null){
            return;
        }
        this.area.setText(input);
    }

}
