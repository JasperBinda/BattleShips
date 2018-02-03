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

        Player[] players = new Player[]{p1,p2} ;
        Shot shot = new Shot();
        while(playingGame) {
            String result = "You missed.";
            System.out.println(players[0].name + " please enter coordinates to shoot."); //coordinates a1, A1, etc.

            String input = scanner.next();
            shot.FireShot(input);

            if(players[0].DidIShootHereBefore(shot.x,shot.y)){
                result = "You already shot here, try again...";
            }else{
                for (Boat b : players[1].fleet){
                    if(b.AmIHit(shot.x,shot.y)){
                        result = "Hit a boat!";
                        if (b.DidISink()) {
                            result = "Boat has sunk.";
                            playingGame = !(players[1].EndGame());
                        }
                        break;
                    }
                }
                players[0].AddShotToSea(shot.x,shot.y);
                players = SwitchTurns(players, players[0]);
            }
            System.out.println(result);
        }
        System.out.println(players[1].name + " won the game!"); //players switch before this line is called
    }
    static Player[] SwitchTurns(Player[] plays, Player p0){
        plays[0] = plays[1];
        plays[1] = p0;
        return plays;
    }
}

abstract class Sea{
    int[][] field = new int[5][5];
    void AddShotToSea(int x, int y){
        field[x][y] = 1;
    }
    boolean DidIShootHereBefore(int x, int y){
        return (field[x][y] == 1);
    }
}

class Player extends Sea{
    String name;
    Boat[] fleet;
    int destroyedboats;
    void CreateFleet(){
        Boat boat1 = new Boat();
        boat1.xc = new int[] {0,0,0};
        boat1.yc = new int[] {0,1,2}; // boat on a1, a2, a3

        Boat boat2 = new Boat();
        boat2.xc = new int[] {3,4};
        boat2.yc = new int[] {4,4}; // boat on d5, e5
        fleet = new Boat[]{boat1, boat2};
    }
    boolean EndGame(){
        destroyedboats += 1;
        return (destroyedboats == fleet.length);
    }
}

class Boat {
    int[] xc;
    int[] yc;
    int totalhits;
    boolean AmIHit(int x_shot, int y_shot) {
        boolean hit = false;
        for (int i = 0; i < xc.length; i++) {
            if (x_shot == xc[i] && y_shot == yc[i]) {
                hit = true;
                totalhits += 1;
            }
        }
        return hit;
    }
    boolean DidISink(){
        return (totalhits == xc.length);
    }
}

class Shot{
    int x;
    int y;
    void FireShot(String coord){
        char[] shot_xy = coord.toLowerCase().toCharArray();
        x = shot_xy[0] - 97; //transfer a ascii value to int 0, etc.
        y = shot_xy[1] - 49; //transfer 1 ascii value to int 0, etc.
    }
}