package Common;

import java.util.Vector;

public class newVector extends Vector<Card> {
    private CardType type;
    private int total;

    public CardType getType() {
        type = Commen.cardType(this);
        return type;
    }

    public int getTotal() {
        int total = 0;
        getType();
        int[] s = new int[20];
        for(int i = 0; i < this.size(); i++) {
            s[this.get(i).getScore()]++;
        }
        if( type == CardType.c1112223344 || type == CardType.c11122234 || type == CardType.c111222 || type == CardType.c111
                || type == CardType.c1112 || type == CardType.c11122 ) {
            for(int i = 0; i < s.length; i++) {
                if( s[i] == 3 ) {
                    total += i * 3;
                }
            }
        } else if( type == CardType.c111123 || type == CardType.c11112233 ) {
            for(int i = 0; i < s.length; i++) {
                if( s[i] == 4 ) {
                    total += i * 4;
                }
            }
        } else {
            for(Card card : this) {
                total += card.getScore();
            }
        }
        this.total = total;
        return this.total;
    }

}
