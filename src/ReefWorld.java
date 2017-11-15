

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class ReefWorld extends JPanel implements Runnable, Observer {

    private Thread thread;
    private static final ReefWorld rainbowRoom = new ReefWorld();
    public static HashMap<String, Image> sprites = new HashMap<String, Image>();
    public static final GameClock clock = new GameClock();
    public static final GameSounds sound = new GameSounds();
    private static JFrame frame;
    private static RainbowUI ui;
    private static GameOverUI gameOverUI;
    private static ReefWorld room;

    private int width;
    private int height;
    private Point mapSize;
    private Graphics2D g2;
    private BufferedImage buffereImg;
    private Katch katch;
    private int[] controls = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_SPACE};
    private int levelIndicator;
    private String levels =  "Resources/level_03.txt";
    private int keyFrame;
    private int passCounter;
    private Image backGroImg1, backGRoImg2, wallImg,smallleg,bigleg,block_D, block_Life, block_Sol,block_Split,block1,block2,block3;
    protected Image block4, block5,block6,block7,katchImg,pop,wallMaskv,wallMaskH,solidBlockMask1,solidBlockMask2,title, map, congrats,gameOver;

    BufferedReader reader;
    int w, h;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<RainbowPop> pops;
    private ArrayList<GameObject> UI;


    private ReefWorld() {
        this.setFocusable(true);
    }


    public static Image getImage(String path) {
        Image img = null;
        try {
            img = ImageIO.read(ReefWorld.class.getResource(path));
        } catch (Exception e) {

            System.exit(1);
        }
        return img;
    }

    public static ReefWorld getRoom() {
        return rainbowRoom;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getFrameNumber() {
        return clock.getFrame();
    }

    public ListIterator<GameObject> getGameObjects() {
        return gameObjects.listIterator();
    }

    public ArrayList<RainbowPop> getPops() {
        return pops;
    }

    public ListIterator<RainbowPop> getPopsIt() {
        return pops.listIterator();
    }

    public Katch getKatch() {
        return katch;
    }

    public void addBackgrounds(Background background) {
        gameObjects.add(background);
    }

    public void addWalls(Wall wall) {
        gameObjects.add(wall);
    }

    public void addBlocks(Blocks block) {
        gameObjects.add(block);
    }

    public void addLegs(RainbowLegs leg) {
        gameObjects.add(leg);
    }

    public void addPops(RainbowPop pop) {
        pops.add(pop);
    }

    public void addWallMasks(RainbowWallMask mask) {
        gameObjects.add(mask);
    }

    public void addBlockMasks(RainbowSolidblockMask mask) {
        gameObjects.add(mask);
    }

    public void updatePassCounter(int x) {
        passCounter += x;
    }

    public void initialize() {
        setBackground(Color.BLACK);
        try {
            backGroImg1 = ImageIO.read(ReefWorld.class.getResource("Resources/Background1.png"));
            backGRoImg2 = ImageIO.read(ReefWorld.class.getResource("Resources/Wall.png"));
            wallImg = ImageIO.read(ReefWorld.class.getResource("Resources/Background2.png"));
            smallleg = ImageIO.read(ReefWorld.class.getResource("Resources/Bigleg_small_strip24.png"));
            bigleg = ImageIO.read(ReefWorld.class.getResource("Resources/Bigleg_strip24.png"));
            block_D = ImageIO.read(ReefWorld.class.getResource("Resources/Block_double.png"));
            block_Life = ImageIO.read(ReefWorld.class.getResource("Resources/Block_life.png"));
            block_Sol = ImageIO.read(ReefWorld.class.getResource("Resources/Block_solid.png"));
            block_Split = ImageIO.read(ReefWorld.class.getResource("Resources/Block_split.png"));
            block1 = ImageIO.read(ReefWorld.class.getResource("Resources/Block1.png"));
            block2 = ImageIO.read(ReefWorld.class.getResource("Resources/Block2.png"));
            block3 = ImageIO.read(ReefWorld.class.getResource("Resources/Block3.png"));
            block4 = ImageIO.read(ReefWorld.class.getResource("Resources/Block4.png"));
            block5 = ImageIO.read(ReefWorld.class.getResource("Resources/Block5.png"));
            block6 = ImageIO.read(ReefWorld.class.getResource("Resources/Block6.png"));
            block7 = ImageIO.read(ReefWorld.class.getResource("Resources/Block7.png"));
            katchImg = ImageIO.read(ReefWorld.class.getResource("Resources/Katch.png"));
            pop = ImageIO.read(ReefWorld.class.getResource("Resources/Pop_strip45.png"));
            wallMaskv = ImageIO.read(ReefWorld.class.getResource("Resources/WallMask_v.png"));
            wallMaskH = ImageIO.read(ReefWorld.class.getResource("Resources/WallMask_h.png"));
            solidBlockMask1= ImageIO.read(ReefWorld.class.getResource("Resources/Block_solid_mask1.png"));
            solidBlockMask2= ImageIO.read(ReefWorld.class.getResource("Resources/Block_solid_mask2.png"));
            title= ImageIO.read(ReefWorld.class.getResource("Resources/Title.png"));
            map= ImageIO.read(ReefWorld.class.getResource("Resources/level_01.txt"));
            gameOver= ImageIO.read(ReefWorld.class.getResource("Resources/gameover.jpg"));
            congrats= ImageIO.read(ReefWorld.class.getResource("Resources/congrats.jpeg"));




        } catch (Exception e) {
            System.out.println("Couldn't load resources");
            System.exit(1);
        }


        levelIndicator = 0;
        UI = new ArrayList<>();
        gameObjects = new ArrayList<>();
        pops = new ArrayList<>();
        mapSize = new Point(800, 600);
        startGame();
    }

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2;
        if (buffereImg == null || buffereImg.getWidth() != w || buffereImg.getHeight() != h) {
            buffereImg = (BufferedImage) createImage(w, h);
        }
        g2 = buffereImg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    public void drawFrame(int width, int height, Graphics2D g2) {
        ListIterator objIt = getGameObjects();

        GameObject obj = (GameObject) objIt.next();
        obj.update();
        obj.draw(g2, this);

        while (objIt.hasNext()) {
            obj = (GameObject) objIt.next();
            if (obj.isShown()) {
                obj.update();
                obj.draw(g2, this);
            }
        }

        if (pops.isEmpty() && katch.getNumLives() > 0) {
            addPops(new RainbowPop(katch.getX() + katch.getWidth() / 4 - 17, katch.getY() - 35, 0, 8, this, false));
            katch.setIsFirstFire();
        }

        objIt = getPopsIt();
        while (objIt.hasNext()) {
            try {
                obj = (RainbowPop) objIt.next();
            } catch (Exception e) {
                break;
            }
            if (obj.isShown()) {
                obj.update();
                obj.draw(g2, this);
            }
        }

        katch.update(new Observable(), new Object());
        katch.draw(g2, this);
    }

    public void loadLevel() {
        RoomGenerator(levels);
        if (!gameObjects.isEmpty()) {
            gameObjects.clear();
        }
        addBackgrounds(new Background(backGroImg1, 800, 600));
        load(levels);
    }



    public void RoomGenerator(String  filename) {
        String  fileN = filename;
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(ReefWorld.class.getResource(fileN).openStream()));
            line = reader.readLine();
            w = line.length();
            h = 0;
            while(line!=null){
                h++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void load(String filename) {
        ReefWorld rainbowRoom = ReefWorld.getRoom();
        try {
            reader = new BufferedReader(new InputStreamReader(ReefWorld.class.getResource(filename).openStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String line;
        try {
            line = reader.readLine();
            w = line.length();
            h=0;
            while (line != null) {
                for (int i = 0, n = line.length() ; i < n ; i++) {
                    char c = line.charAt(i);
                    if (c == '0') {
                        Wall wall = new Wall(i,h,wallImg);
                        rainbowRoom.addWalls(wall);
                    }
                    if (c == 'a') {
                        Blocks block = new Blocks("Resources/Block1.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'b') {
                        Blocks block = new Blocks("Resources/Block2.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'c') {
                        Blocks block = new Blocks("Resources/Block3.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'd') {
                        Blocks block = new Blocks("Resources/Block4.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'e') {
                        Blocks block = new Blocks("Resources/Block5.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'f') {
                        Blocks block = new Blocks("Resources/Block6.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'g') {
                        Blocks block = new Blocks("Resources/Block7.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'h') { // life block
                        Blocks block = new Blocks("Resources/Block_life.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'i') { // double block
                        Blocks block = new Blocks("Resources/Block_double.png", i, h, 200);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'j') {
                        Blocks block = new Blocks("Resources/Block_split.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'k') {
                        Blocks block = new Blocks("Resources/Block_solid.png", i, h, 100);
                        rainbowRoom.addBlocks(block);
                    }
                    if (c == 'x') {
                        RainbowLegs leg = new RainbowLegs("Resources/Bigleg_strip24.png", i, h);
                        rainbowRoom.addLegs(leg);
                        rainbowRoom.updatePassCounter(1);
                    }
                    if (c == 'y') {
                        RainbowLegs leg = new RainbowLegs("Resources/Bigleg_small_strip24.png", i, h);
                        rainbowRoom.addLegs(leg);
                        rainbowRoom.updatePassCounter(1);
                    }
                }
                h++;
                line = reader.readLine();
            }
            rainbowRoom.addWallMasks(new RainbowWallMask(0, 0, true));
            rainbowRoom.addWallMasks(new RainbowWallMask(0, 0, false));
            rainbowRoom.addWallMasks(new RainbowWallMask(800 - 20, 0, true));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void startGame() {
        keyFrame = 0;
        levelIndicator++;
        loadLevel();
        addKatch();
        addFirstPop();
    }

    public void addKatch() {
        katch = new Katch(katchImg, new Point(width / 2 - 40, 510), 8, controls);
    }

    public void addFirstPop() {
        if (!pops.isEmpty())
            pops.clear();
        addPops(new RainbowPop(width / 2 - 17, 510 - 35, 0, 8, this, false));
    }

    public void enterNextLevel() {
        startGame();
    }

    public void paint(Graphics g) {
        clock.tick();
        Graphics2D g2 = createGraphics2D(800, 600);
        drawFrame(820, 600, g2);
        g.drawImage(buffereImg, 0, 0, this);


        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score:" + katch.getScore(), 30, 560);

        for (int i = 0; i < katch.getNumLives(); i++) {
            int x = width - 70 - i * 40;
            int y = 530;
            g.drawImage(pop, x, y, x + 35, y + 35, 0, 0, 35, 35, this);
        }
        if (passCounter == 0 && (keyFrame == 0 || rainbowRoom.getFrameNumber() < keyFrame + 60)) {
            if (keyFrame == 0)
                keyFrame = rainbowRoom.getFrameNumber();
            Image img = congrats;
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            g.drawImage(img, width / 2 - imgWidth / 2, height / 2 - imgHeight / 2, this);

        }
        if (rainbowRoom.getFrameNumber() >= keyFrame + 60 && keyFrame != 0)
            enterNextLevel();
        if (passCounter == 0 && levelIndicator >=3)
            switchToUI();

        if (katch.getNumLives() == 0)
            switchToGameOverUI();


    }

    public void update(Observable obj, Object arg) {

    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(23);
            } catch (InterruptedException e) {
                break;
            }
        }
    }



    public static void switchToRoom() {
        ui.setVisible(false);
        gameOverUI.setVisible(false);
        room.setVisible(true);
        frame.getContentPane().add("Center", room);
    }

    public static void switchToUI() {
        ui.setVisible(true);
        gameOverUI.setVisible(false);
        room.setVisible(false);
        frame.getContentPane().add("Center", ui);
    }

    public static void switchToGameOverUI() {
        ui.setVisible(false);
        gameOverUI.setVisible(true);
        room.setVisible(false);
        frame.getContentPane().add("Center", gameOverUI);
        gameOverUI.getFocus();
    }
    public static void main(String[] args) {
        final ReefWorld game = ReefWorld.getRoom();
        room = game;
        frame = new JFrame("Super Rainbow Reef");
        ui = new RainbowUI();
        gameOverUI = new GameOverUI();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
            }
        });

        frame.getContentPane().add("Center", room);
        frame.getContentPane().add("Center", gameOverUI);
        frame.getContentPane().add("Center", ui);

        frame.pack();
        frame.setSize(new Dimension(800, 600));
        room.setDimensions(800, 600);
        room.initialize();
        room.start();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ReefWorld.sound.playLoop("Resources/Music.mp3");
    }
}
