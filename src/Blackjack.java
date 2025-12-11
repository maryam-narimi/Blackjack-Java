import java.util.*;

class Card {
    String rank;
    String suit;
    Card(String r, String s){rank=r; suit=s;}
    int value(){
        switch(rank){
            case "A": return 11;
            case "K": case "Q": case "J": return 10;
            default: return Integer.parseInt(rank);
        }
    }
    public String toString(){return rank + suit;}
}

class Deck {
    private List<Card> cards = new ArrayList<>();
    Deck(){
        String[] suits = {"♠","♥","♦","♣"};
        String[] ranks = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        for(String s: suits) for(String r: ranks) cards.add(new Card(r,s));
        shuffle();
    }
    void shuffle(){Collections.shuffle(cards);}
    Card draw(){return cards.remove(cards.size()-1);}
}

class Hand {
    List<Card> cards = new ArrayList<>();
    void add(Card c){cards.add(c);}
    int value(){
        int sum=0; int aces=0;
        for(Card c: cards){
            sum += c.value();
            if(c.rank.equals("A")) aces++;
        }
        while(sum>21 && aces>0){sum-=10; aces--;}
        return sum;
    }
    String show(boolean hideFirst){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<cards.size();i++){
            if(i==0 && hideFirst) sb.append("[??] ");
            else sb.append("[").append(cards.get(i)).append("] ");
        }
        return sb.toString().trim();
    }
}

public class Blackjack {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("=== Simple Blackjack ===");
        while(true){
            Deck deck=new Deck();
            Hand player=new Hand();
            Hand dealer=new Hand();
            player.add(deck.draw());
            dealer.add(deck.draw());
            player.add(deck.draw());
            dealer.add(deck.draw());
            boolean playerTurn=true;
            while(playerTurn){
                System.out.println("\nDealer: " + dealer.show(true));
                System.out.println("Your hand: " + player.show(false) + "  Total: " + player.value());
                if(player.value()==21) {System.out.println("Blackjack!"); break;}
                if(player.value()>21) {System.out.println("Bust! You lose."); break;}
                System.out.print("Hit or Stand? (h for Hit / s for Stand): ");
                String in=sc.nextLine().trim().toLowerCase();
                if(in.equals("h") || in.equals("hit")){
                    player.add(deck.draw());
                } else {
                    playerTurn=false;
                }
            }
            if(player.value()<=21){
                System.out.println("\nDealer's turn...");
                System.out.println("Dealer: " + dealer.show(false) + "  Total: " + dealer.value());
                while(dealer.value()<17){
                    System.out.println("Dealer draws a card...");
                    dealer.add(deck.draw());
                    System.out.println("Dealer: " + dealer.show(false) + "  Total: " + dealer.value());
                }
                if(dealer.value()>21) System.out.println("Dealer busts! You win!");
                else {
                    System.out.println("\nFinal result:");
                    System.out.println("Your hand: " + player.show(false) + " Total: " + player.value());
                    System.out.println("Dealer: " + dealer.show(false) + " Total: " + dealer.value());
                    int pv = player.value(), dv = dealer.value();
                    if(pv>dv) System.out.println("You win!");
                    else if(pv<dv) System.out.println("You lose.");
                    else System.out.println("It's a tie.");
                }
            }
            System.out.print("\nPlay again? (y/n): ");
            String again=sc.nextLine().trim().toLowerCase();
            if(!again.equals("y") && !again.equals("yes")) break;
        }
        System.out.println("Goodbye!");
        sc.close();
    }
}