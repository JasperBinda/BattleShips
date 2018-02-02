import java.util.Scanner;

public class Battle{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean PlayingGame = true;
        Player p1 = new Player();
        p1.name = "Player1";
        p1.CreateFleet();

        Player p2 = new Player();
        p2.name = "Player2";
        p2.CreateFleet();

        Player CurrentPlayer = p1;
        Player WaitingPlayer = p2;
        while(PlayingGame) {
            System.out.println(CurrentPlayer.name + " please enter coordinates to shoot."); //coordinates a1, A1, etc.
            String input = scanner.next();
            char[] shot_xy = input.toLowerCase().toCharArray();
            int xs = shot_xy[0] - 97; //transfer a ascii value to int 0, etc.
            int ys = shot_xy[1] - 49; //transfer 1 ascii value to int 0, etc.

            if(CurrentPlayer.DidIShootHereBefore(xs,ys)){
                System.out.println("You already shot here, try again...");
            }else{
                if(WaitingPlayer.fleet[0].AmIHit(xs,ys)){
                    System.out.println("Hit a boat!");
                    if(WaitingPlayer.fleet[0].DidISink()){
                        System.out.println("Boat has sunk.");
                        PlayingGame = !(WaitingPlayer.EndGame());
                    }
                }else{
                    System.out.println("You missed.");
                }
                CurrentPlayer.AddShot(xs,ys);
                Player[] Players = SwitchTurns(CurrentPlayer, WaitingPlayer);
                CurrentPlayer = Players[0];
                WaitingPlayer = Players[1];
            }
        }
        System.out.println(WaitingPlayer.name + " won the game!"); //players switch before this line is called
    }
    static Player[] SwitchTurns(Player c, Player w){
        return new Player[]{w,c};
    }
}

class Player extends Sea{
    String name;
    Boat[] fleet;
    int destroyedboats;
    void CreateFleet(){
        Boat boat = new Boat();
        boat.x = new int[] {0,0,0};
        boat.y = new int[] {0,1,2}; // boat on a1, a2, a3
        fleet = new Boat[]{boat};
    }
    boolean EndGame(){
        destroyedboats += 1;
        return (destroyedboats == fleet.length);
    }
}

abstract class Sea{
    int[][] field = new int[5][5];
    void AddShot(int x, int y){
        field[x][y] = 1;
    }
    boolean DidIShootHereBefore(int x, int y){
        return (field[x][y] == 1);
    }
}

class Boat {
    int[] x;
    int[] y;
    int totalhits;
    boolean AmIHit(int x_shot, int y_shot) {
        boolean hit = false;
        for (int i = 0; i < x.length; i++) {
            if (x_shot == x[i] && y_shot == y[i]) {
                hit = true;
                totalhits += 1;
            }
        }
        return hit;
    }
    boolean DidISink(){
        return (totalhits == x.length);
    }
}

