import java.util.Scanner;

public class Battle{
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean playingGame = true;
        Player p1 = new Player();
        p1.name = "Player1";
        p1.sea.CreateFleet();

        Player p2 = new Player();
        p2.name = "Player2";
        p2.sea.CreateFleet();

        Player[] players = new Player[]{p1,p2} ;
        Shot shot = new Shot();
        while(playingGame) {
            String result = "You missed.";
            System.out.println(players[0].name + " please enter coordinates to shoot."); //a1, A1, ... , e5

            String input = scanner.next();
            shot.FireShot(input);

            if(players[0].DidIShootHereBefore(shot.x,shot.y)){
                result = "You already shot here, try again...";
            }else{
                for (Boat b : players[1].sea.fleet){
                    if(b.AmIHit(shot.xy)){
                        result = "Hit a boat!";
                        if (b.DidISink()) {
                            result = "Boat has sunk.";
                            playingGame = !(players[1].EndGame());
                        }
                        break;
                    }
                }
                players[0].AddShotToField(shot.x,shot.y);
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

class Sea{
    Boat[] fleet;
    void CreateFleet(){
        Boat boat1 = new Boat();
        boat1.position = new String[] {"0 0","0 1","0 2"}; // boat on a1, a2, a3

        Boat boat2 = new Boat();
        boat2.position = new String[] {"3 4","4 4"}; // boat on d5, e5
        fleet = new Boat[]{boat1, boat2};
    }
}

class Player{
    String name;
    Sea sea = new Sea();
    private int[][] field = new int[5][5];
    private int destroyedboats;
    void AddShotToField(int x, int y){
        field[x][y] = 1;
    }
    boolean DidIShootHereBefore(int x, int y){
        return (field[x][y] == 1);
    }
    boolean EndGame(){
        destroyedboats += 1;
        return (destroyedboats == sea.fleet.length);
    }
}

class Boat {
    String[] position;
    private int totalhits;
    boolean AmIHit(String shot) {
        boolean hit = false;
        for (String p : position) {
            if (p.equals(shot)){
                hit = true;
                totalhits += 1;
            }
        }
        return hit;
    }
    boolean DidISink(){
        return (totalhits == position.length);
    }
}

class Shot{
    int x;
    int y;
    String xy;
    void FireShot(String coord){
        char[] shot = coord.toLowerCase().toCharArray();
        x = shot[0]-97; y = shot[1]-49;
        xy = String.valueOf(x)+" "+String.valueOf(y);
    }
}