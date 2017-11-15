import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

public class RainbowUI extends JPanel {
    private int width, height;
    private JButton start;
    private JButton exit;
    private JButton score;
    private JLabel background;
    public RainbowUI() {
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
        start = new JButton("");
        start.setBounds(33, 413, 129, 50);
        start.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_start.png")));
        start.setPressedIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_start_pressed.png")));
        start.setBorder(null);
        start.setBorderPainted(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReefWorld.sound.play("Resources/Sound_click.wav");
                ReefWorld.switchToRoom();
            }
        });
        this.add(start);

        exit = new JButton("");
        exit.setBounds(327, 413, 129, 50);
        exit.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_exit.png")));
        exit.setPressedIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_exit_pressed.png")));
        exit.setBorder(null);
        exit.setBorderPainted(false);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReefWorld.sound.play("Resources/Sound_click.wav");
                System.exit(0);
            }
        });
        this.add(exit);

        score = new JButton("");
        score.setBounds(180, 413, 129, 50);
        score.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_score.png")));
        score.setPressedIcon(new ImageIcon(ReefWorld.class.getResource("Resources/btn_score_pressed.png")));
        score.setBorder(null);
        score.setBorderPainted(false);
        score.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReefWorld.sound.play("Resources/Sound_click.wav");
                String str = getScores();
                if (str.length() == 0)
                    JOptionPane.showMessageDialog(null, "Still no play record.\nPlease finish at least one game.", "Scores", JOptionPane.PLAIN_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, str, "Scores", JOptionPane.PLAIN_MESSAGE);
            }
        });
        this.add(score);


        background = new JLabel();
        background.setIcon(new ImageIcon(ReefWorld.class.getResource("Resources/Title.png")));
        background.setBounds(0, 0, 800, 600);
        this.add(background);


    }

    public String getScores() {
        String str = "";
        HashMap<String, Integer> scores = new HashMap<>();
        try {
            File file = new File("score.txt");
            Scanner input = new Scanner(file);
            while(input.hasNext()) {
               scores.put(input.next(), Integer.parseInt(input.next()));
            }
            ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());
            System.out.println(list);
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return (o2.getValue() - o1.getValue());
                }
            });
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                str += (i + 1) + ". " + list.get(i).getKey() + " - " + list.get(i).getValue() + "\n";
                if (i > 10)
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Cannot open score file.");
        }

        return str;
    }
}
