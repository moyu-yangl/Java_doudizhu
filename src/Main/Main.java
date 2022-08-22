package Main;

import Common.Commen;
import Common.MyJFrame;
import Common.User;

import javax.swing.*;

public class Main {
    //    public static Vector<Card> cards = new Vector<>();
//    public static boolean flag = false;

    public static void main(String[] args) {
        Start();
    }

    public static void Start() {
        MyJFrame myJFrame = new MyJFrame("斗地主");
        JPanel panel = myJFrame.getPanel();
        while( ! myJFrame.start ) {
            try {
                Thread.sleep(10);
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        myJFrame.setPlayStart();
        Commen.GameStart(myJFrame.dizhuCard, panel);
        new User(myJFrame).start();
    }
}
