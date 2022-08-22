package Common;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Commen {

    /*
        牌移动
         */
    public static void Move(Card card, Point to, JPanel panel) {
        int x = card.getX() - ( int ) to.getX();
        int y = card.getY() - ( int ) to.getY();
        int x1 = x / 20, y1 = y / 20;
        for(int i = 1; i <= 20; i++) {
            card.setLocation(card.getX() - x1, card.getY() - y1);
            try {
                Thread.sleep(1);
            } catch( InterruptedException e ) {
                e.printStackTrace();
            }
        }
        card.setLocation(to.getLocation());
        panel.repaint();
    }

    private static void shuffle_Cards(Vector<Card> cards) {
        Collections.shuffle(cards);
    }

    /*
    游戏初始化
     */
    public static boolean GameStart(Vector<Card> cards, JPanel panel) {
        Integer[] flowers = { 1, 2, 3, 4 };
        Integer[] scores = { 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
        for(int i = 0; i < 13; i++) {
            for(int i1 = 0; i1 < 4; i1++) {
                Card card = new Card(flowers[i1], scores[i]);
                card.setPath2();
                card.setBounds(550, 277, 72, 96);
                cards.add(card);
                panel.add(card);
            }
        }
        Card small = new Card("image/5-17.gif", 17);
        small.setBounds(550, 277, 72, 96);
        Card big = new Card("image/5-18.gif", 18);
        big.setBounds(550, 277, 72, 96);
        panel.add(small);
        panel.add(big);
        cards.add(small);    //  小王
        cards.add(big);    //  大王
//        Collections.shuffle(cards);
        panel.repaint();
        return true;
    }

    /*
    牌组排序
     */
    private static void CardSort(Vector<Card> cards) {
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                if( Objects.equals(o1.getScore(), o2.getScore()) ) {
                    return o2.getFlower() - o1.getFlower();
                } else
                    return o1.getScore() - o2.getScore();
            }
        });
    }

    /*
    出牌重绘
     */
    public static void DeruCard(Vector<Card> cards, JPanel panel) {
        CardSort(cards);
        int x = (cards.size() - 1) * 20 + 525;
        int y = 250;
        int gap = 32;
        for(Card card : cards) {
            panel.remove(card);
        }
        for(Card card : cards) {
            card.setPath();
            card.setLocation(x, y);
            x -= gap;
            card.setRear(true);
            panel.add(card);
        }
        panel.repaint();
    }

    /*
    手牌重绘
     */
    public static void Repaint(Vector<Card> player, JPanel panel, int flag) {
        CardSort(player);
        int x = 0, y = 0;
        int gap = 0;
        int xx = 0, yy = 0;
        if( flag == 0 ) {                                             //玩家重绘
            xx = (player.size() - 1) * 16 + 550;
            gap = 32;
            y = 600;
        } else if( flag == 1 ) {                                       //左电脑
            yy = 400 - (player.size() - 1) * 18;
            gap = 18;
            x = 150;
        } else if( flag == 2 ) {                                        //右电脑
            yy = 400 - (player.size() - 1) * 18;
            gap = 18;
            x = 1000;
        }
        for(Card card : player) {
            panel.remove(card);
        }
        for(Card card : player) {
            if( card.isJump() && ! card.isRear() ) {
                card.setLocation(card.getX(), card.getY() + 80 / 2);
                card.setJump(false);
            }
            if( flag == 0 ) {
                card.setPath();
                card.setLocation(xx, y);
            } else {
                card.setPath2();
                card.setLocation(x, yy);
            }
            panel.add(card);
            if( flag == 0 )
                xx -= gap;
            else
                yy += gap;
        }
        panel.repaint();
    }

    /*
    抽牌
     */
    public static void Draw(MyJFrame jFrame) {
//        int x1 = 850, y1 = 600;
//        int x2 = 150, y2 = 100;
//        int x3 = 950, y3 = 100;
        JPanel panel = jFrame.getPanel();
        jFrame.setLandlordCards(Commen.Player_Drow(jFrame.dizhuCard, jFrame.gamer[0], jFrame.gamer[1], jFrame.gamer[2], panel));
//        jFrame.gamer[0] = jFrame.dizhuCard;
        jFrame.getLandlordCards();
//        for(int i = 0; i < 3; i++) {
//            Commen.CardSort(player[i]);
//        }
//        Card card = new Card(1, 13);
//        card.setBounds(0, 0, 72, 96);
//        jFrame.gamer[0].add(card);
//        jFrame.gamer[1].remove(card);
//        jFrame.gamer[2].remove(card);
        Commen.Repaint(jFrame.gamer[0], panel, 0);
        Commen.Repaint(jFrame.gamer[1], panel, 1);
        Commen.Repaint(jFrame.gamer[2], panel, 2);
    }

    /*
    初始发牌
     */
    private static Vector<Card> Player_Drow(Vector<Card> cards, Vector<Card> play1, Vector<Card> play2, Vector<Card> play3, JPanel panel) {
        int i = 1;
        int x1 = 850, y1 = 600;
        int x2 = 150, y2 = 100;
        int x3 = 950, y3 = 100;
        play1.clear();
        play2.clear();
        play3.clear();
        Collections.shuffle(cards);
        Vector<Card> landlord = new Vector<>(3);
        for(Card card : cards) {

            if( i >= 52 ) {
                landlord.add(card);
                card.setRear(true);
                card.setPath2();
                panel.remove(card);
            } else {
                switch( i % 3 ) {
//                if( i <= 17 ) {
                    case 0:
                        card.setPath();
                        play1.add(card);
                        Commen.Move(card, new Point(x1, y1), panel);
                        x1 -= 36;
                        panel.add(card);
                        break;
//                }
                    case 1:
//                else if( i <= 34 ) {
                        card.setPath2();
                        card.setRear(true);
                        play2.add(card);
                        Commen.Move(card, new Point(x2, y2), panel);
                        y2 += 18;
                        panel.add(card);
                        break;
//                }
                    case 2:
//                else if( i <= 51 ) {
                        card.setPath2();
                        card.setRear(true);
                        play3.add(card);
                        Commen.Move(card, new Point(x3, y3), panel);
                        y3 += 18;
                        panel.add(card);
                        break;
                }
            }
            i++;
        }
        return landlord;
    }

    /*
   抢地主
    */
    public static void Landlord(MyJFrame jFrame) {
        jFrame.getLandlord();
        while( true ) {
            jFrame.Second(10);
            if( jFrame.result == 3 ) {
                jFrame.player.setText("你是地主");
                System.out.println("你是地主");
                jFrame.dizhu = 0;
                jFrame.setLandlord();
                break;
            } else if( jFrame.result == 1 ) {
                jFrame.result = 10;
                System.out.println("ai1 一分");
                jFrame.player.setText("ai1 一分");
                jFrame.Second(3);
                System.out.println("ai2 不要");
                jFrame.player.setText("ai2 不要");
                jFrame.Second(3);
                System.out.println("玩家继续叫");
                jFrame.player.setText("玩家继续叫");
            } else if( jFrame.result == 2 ) {
                double i = (new Random().nextDouble()) * 2 + 1;
                jFrame.dizhu = ( int ) i;
//                System.out.println(jFrame.dizhu);
                jFrame.setLandlord();
                if( jFrame.dizhu == 1 ) {
                    System.out.println("地主是ai1");
                    jFrame.player.setText("ai2 不要");
                } else {
                    System.out.println("地主ai2");
                    jFrame.player.setText("ai2 不要");
                }
                break;
            } else {
                double j = (new Random().nextDouble()) * 3 + 1;
                jFrame.dizhu = ( int ) j;
//                jFrame.dizhu = - 1;
//                System.out.println(jFrame.dizhu);
                jFrame.setLandlord();
                if( jFrame.dizhu == 1 ) {
                    System.out.println("地主是ai1");
                    jFrame.player.setText("地主是ai1");
                } else if( jFrame.dizhu == 2 ) {
                    System.out.println("地主ai2");
                    jFrame.player.setText("地主ai2");
                } else {
                    System.out.println("都不要，重新开始游戏");
                    jFrame.player.setText("都不要，重新开始游戏");
                    jFrame.setVisible(true);
                    new Main().Start();
                }
                return;
            }
        }
        if( jFrame.dizhu == 0 ) {        //玩家是地主
            jFrame.landlordIcon.setLocation(150, 650);

        } else if( jFrame.dizhu == 1 ) {      //地主是左边电脑
            jFrame.landlordIcon.setLocation(50, 50);
        } else if( jFrame.dizhu == 2 ) {      //地主是右边电脑
            jFrame.landlordIcon.setLocation(1100, 50);
        }
        jFrame.landlordIcon.setVisible(true);
        jFrame.next = jFrame.dizhu;           //将地主设为出牌人
        jFrame.chupairen = jFrame.dizhu;        //出牌人设为地主
        jFrame.result = 10;
    }

    /*
    获取地主牌
     */
    public static void getLandCard(MyJFrame jFrame, int flag) {
        JPanel panel = jFrame.getPanel();
        for(Card card : jFrame.landlordCards) {
            card.setPath();
            Card newcard = new Card(card);
            newcard.setBounds(0, 0, 72, 96);
            panel.add(newcard);
            newcard.setPath();
            jFrame.gamer[flag].add(newcard);
        }
//        Commen.CardSort(player[flag]);
        Commen.Repaint(jFrame.gamer[flag], panel, flag);
    }

    /*
    判断是不是单牌、对子、单砍、炸弹、王炸
     */
    private static CardType is4Card(Vector<Integer> cards) {
        int len = cards.size();
        if( len <= 4 ) {
            //不是王炸,且牌都相同
            if( cards.get(0).equals(cards.get(len - 1)) ) {
                switch( len ) {
                    case 1:
                        return CardType.c1;
                    case 2:
                        return CardType.c11;
                    case 3:
                        return CardType.c111;
                    case 4:
                        return CardType.c1111;
                }       //王炸化为最大的对子
            } else if( len == 2 && cards.get(0) == 17 && cards.get(1) == 18 ) {
                return CardType.c12;
            }
            if( len == 4 ) {
                int max = 0;
                int num[] = new int[20];
                for(int card : cards) {
                    num[card]++;
                }
                for(int i : num) {
                    if( i == 3 ) {
                        max++;
                    }
                }
                if( max == 1 ) {
                    return CardType.c1112;
                }
            }
        }
        return CardType.c;
    }

    /*
    判断单顺子
     */
    private static CardType isdanshunzi(Vector<Integer> cards) {
        int len = cards.size();
        //长度至少有5张 //单顺子,最后一张牌不是2
        if( cards.get(len - 1) != 15 && len >= 5 ) {
            Integer card = cards.get(0);
            for(int i = 1; i < cards.size(); i++) {
                if( cards.get(i) - 1 != card ) {
                    return CardType.c;
                }
                card = cards.get(i);
            }
            return CardType.c12345;
        }
        return CardType.c;
    }

    /*
    //飞机3带2  44455 或者33444
     */
    private static CardType isKanzi(Vector<Integer> cards) {
        int len = cards.size();
        if( len == 5 ) {
            if( cards.get(0).equals(cards.get(1)) && cards.get(2).equals(cards.get(3)) && cards.get(3).equals(cards.get(4)) ) {
                return CardType.c11122;
            } else if( cards.get(3).equals(cards.get(4)) && cards.get(0).equals(cards.get(1)) && cards.get(1).equals(cards.get(2)) ) {
                return CardType.c11122;
            }
        }
        return CardType.c;
    }

    /*
    判断双顺子
     */
    private static CardType isShuangshunzi(Vector<Integer> cards) {
        int len = cards.size();
        cards.sort(((o1, o2) -> o1 - o2));
        //双顺子，最后一张不是2
        System.out.println(111111111);
        if( len % 2 == 0 && cards.get(len - 1) != 15 && len >= 6 ) {
            Integer card = cards.get(0);
            for(int i = 0; i < cards.size(); i += 2) {
                if( cards.get(i) - 1 != card && i >= 2 )
                    return CardType.c;
                if( ! cards.get(i).equals(cards.get(i + 1)) )
                    return CardType.c;
                card = cards.get(i);
            }
            return CardType.c112233;
        }
        return CardType.c;
    }

    /*
    判断飞机、飞机带单、飞机带双
     */
    private static CardType isAirdan(Vector<Integer> cards) {
//        int len = cards.size();
        int[] num = new int[20];
        for(int i : cards) {
            num[i] += 1;
        }
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for(int value : num) {
            if( value == 3 )
                count1++;
            else if( value >= 1 )
                count2++;
            if( value == 2 ) {
                count3++;
            }
        }
        if( count2 == 0 && count1 >= 2 ) {  //飞机不带
            return CardType.c111222;
        } else if( count1 == count2 && count1 >= 2 ) {  //飞机带单
            return CardType.c11122234;
        } else if( count3 == count1 && count1 >= 2 ) {      //飞机带双
            return CardType.c1112223344;
        } else
            return CardType.c;
    }

    /*
    判断是4带2、4带对
     */
    private static CardType isFortow(Vector<Integer> cards) {
        int len = cards.size();
        //6张为4带2,必定不是双顺子,8张为4带两对
        if( len == 6 || len == 8 ) {
            int max = 0;
            int[] num = new int[20];
            for(int i : cards) {
                num[i] += 1;
            }
            int count = 0;
            for(int i : num) {
                if( i == 1 || i == 2 )
                    count++;
                else if( i == 4 )
                    max++;
            }
            if( len == 6 && max == 1 )
                return CardType.c111123;
            else if( len == 8 && max == 1 && count == 2 )
                return CardType.c11112233;
        }
        return CardType.c;
    }

    /*
    判断出牌是否符合规则
     */
    public static CardType cardType(Vector<Card> cards) {
        if( cards.size() == 0 ) {
            return CardType.c0;
        }
        Vector<Integer> s = new Vector<>();
        for(Card card1 : cards) {
            s.add(card1.getScore());
        }
        CardType type = CardType.c;
        for(int i = 0; i < 6; i++) {
            type = isType(s, i);
            if( ! Objects.equals(type, CardType.c) )
                return type;
        }
        return CardType.c0;
    }

    private static CardType isType(Vector<Integer> cards, int flag) {
        switch( flag ) {
            case 0:
                return is4Card(cards);
            case 1:
                return isdanshunzi(cards);
            case 2:
                return isKanzi(cards);
            case 3:
                return isShuangshunzi(cards);
            case 4:
                return isAirdan(cards);
            case 5:
                return isFortow(cards);
        }
        return CardType.c;
    }


    /*
    对电脑牌组进行拆分
     */
    public static Cards ChaiFen(Vector<Card> cardVector) {
        Cards cards = new Cards();
        Vector<Card> new_Card = new Vector<>(cardVector);
        int[] flag = { 4, 3, 2, 1, 5 };
        for(int i = 0; i < flag.length; i++) {
            isCards(new_Card, cards, flag[i]);
        }
        return cards;
    }

    private static void isCards(Vector<Card> new_Card, Cards cards, int flag) {
        switch( flag ) {
            case 1:
                getOne(new_Card, cards);
                break;
            case 2:
                getTwo(new_Card, cards);
                getTwoTwo(new_Card, cards);
                break;
            case 3:
                getThree(new_Card, cards);
                getPlane(new_Card, cards);
                break;
            case 4:
                getBoom(new_Card, cards);
                break;
            case 5:
                getShun(new_Card, cards);
                break;
        }

    }

    public static void zh(String s, Vector<Card> card1, Vector<Card> card2) {
        Vector<String> vector = new Vector<>(Arrays.asList(s.split(",")));
        for(String ss : vector) {
            for(Card card : card2) {
                if( card.Path().equals(ss) )
                    card1.add(card);
            }
        }
    }

    //通过path将String转换成Card
    public static void zh(Vector<String> card1, int i, Vector<Card> card2, Vector<Card> cards) {
        Vector<String> vector = new Vector<>();
        for(int j = 0; j < i; j++) {
            vector.addAll(Arrays.asList(card1.get(j).split(",")));
        }
        vector.sort((o1, o2) -> Integer.parseInt(o1.substring(2)) - Integer.parseInt(o2.substring(2)));
        for(String s : vector) {
            for(Card card : cards) {
                if( s.equals(card.Path()) ) {
                    card2.add(card);
                }
            }
        }
    }

    //获得顺子
    private static void getShun(Vector<Card> new_card, Cards cards) {
        Vector<String> one = cards.d1;
        Vector<String> del2 = new Vector<>();
        Vector<String> del1 = new Vector<>();
        Vector<String> two = cards.d2;

        for(String s : two) {
            del2.add(s);
            one.addAll(Arrays.asList(s.split(",")));
        }
        cards.d2.removeAll(del2);
        cards.d1.sort(((o1, o2) -> Integer.parseInt(o1.substring(2)) - Integer.parseInt(o2.substring(2))));

        for(int i = 0; i < one.size() - 5; i++) {
            int gap = 0;
            Vector<String> count = new Vector<>();
            for(int j = i; j < one.size(); j++) {
                if( Integer.parseInt(one.get(j).substring(2)) >= 15 )
                    break;
                if( Integer.parseInt(one.get(i).substring(2)) == Integer.parseInt(one.get(j).substring(2)) - gap ) {
                    gap++;
                    count.add(one.get(j));
                }
            }
            if( count.size() >= 5 ) {
                System.out.print("获得一个顺子：");
                String name = "";
                for(int n = 0; n < count.size() - 1; n++)
                    name += count.get(n) + ",";
                name += count.get(count.size() - 1);
                System.out.println(name);
                cards.d123.add(name);
                one.removeAll(count);
            }
        }
        for(int i = 0; i < one.size(); i++) {
            if( i < one.size() - 1 && Integer.parseInt(one.get(i).substring(2)) == Integer.parseInt(one.get(i + 1).substring(2)) ) {
                String name = one.get(i) + ",";
                name += one.get(i + 1);
                cards.d2.add(name);
                del1.add(one.get(i));
                del1.add(one.get(i + 1));
            }
        }
        one.removeAll(del1);
    }

    //获得飞机
    private static void getPlane(Vector<Card> new_card, Cards cards) {
        Vector<String> del = new Vector<>();
        Vector<String> list = cards.d3;
        if( list.size() < 2 )
            return;
        Integer s[] = new Integer[list.size()];
        for(int i = 0; i < list.size(); i++) {
            String[] name = list.get(i).split(",");
            s[i] = Integer.parseInt(name[0].substring(2));
        }
        int len = s.length;
        for(int i = 0; i < len; i++) {
            int k = i;
            for(int j = i; j < len; j++) {
                if( s[j] - s[i] == j - i )
                    k = j;
            }
            if( k != i ) {// 说明从i到k是飞机
                String ss = "";
                for(int j = i; j < k; j++) {
                    ss += list.get(j) + ",";
                    del.add(list.get(j));
                }
                ss += list.get(k);
                cards.d111222.add(ss);
                del.add(list.get(k));
                i = k;
            }
        }
        cards.d3.removeAll(del);
    }

    //获得三顺
    private static void getThree(Vector<Card> new_card, Cards cards) {
        if( new_card.size() < 1 )
            return;
        Vector<Card> del = new Vector<>();
        int[] score = new int[new_card.size()];
        for(int i = 0; i < new_card.size(); i++)
            score[i] = new_card.get(i).getScore();
        //获得连续3张相同的
        for(int i = 0; i < score.length; i++) {
            if( i < score.length - 2 && score[i] == score[i + 2] ) {
                String name = new_card.get(i).Path() + ",";
                name += new_card.get(i + 1).Path() + ",";
                name += new_card.get(i + 2).Path();
                cards.d3.add(name);
                for(int j = i; j <= i + 2; j++)
                    del.add(new_card.get(j));
                i += 2;
            }
        }
        new_card.removeAll(del);
    }

    //获得双顺子
    private static void getTwoTwo(Vector<Card> new_card, Cards cards) {
        Vector<String> del = new Vector<>();
        Vector<String> list = cards.d2;
        if( list.size() < 3 )
            return;
        Integer s[] = new Integer[list.size()];
        int count = 0;
        for(int i = 0; i < list.size(); i++) {
            String[] name = list.get(i).split(",");
            s[count++] = Integer.parseInt(name[0].substring(2));
            if( s[-- count] == 15 )
                break;

        }
        int len = count;
        for(int i = 0; i < len; i++) {
            int k = i;
            for(int j = i; j < len; j++) {
                if( s[j] - s[i] == j - i )
                    k = j;
            }
            if( k - i >= 2 ) {// 说明从i到k是连对
                String ss = "";
                for(int j = i; j < k; j++) {
                    ss += list.get(j) + ",";
                    del.add(list.get(j));
                }
                ss += list.get(k);
                cards.d112233.add(ss);
                del.add(list.get(k));
                i = k;
            }
        }
        cards.d2.removeAll(del);
    }

    //获得对子
    private static void getTwo(Vector<Card> new_card, Cards cards) {
        if( new_card.size() < 2 )
            return;
        Vector<Card> del = new Vector<>();
        int[] score = new int[new_card.size()];
        for(int i = 0; i < new_card.size(); i++)
            score[i] = new_card.get(i).getScore();
        for(int i = 0; i < score.length; i++) {
            if( i < score.length - 1 && score[i] == score[i + 1] ) {
                String name = new_card.get(i).Path() + ",";
                name += new_card.get(i + 1).Path();
                cards.d2.add(name);
                for(int j = i; j <= i + 1; j++)
                    del.add(new_card.get(j));
                i += 1;
            }
        }
        new_card.removeAll(del);
    }

    //获得单牌
    private static void getOne(Vector<Card> new_card, Cards cards) {
        if( new_card.size() < 1 )
            return;
        Vector<Card> del = new Vector<>();
        for(Card card : new_card) {
            del.add(card);
            cards.d1.add(card.Path());
        }
        new_card.removeAll(del);
    }

    //获得炸弹
    private static void getBoom(Vector<Card> new_card, Cards cards) {
        if( new_card.size() < 1 )
            return;
        Vector<Card> del = new Vector<>();
        int[] score = new int[new_card.size()];
        for(int i = 0; i < new_card.size(); i++)
            score[i] = new_card.get(i).getScore();
        //王炸
        if( score[score.length - 1] == 18 && score[score.length - 2] == 17 ) {
            cards.bs.add(new_card.get(score.length - 2).Path() + "," +
                    new_card.get(score.length - 1).Path());
            //加入待删除
            del.add(new_card.get(score.length - 1));
            del.add(new_card.get(score.length - 2));
        }
        //没有只有单王就拆单牌
        else if( score[score.length - 1] == 18 || score[score.length - 1] == 17 ) {
            cards.d1.add(new_card.get(score.length - 1).Path());
            del.add(new_card.get(score.length - 1));
        }
        //获得普通炸弹
        for(int i = 0; i < score.length; i++) {
            if( i < score.length - 3 && score[i] == score[i + 3] ) {
                String name = new_card.get(i).Path() + ",";
                name += new_card.get(i + 1).Path() + ",";
                name += new_card.get(i + 2).Path() + ",";
                name += new_card.get(i + 3).Path();
                cards.d4.add(name);
                for(int j = i; j <= i + 3; j++) {
                    del.add(new_card.get(j));
                }
                i += 3;
            }
        }
        //从手牌中删除已经拆好的牌
        new_card.removeAll(del);
    }

    /*
    求打出的一次牌的总分和
     */
    public static int getTotal(Vector<Card> cards) {
        int total = 0;
        for(Card card : cards) {
            total += card.getScore();
        }
        return total;
    }

    /*
    类型比较
     */
    public static boolean typeCompare(newVector type1, newVector type2) {
        CardType t1 = type1.getType();
        CardType t2 = type2.getType();
        int o1 = type1.getTotal();
        int o2 = type2.getTotal();
        int s1 = type1.size();
        int s2 = type2.size();
        if( t1 == CardType.c12 )        //我是王炸
            return true;
        if( t2 == CardType.c12 )            //对方是王炸
            return false;
        if( t2 == CardType.c1111 && t1 != CardType.c1111 )    //对方是炸弹，我不是炸弹
            return false;
        else if( t1 == CardType.c1111 ) {   //我出炸弹
            //我的分小
            if( t2 == CardType.c1111 && s1 == s2 && o1 > o2 )       //我是炸弹，对方是炸弹，且我的分更大
                return true;
            else if( t2 == CardType.c1111 && s1 == s2 && o1 < o2 )  //对方是炸弹，且对方分更大
                return false;
            else
                return true;
        } else if( t1 == CardType.c1112223344 || t1 == CardType.c11122234
                || t1 == CardType.c111222 || t1 == CardType.c1112 || t1 == CardType.c11122
                || t1 == CardType.c111 ) {
            //我是砍子或者飞机类，对方不是王炸和炸弹
            if( t1 == t2 && s1 == s2 && o1 > o2 )     //类型相同，牌数一样，且分更大
                return true;
            else
                return false;
        } else if( t1 == CardType.c111123 || t1 == CardType.c11112233 ) {
            //我是四带一或者四带二 对方非王炸非炸弹
            if( t1 == t2 && s1 == s2 && o1 > o2 )
                return true;
            else
                return false;
        } else if( t1 == CardType.c12345 || t1 == CardType.c112233
                || t1 == CardType.c1 || t1 == CardType.c11 ) {
            //我是顺子、双顺子、单牌、对子，对方非王炸非炸弹
            if( t1 == t2 && s1 == s2 && o1 > o2 )
                return true;
            else
                return false;
        } else if( t1 == CardType.c0 ) {    //我是不能出的类型
            return false;
        }
        return false;
    }
}
