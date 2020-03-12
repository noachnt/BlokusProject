import java.util.LinkedList;

public class Player
{
  // to store all the pieces
   public LinkedList<Piece> pieces;
   public boolean firstMove = true;
   public boolean canPlay = true;

   // determine player color
   public Player(int color)
   {

      int[][][] shapes = Piece.getAllShapes(); //get all the pieces from piece.java

      pieces = new LinkedList<Piece>();

      for (int i = 0; i < shapes.length; i++)
      {
         pieces.add(new Piece(shapes[i], color)); //add pieces to linked list
      }
   }

   public int getScore()
   {
      int total = 0;
      for (Piece p : pieces)
      {
         total = total + p.getPoints();
      }
      return total;
   }

}
