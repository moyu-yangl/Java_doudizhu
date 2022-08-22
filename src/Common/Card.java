package Common;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Card extends JLabel {
    private int flower;
    private int score;
    private String path;
    private String name;
    private final String path2 = "image/rear.gif";
    private boolean jump = false;
    private boolean rear = false;
    private boolean kq = false;

    public Card(Card card) {
        this.flower = card.getFlower();
        this.score = card.getScore();
        this.path = card.path;
        this.name = card.name;
        ImageIcon icon = new ImageIcon(this.path);
        this.setIcon(icon);
    }

    public Card(int flower, int score) {
        this.flower = flower;
        this.score = score;
        this.path = "image/" + flower + "-" + score + ".gif";
        this.name = "" + flower + "-" + score;
        this.jump = false;
        this.rear = false;
        ImageIcon icon = new ImageIcon(this.path);
        this.setIcon(icon);
    }

    public Card(String path, int score) {
        this.path = path;
        this.score = score;
        this.flower = 5;
        this.name = "" + flower + "-" + score;
        this.setIcon(new ImageIcon(this.path2));
        this.jump = false;
        this.rear = false;
    }

    public String Path() {
        return this.name;
    }

    public boolean isKq() {
        return kq;
    }

    public void setKq(boolean kq) {
        this.kq = kq;
    }

    public boolean isRear() {
        return rear;
    }

    public void setPath2() {
        this.setIcon(new ImageIcon(path2));
    }

    public void setRear(boolean rear) {
        this.rear = rear;
    }

    public void setPath() {
        this.setIcon(new ImageIcon(this.path));
    }

    public int getFlower() {
        return flower;
    }

    public void setFlower(int flower) {
        this.flower = flower;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void UpOrDown() {
        Card card = this;
        card.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int h = 80;
//                int w = 72;
//                if( ! card.isJump() && ! card.isRear() ) {
//                    card.setLocation(card.getX(), card.getY() - h / 2);
//                    card.setJump(true);
//                } else if( card.isJump() && ! card.isRear() ) {
//                    card.setLocation(card.getX(), card.getY() + h / 2);
//                    card.setJump(false);
//                }
//            }

            @Override
            public void mousePressed(MouseEvent e) {
                int h = 80;
                int w = 72;
                if( ! card.isJump() && ! card.isRear() ) {
                    card.setLocation(card.getX(), card.getY() - h / 2);
                    card.setJump(true);
                } else if( card.isJump() && ! card.isRear() ) {
                    card.setLocation(card.getX(), card.getY() + h / 2);
                    card.setJump(false);
                }
            }
        });

    }
}
