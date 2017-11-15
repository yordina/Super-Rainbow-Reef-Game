import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

public class KeyControl extends Observable implements KeyListener {
    GameObject player;
    int[] keys;
    int moveFlag;

    public KeyControl() {}

    public KeyControl(GameObject player, int[] keys, Component world) {

        this.player = player;
        this.keys = keys;
        world.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == keys[0]) {
            setMove(0);
        } else if (code == keys[1]) {
            setMove(1);
        } else if (code == keys[2]) {
            setMove(2);
        } else if (code == keys[3]) {
            setMove(3);
        } else if (code == keys[4]) {
            setFire();
        }
        setChanged();
        notifyObservers();
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == keys[0]) {
            unSetMove(0);
        } else if (code == keys[1]) {
            unSetMove(1);
        } else if (code == keys[2]) {
            unSetMove(2);
        } else if (code == keys[3]) {
            unSetMove(3);
        } else if (code == keys[4]) {
            setUnFire();
        }

    }

    public void keyTyped(KeyEvent e) {

    }


    private void setMove(int flag) {
        switch (flag) {
            case 0: player.updateLeft(1); break;
            case 1: player.updateUp(1); break;
            case 2: player.updateRight(1); break;
            case 3: player.updateDown(1); break;
        }

    }

    private void unSetMove(int flag) {
        switch (flag) {
            case 0: player.updateLeft(0); break;
            case 1: player.updateUp(0); break;
            case 2: player.updateRight(0); break;
            case 3: player.updateDown(0); break;
        }
//
    }

    private void setFire() {
        player.setFire();
        setChanged();
        notifyObservers();
    }
    private void setUnFire() {
        player.setUnFire();
        setChanged();
        notifyObservers();
    }
}
