package Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MyJFrame extends JFrame implements ActionListener {
    public JLabel landlordIcon;  //地主图标
    public JButton playStart;    //开始按钮
    public JButton[] buttons = new JButton[2]; //出牌按钮
    public JButton[] landlord = new JButton[4];  //抢地主按钮
    public JTextField time;                 //时间提示框
    public JTextField player;           //出牌人提示框
    public Vector<Card> landlordCards;   //地主牌
    public JPanel panel = new JPanel();
    public int result = 10;          //地主权值
    public int deru;                //出牌
    public boolean start;               //游戏开始
    public int dizhu = 0;
    public Vector<Card>[] gamer = new Vector[3];        //地主和电脑
    public Vector<Card> dizhuCard = new Vector<>(54);//牌组
    public newVector shou;           //上一个玩家出的牌
    public int chupairen;
    public int next;                    //轮到出牌的玩家
    public CardType type;               //
    public int total = 0;
    public int kktime;

    public MyJFrame(String name) {
        super(name);
        Init();
        setAssembly();
        for(int i = 0; i < 3; i++) {
            gamer[i] = new Vector<>();
        }
    }

    /*
    窗口初始化
     */
    public void Init() {
//        this.setSize(1920, 1080);
        this.setSize(1200, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(this.getOwner());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        ImageIcon background = new ImageIcon("image/bj.jpeg");
        JLabel label = new JLabel();
        label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        label.setIcon(background);
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        (( JPanel ) getContentPane()).setOpaque(false);
        panel.setLayout(null);
        this.setVisible(true);
    }

    /*
    设置按钮等组件
     */
    public void setAssembly() {
        //设置开始按键
        playStart = new JButton("开始游戏");
        playStart.addActionListener(this);
        playStart.setFont(new Font("宋体", Font.BOLD, 55));
        playStart.setBounds(600 - 300 / 2, 425 - 150 / 2, 300, 150);
        panel.add(playStart);
        //设置抢地主按键
        for(int i = 0; i < 4; i++) {
            if( i != 3 )
                landlord[i] = new JButton("" + (i + 1));
            else
                landlord[i] = new JButton("不抢");
            landlord[i].setBounds(300 + i * 150, 500, 150, 50);
            landlord[i].setFont(new Font("宋体", Font.BOLD, 20));
            landlord[i].addActionListener(this);
            panel.add(landlord[i]);
        }
        setLandlord();
        //设置出牌按键
        for(int i = 0; i < 2; i++) {
            if( i != 1 )
                buttons[i] = new JButton("不要");
            else
                buttons[i] = new JButton("出牌");
            buttons[i].setBounds(300 + i * 400, 400, 150, 75);
            buttons[i].setFont(new Font("宋体", Font.BOLD, 40));
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }
        //电脑提示
        setButtons();
        JLabel[] ai = new JLabel[2];
        for(int i = 0; i < 2; i++) {
            ai[i] = new JLabel("电脑" + (1 + i));
            ai[i].setBounds(50 + i * 1025, 300, 100, 50);
            ai[i].setFont(new Font("宋体", Font.BOLD, 35));
            panel.add(ai[i]);
        }
        //设置时间提示框
        time = new JTextField();
        time.setEnabled(false);
        time.setBounds(600 - 50, 175 - 25, 50, 50);
        time.setDisabledTextColor(Color.BLACK);
        time.setHorizontalAlignment(0);
        time.setFont(new Font("宋体", Font.BOLD, 30));
        time.setVisible(false);
        panel.add(time);
        //设置出牌人提示框
        player = new JTextField();
        player.setEnabled(false);
        player.setBounds(425, 100, 300, 30);
        player.setDisabledTextColor(Color.BLACK);
        player.setHorizontalAlignment(0);
        player.setFont(new Font("宋体", Font.PLAIN, 30));
        player.setVisible(true);
        panel.add(player);
        //设置地主图标
        landlordIcon = new JLabel();
        landlordIcon.setIcon(new ImageIcon("image/dizhu.gif"));
        landlordIcon.setBounds(0, 0, landlordIcon.getIcon().getIconWidth(), landlordIcon.getIcon().getIconHeight());
        landlordIcon.setVisible(false);
        panel.add(landlordIcon);
    }

    /*
    绑定按钮组件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //绑定抢地主键
        if( e.getSource() == landlord[0] ) {
            result = 1;
            b(0);
        } else if( e.getSource() == landlord[1] ) {
            result = 2;
            b(1);
        } else if( e.getSource() == landlord[2] ) {
            result = 3;
            b(2);
        } else if( e.getSource() == landlord[3] ) {
            result = - 1;
            b(3);
        }
        //绑定出牌键
        else if( e.getSource() == buttons[0] ) {        //不要
            deru = - 1;                                 //跳到下一位
        } else if( e.getSource() == buttons[1] ) {      //出牌
            deru = 1;
        }
        //绑定开始键
        else if( e.getSource() == playStart ) {
            start = true;
        }
    }

    private void b(int num) {
        for(int i = 0; i <= num; i++) {
            landlord[i].setEnabled(false);
        }
        landlord[3].setEnabled(false);
    }

    /*
        设置倒计时
         */
    public void Second(int i) {
        time.setVisible(true);
        int t = i;
        for(int i1 = i * 10; i1 >= 0; i1--) {
            if( result == - 1 || result == 3 || result == 2 || result == 1 ) {
                kktime = t;
                break;
            } else if( deru != 0 ) {
                kktime = t;
                break;
            }
            time.setText("" + t);
            if( i1 % 10 == 0 )
                t--;
            try {
                Thread.sleep(100);
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        time.setVisible(false);
    }

    /*
    显示地主图标
     */
    public void getLandlordIcon() {
        landlordIcon.setVisible(true);
    }

    /*
    隐藏地主图标
     */
    public void setLandlordIcon() {
        this.landlordIcon.setVisible(false);
    }

    public JButton getPlayStart() {
        return playStart;
    }

    /*
    隐藏开始游戏按钮
     */
    public void setPlayStart() {
        this.playStart.setVisible(false);
    }

    /*
    显示出牌按钮
     */
    public void getButtons() {
        deru = 0;
        for(int i = 0; i < 2; i++) {
            buttons[i].setVisible(true);
        }
    }

    /*
    隐藏出牌按钮
     */
    public void setButtons() {
        for(int i = 0; i < 2; i++) {
            buttons[i].setVisible(false);
        }
    }

    /*
    显示抢地主按钮
     */
    public void getLandlord() {
        for(int i = 0; i < landlord.length; i++) {
            landlord[i].setVisible(true);
        }
    }

    /*
    隐藏抢地主按钮
     */
    public void setLandlord() {
        for(int i = 0; i < landlord.length; i++) {
            landlord[i].setVisible(false);
        }
    }

    /*
    隐藏出牌人提示框
     */
    public void getPlayer() {
        player.setVisible(false);
    }

    /*
    更改提示人并显示提示框
     */
    public void setPlayer(String player) {
        this.player.setVisible(true);
        this.player.setText(player);
    }

    /*
    将地主牌移动到指定位置并显示
     */
    public void getLandlordCards() {
        for(int i = 0; i < landlordCards.size(); i++) {
            landlordCards.get(i).setLocation(575 - 72 - 36 + i * 72, 0);
            landlordCards.get(i).setVisible(true);
            panel.add(landlordCards.get(i));
        }
    }

    /*
    隐藏地主牌
     */
    public void hideLandlordCards() {
        for(int i = 0; i < landlordCards.size(); i++) {
            landlordCards.get(i).setVisible(false);
        }
    }

    /*
    从外面获取地主牌
     */
    public void setLandlordCards(Vector<Card> landlordCards) {
        this.landlordCards = landlordCards;
    }

    /*
    返回面板容器
     */
    public JPanel getPanel() {
        return panel;
    }

    /*
    获取面板容器
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

}
