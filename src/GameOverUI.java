import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class GameOverUI extends JPanel {
    private int width, height;
    private JLabel fieldBackground;
    private JLabel background;
    private JTextField username;
    private JButton done;
    public GameOverUI() {
        this.setFocusable(true);
        initialize();
    }
    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void initialize() {
        this.setLayout(null);
        this.setDimensions(800, 600);
        this.setBackground(null);

        username = new JTextField("");
        username.setBounds(376, 310, 170, 26);
        username.setBackground(null);
        username.setBorder(null);
        username.setOpaque(false);
        this.add(username);

        done = new JButton("");
        done.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_done.png")));
        done.setPressedIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_done_pressed.png")));
        done.setBounds(368, 370, 58, 14);
        done.setBorder(null);
        done.setBorderPainted(false);
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordScore();
            }
        });
        this.add(done);

        fieldBackground = new JLabel();
        fieldBackground.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/gameover.jpg")));
        fieldBackground.setBounds(width / 2 - 416 / 2, height / 2 - 216 / 2, 416, 216);
        this.add(fieldBackground);

        background = new JLabel();
        background.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/Background1.png")));
        background.setBounds(0, 0, 800, 600);
        this.add(background);

    }
    public void getFocus() {
        username.requestFocus();
        username.requestFocusInWindow();
        username.grabFocus();
    }

    public void recordScore() {
        if (username.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please enter your name!");
            return;
        }
        if (username.getText().indexOf(' ') > 0) {
            JOptionPane.showMessageDialog(null, "Please do not include whitespace in your name!");
            return;
        }
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter(new File("score.txt"), true));
            out.write(username.getText() + " " + ReefWorld.getRoom().getKatch().getScore() + "\n");
            out.flush();
            out.close();
            ReefWorld.switchToUI();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
