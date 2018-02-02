import java.util.Scanner;

public class Battle{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean playingGame = true;
        Player p1 = new Player();
        p1.name = "Player1";
        p1.CreateFleet();

        Player p2 = new Player();
        p2.name = "Player2";
        p2.CreateFleet();

        Player currentPlayer = p1;
        Player waitingPlayer = p2;
        while(playingGame) {
            String result = "You missed.";
            System.out.println(currentPlayer.name + " please enter coordinates to shoot."); //coordinates a1, A1, etc.
            String input = scanner.next();
            char[] shot_xy = input.toLowerCase().toCharArray();
            int xs = shot_xy[0] - 97; //transfer a ascii value to int 0, etc.
            int ys = shot_xy[1] - 49; //transfer 1 ascii value to int 0, etc.

            if(currentPlayer.DidIShootHereBefore(xs,ys)){
                result = "You already shot here, try again...";
            }else{
                for (Boat b : waitingPlayer.fleet){
                    if(b.AmIHit(xs,ys)){
                        result = "Hit a boat!";
                        if (b.DidISink()) {
                            result = "Boat has sunk.";
                            playingGame = !(waitingPlayer.EndGame());
                        }
                        break;
                    }
                }
                currentPlayer.AddShot(xs,ys);
                Player[] Players = SwitchTurns(currentPlayer, waitingPlayer);
                currentPlayer = Players[0];
                waitingPlayer = Players[1];
            }
            System.out.println(result);
        }
        System.out.println(waitingPlayer.name + " won the game!"); //players switch before this line is called
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
        Boat boat1 = new Boat();
        boat1.x = new int[] {0,0,0};
        boat1.y = new int[] {0,1,2}; // boat on a1, a2, a3

        Boat boat2 = new Boat();
        boat2.x = new int[] {3,4};
        boat2.y = new int[] {4,4}; // boat on d5, e5
        fleet = new Boat[]{boat1, boat2};

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

