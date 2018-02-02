import java.util.Scanner;

public class Battle{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean PlayingGame = true;
        Sea sea1 = new Sea();
        Sea sea2 = new Sea();

        Player p1 = new Player();
        p1.name = "Player1";
        p1.sea = sea1;
        Boat boat1 = new Boat();
        boat1.x = new int[] {1,1,1};
        boat1.y = new int[] {1,2,3}; // boat on a1, a2, a3
        p1.fleet = new Boat[]{boat1};

        Player p2 = new Player();
        p2.name = "Player2";
        p2.sea = sea2;
        Boat boat2 = new Boat();
        boat2.x = new int[] {2,2,2};
        boat2.y = new int[] {1,2,3}; // boat on b1, b2, b3
        p2.fleet = new Boat[]{boat2};

        Player CurrentPlayer = p1;
        Player WaitingPlayer = p2;
        while(PlayingGame) {
            System.out.println(CurrentPlayer.name + " please enter coordinates to shoot."); //coordinates a1, A1, etc.
            String input = scanner.next();
            char[] shot_xy = input.toLowerCase().toCharArray();
            int xs = shot_xy[0] - 96; //transfer a ascii value to int 0, etc.
            int ys = shot_xy[1] - 48; //transfer 1 ascii value to int 0, etc.

            if(CurrentPlayer.sea.DidIShootHereBefore(xs,ys)){
                System.out.println("You already shot here, try again...");
            }else{
                if(WaitingPlayer.fleet[0].AmIHit(xs,ys)){
                    System.out.println("Hit a boat!");
                    if(WaitingPlayer.fleet[0].DidISink()){
                        System.out.println("Boat has sunk.");
                    }
                }else{
                    System.out.println("You missed.");
                }
                CurrentPlayer.sea.AddShot(xs,ys);
                Player[] Players = SwitchTurns(CurrentPlayer, WaitingPlayer);
                CurrentPlayer = Players[0];
                WaitingPlayer = Players[1];
            }
        }
    }
    static Player[] SwitchTurns(Player c, Player w){
        return new Player[]{w,c};
    }

}
class Player{
    String name;
    Sea sea; //keep track of where you shot
    Boat[] fleet;
    void CreateFleet(){
    }
}
class Sea{
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

