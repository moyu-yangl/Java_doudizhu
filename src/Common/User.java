package Common;

import java.util.Objects;
import java.util.Vector;

public class User extends Thread {
    //    private Vector<Card> cards;          //手牌
    private MyJFrame myJFrame;            //控制背景

    public User(MyJFrame jFrame) {
        this.myJFrame = jFrame;
    }

    @Override
    public void run() {
        int win = 0;
        Commen.Draw(myJFrame);          //发牌
        Commen.Landlord(myJFrame);      //抢地主
        Commen.getLandCard(myJFrame, myJFrame.dizhu);   //获得地主牌
        for(Card card : myJFrame.gamer[0])              //牌可点击
            if( ! card.isKq() ) {
                card.UpOrDown();
                card.setKq(true);
            }
        //获得手中牌所组成的牌型
//        Cards cards = Commen.ChaiFen(myJFrame.gamer[0]);
//        System.out.println("王炸：" + cards.bs);
//        System.out.println("单牌：" + cards.d1);
//        System.out.println("对子：" + cards.d2);
//        System.out.println("砍子：" + cards.d3);
//        System.out.println("炸弹：" + cards.d4);
//        System.out.println("飞机：" + cards.d111222);
//        System.out.println("单顺：" + cards.d123);
//        System.out.println("双顺：" + cards.d112233);
        int key=0;
        while( true ) {
            //如果是人出牌
            if( myJFrame.next % 3 == 0 ) {
                //出牌按钮出现
                myJFrame.getButtons();
                if(myJFrame.chupairen==0){
                    myJFrame.buttons[0].setEnabled(false);
                }
                //30秒出牌时间
                myJFrame.player.setText("轮到你出牌");
                myJFrame.kktime=30;
                myJFrame.Second(30);
                if( myJFrame.deru == 1 ) {          //确定出牌
                    while(true){
                        if(chuCard(0)||myJFrame.kktime<=0)
                            break;
                        myJFrame.deru=0;
                        myJFrame.Second(myJFrame.kktime);
                    }
                    myJFrame.buttons[0].setEnabled(true);
                } else {
                    myJFrame.player.setText("你选择不要");
                }
                myJFrame.deru = 0;
                //手牌重绘
                Commen.Repaint(myJFrame.gamer[0], myJFrame.panel, 0);
                //关闭出牌按钮
                myJFrame.setButtons();
                //轮到下一个人
                myJFrame.next++;
            } else if( myJFrame.next % 3 == 1 ) {
                //如果是左边电脑出牌
//                System.out.println(myJFrame.next);
                myJFrame.player.setText("轮到电脑" + 1 + "出牌");
                myJFrame.Second(2);
                if( myJFrame.chupairen == 1 ) {  //如果上一次出的牌还是自己出的，那么接着出新牌
                    AICard1(1);
                } else if( myJFrame.dizhu == 1 ) {       //如果上一次的牌不是自己出的，且自己是地主，那么进行出牌
                    AICard2(1);
                } else if( myJFrame.chupairen == myJFrame.dizhu ) {//如果自己不是地主，且上一次牌是地主出的，那么进行出牌
                    AICard2(1);
                }
                //如果上次出牌是队友农民出的，那么不进行出牌

                //手牌重绘
                Commen.Repaint(myJFrame.gamer[1], myJFrame.panel, 1);
                myJFrame.next++;
            } else if( myJFrame.next % 3 == 2 ) {
                //如果是右边电脑出牌
//                System.out.println(myJFrame.next);
                myJFrame.player.setText("轮到电脑" + 2 + "出牌");
                myJFrame.Second(2);
                if( myJFrame.chupairen == 2 ) {  //如果上一次出的牌还是自己出的，那么接着出新牌
                    AICard1(2);
                } else if( myJFrame.dizhu == 2 ) {       //如果上一次的牌不是自己出的，且自己是地主，那么进行出牌
                    AICard2(2);
                } else if( myJFrame.chupairen == myJFrame.dizhu ) {//如果自己不是地主，且上一次牌是地主出的，那么进行出牌
                    AICard2(2);
                }
                //手牌重绘
                Commen.Repaint(myJFrame.gamer[2], myJFrame.panel, 2);
                myJFrame.next++;
            }
            myJFrame.Second(1);
            //判断胜利
            if( isWin() )
                break;
        }
        myJFrame.time.setText("本次游戏已经结束，即将退出游戏");
        myJFrame.Second(5);
        System.exit(0);
    }


    /*
    判断胜利
     */
    public boolean isWin() {
        for(int i = 0; i < 3; i++) {
            if( myJFrame.gamer[i].size() == 0 ) {
                if( i == myJFrame.dizhu )
                    myJFrame.player.setText("地主获胜");
                else
                    myJFrame.player.setText("农民获得胜利");
                return true;
            }
        }
        return false;
    }

    /*
    玩家进行出牌
     */
    public boolean chuCard(int flag) {
//        Vector<Card> cards = new Vector<>();
        newVector cards = new newVector();
        for(Card card : myJFrame.gamer[0]) {
            if( card.isJump() )
                cards.add(card);
        }
        CardType type = Commen.cardType(cards);
        int total = Commen.getTotal(cards);
        System.out.println("" + type);
        if( myJFrame.chupairen != flag && !Objects.equals(type,CardType.c0)) {            //如果上一次出牌的人不是我
            if( Commen.typeCompare(cards, myJFrame.shou) ) {
                Clear();
                myJFrame.type = type;
                for(Card card : cards)
                    myJFrame.gamer[flag].remove(card);
                Commen.DeruCard(cards, myJFrame.panel);
                myJFrame.shou = cards;
                myJFrame.chupairen = flag;
                myJFrame.total = total;
                return true;
            }
            return false;
        } else if(!Objects.equals(type,CardType.c0) ) {  //如果上一次出牌人是我
            Clear();
            myJFrame.type = type;
            for(Card card : cards)
                myJFrame.gamer[flag].remove(card);
            Commen.DeruCard(cards, myJFrame.panel);
            myJFrame.shou = cards;
            myJFrame.chupairen = flag;
            myJFrame.total = total;
            return true;
        }
        return false;
    }

    /*
    清除掉上一次出的牌
     */
    public void Clear() {
        if( myJFrame.shou != null && myJFrame.shou.size() != 0 ) {
            for(Card card : myJFrame.shou)
                myJFrame.panel.remove(card);
        }
    }

    public void aiChuPai(int flag, newVector cards) {
        Clear();
        CardType type = Commen.cardType(cards);
        int total = Commen.getTotal(cards);
        System.out.println("" + type);
        if( ! Objects.equals(type, CardType.c0) ) {
            myJFrame.type = type;
            for(Card card : cards)
                myJFrame.gamer[flag].remove(card);
            Commen.DeruCard(cards, myJFrame.panel);
        }
        myJFrame.shou = cards;
        myJFrame.chupairen = flag;
        myJFrame.total = total;
    }

    /*
    电脑出牌1
        1.//如果上一次出的牌还是自己出的，那么接着出新牌
        2.//如果上一次的牌不是自己出的，且自己是地主，那么进行出牌
        3.//如果自己不是地主，且上一次牌是地主出的，那么进行出牌
     */
    public void AICard1(int ai) {
        Cards types = Commen.ChaiFen(myJFrame.gamer[ai]);
        newVector vector = new newVector();
        if( types.d111222.size() > 0 ) {                  //如果有飞机，就出飞机并搭连对，如果没有连对就搭单
            Commen.zh(types.d111222, 1, vector, myJFrame.gamer[ai]);
            if( types.d2.size() >= vector.size() / 3 ) {    //如果有飞机个数那么多的对子，就加上对子
                Vector<Card> vector2 = new Vector<>();
                Commen.zh(types.d2, vector.size() / 3, vector2, myJFrame.gamer[ai]);
                vector.addAll(vector2.subList(0, vector.size() / 3 * 2));
            } else if( types.d1.size() >= vector.size() / 3 ) {    //如果对子不够就上单牌
                Vector<Card> vector2 = new Vector<>();
                Commen.zh(types.d1, vector.size() / 3, vector2, myJFrame.gamer[ai]);
                vector.addAll(vector2.subList(0, vector.size() / 3));
            }
        } else if( types.d112233.size() > 0 ) {
            Commen.zh(types.d112233, 1, vector, myJFrame.gamer[ai]);
        } else if( types.d123.size() > 0 ) {
            Commen.zh(types.d123, 1, vector, myJFrame.gamer[ai]);
        } else if( types.d3.size() > 0 ) {
            Commen.zh(types.d3, 1, vector, myJFrame.gamer[ai]);
            if( types.d2.size() >= 1 ) {    //如果有1个对子，就加上对子
                Vector<Card> vector2 = new Vector<>();
                Commen.zh(types.d2, 1, vector2, myJFrame.gamer[ai]);
                vector.addAll(vector2.subList(0, 2));
            } else if( types.d1.size() >= 1 ) {    //如果对子不够就上单牌
                Vector<Card> vector2 = new Vector<>();
                Commen.zh(types.d1, 1, vector2, myJFrame.gamer[ai]);
                vector.add(vector2.get(0));
            }
        } else if( types.d2.size() > 0 ) {
            Commen.zh(types.d2, 1, vector, myJFrame.gamer[ai]);
        } else if( types.d1.size() > 0 ) {
            Commen.zh(types.d1, 1, vector, myJFrame.gamer[ai]);
        } else if( types.d4.size() > 0 ) {
            Commen.zh(types.d4, 1, vector, myJFrame.gamer[ai]);
        }
        aiChuPai(ai, vector);
    }

    /*
    电脑出牌2
        a.先查看上次出的牌是哪种类型
        b.根据类型去进入到出牌
        出牌原则:
        对方是顺子、双顺子、飞机带牌或不带、四带二、四带2对、连对就优先出炸弹，再有类型的牌就出
        对方是炸弹 就优先出王炸，然后再出炸弹

     */
    public void AICard2(int flag) {
        CardType t = myJFrame.shou.getType();
        int s = myJFrame.shou.size();
        int o = myJFrame.shou.getTotal();
        newVector vector = new newVector();
        //对电脑牌进行拆分
        Cards cards = Commen.ChaiFen(myJFrame.gamer[flag]);
        if( t == CardType.c1 ) {          //出的单牌
            AI1(cards.d1, o, flag, vector, 1);
        } else if( t == CardType.c11 ) {      //出的对子
            AI1(cards.d2, o, flag, vector, 2);
        } else if( t == CardType.c111 || t == CardType.c1112 || t == CardType.c11122 ) {     //出的单砍子、砍子带单、砍子带双
            AI1(cards.d3, o, flag, vector, 3);
            if( t == CardType.c1112 && cards.d1.size() >= 1 ) {//出的砍子带单
                Commen.zh(cards.d1, 1, vector, myJFrame.gamer[flag]);
            } else if( t == CardType.c11122 && cards.d2.size() >= 1 ) {//出的砍子带双
                Commen.zh(cards.d2, 1, vector, myJFrame.gamer[flag]);
            }
        } else if( t == CardType.c1111 || t == CardType.c111123 || t == CardType.c11112233 ) {    //出的炸弹、四带二、四带四
            AI1(cards.d4, o, flag, vector, 4);
            if( t == CardType.c111123 && cards.d1.size() >= 2 ) {
                Commen.zh(cards.d1, 2, vector, myJFrame.gamer[flag]);
            } else if( t == CardType.c11112233 && cards.d2.size() >= 2 ) {
                Commen.zh(cards.d2, 2, vector, myJFrame.gamer[flag]);
            }
        }
        if( Commen.typeCompare(vector, myJFrame.shou) ) {
            aiChuPai(flag, vector);
        }

    }

    public void AI1(Vector<String> cards, int o, int flag, newVector vector, int i) {
        for(String s1 : cards) {
//            if(s1.split(",").length)
            if( getValue(s1) * i > o ) {
                cards.remove(s1);
                Commen.zh(s1, vector, myJFrame.gamer[flag]);
                break;
            }
        }
    }

    public static int getValue(String s) {
        return Integer.parseInt(s.split(",")[0].substring(2));
    }
}