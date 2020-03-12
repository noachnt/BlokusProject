import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Piece
{
   public static final int shapeSize = 7;
   public static final int inner = 3; //inside part
   public static final int side = 2; //side part
   public static final int corner = 1; //corner
   public static final int voids = 0; //else

   public static final int DEFAULT_RESOLUTION = 120;

   private int[][] grid;
   private int color;

   public Piece(int[][] shape, int color)
   {
      if (shape.length != shapeSize || shape[0].length != shapeSize)
      {
        //error handling (the dimension of pieces must be 7x7)
         throw new IllegalArgumentException("array must be 7x7");
      }

      //generate grid and apply color to the board
      grid = (int[][]) shape.clone();
      this.color = color;
   }

   //to rotate piece clock wise
   public void rotateCW()
   {
      int[][] temp = new int[shapeSize][shapeSize];

      for (int x = 0; x < shapeSize; x++)
         for (int y = 0; y < shapeSize; y++)
            temp[shapeSize - y - 1][x] = grid[x][y];

      grid = temp;
   }

   // to rotate piece counter clockwise
   public void rotateCCW()
   {
      int[][] temp = new int[shapeSize][shapeSize];

      for (int x = 0; x < shapeSize; x++)
         for (int y = 0; y < shapeSize; y++)
            temp[y][shapeSize - x - 1] = grid[x][y];

      grid = temp;
   }

   //to flip piece
   public void flipOver()
   {
      int[][] temp = new int[shapeSize][shapeSize];

      for (int x = 0; x < shapeSize; x++)
         for (int y = 0; y < shapeSize; y++)
            temp[shapeSize - x - 1][y] = grid[x][y];

      grid = temp;
   }

   public int getValue(int x, int y)
   {
      return grid[x][y];
   }

   public int getColor()
   {
      return color;
   }

   // to get points
   public int getPoints()
   {
      int points = 0;
      for (int y = 0; y < shapeSize; y++)
         for (int x = 0; x < shapeSize; x++)
            if (grid[x][y] == inner) points++;
      return points;
   }


   public BufferedImage render(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int cellSize = size / (shapeSize);
      Graphics2D g = (Graphics2D) image.getGraphics();

      g.setColor(Color.WHITE);
      g.fillRect(0, 0, size, size);

      for (int x = 0; x < shapeSize; x++)
      {
         for (int y = 0; y < shapeSize; y++)
         {
            if (grid[x][y] == inner)
            {
               g.setColor(Board.getColor(color));
               g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
               g.setColor(Color.BLACK);
               g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
         }
      }
      return image;
   }


   //string handler
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      for (int y = 0; y < shapeSize; y++)
      {
         for (int x = 0; x < shapeSize; x++)
         {
            sb.append(grid[x][y]);
            sb.append(" ");
         }
         sb.append("\n");
      }
      return sb.toString();
   }


    // all shapes of blokus list
   public static int[][][] getAllShapes()
   {
      int[][][] shapes = new int[21][shapeSize][shapeSize];
      int i = 0;

      shapes[i++] = new int[][] { // * * * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {1, 2, 2, 2, 2, 2, 1},
         {2, 3, 3, 3, 3, 3, 2},
         {1, 2, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * * * *
         {0, 0, 0, 0, 0, 0, 0}, //   *
         {0, 1, 2, 1, 0, 0, 0},
         {0, 2, 3, 2, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //   * * *
         {0, 0, 1, 2, 1, 0, 0},   // * *
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0}, //   *
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0}, //   *
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * * *
         {0, 0, 0, 0, 0, 0, 0},   // *   *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 2, 3, 2, 3, 2, 0},
         {0, 1, 2, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, //   * *
         {0, 0, 0, 1, 2, 1, 0}, // * *
         {0, 0, 1, 2, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 1, 2, 1, 0, 0},   // *
         {0, 0, 2, 3, 2, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 1, 2, 1, 0, 0},   // *
         {0, 0, 2, 3, 2, 0, 0},   // * * *
         {0, 0, 2, 3, 2, 2, 1},
         {0, 0, 2, 3, 3, 3, 2},
         {0, 0, 1, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 1, 2, 2, 1, 0},   //     *
         {0, 0, 2, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };


      shapes[i++] = new int[][] { // * * * *
         {0, 0, 1, 2, 1, 0, 0},   //
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0},   //   * *
         {0, 0, 1, 2, 2, 1, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0}, //   * *
         {0, 1, 2, 2, 1, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //   *
         {0, 0, 0, 0, 0, 0, 0}, // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 2, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { //
         {0, 0, 0, 0, 0, 0, 0},   // * * *
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   // * *
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // * *
         {0, 0, 0, 0, 0, 0, 0},   //
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      shapes[i++] = new int[][] { // *
         {0, 0, 0, 0, 0, 0, 0},   //
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };

      return shapes;
   }
}
