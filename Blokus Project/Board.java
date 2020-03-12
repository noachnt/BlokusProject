import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Point;

public class Board
{
   public static final int blank = 0;
   public static final int BLUE = 1;
   public static final int RED = 2;
   public static final int YELLOW = 3;
   public static final int GREEN = 4;

   //error message
   public static final String out_of_border_error = "ERROR! PLACE THE PIECES INSIDE OF THE BORDER PLS!!! ";
   public static final String edges_error = "ERROR! EDGES PLS!";
   public static final String overlap_error = "ERROR! OVERLAP ";
   public static final String start_corner_error = "ERROR! START FROM YOUR CORNER!";
   public static final String same_color_corner_error = "ERROR! PLACE BLOCK ON THE EDGES OF YOUR COLOR PLS!";

   // add more color later
   public static final int boardSize = 20;
   public static final int DEFAULT_RESOLUTION = 600;

   public static final Color boardColor = Color.WHITE;
   public static final Color gridColor = Color.BLACK;



   private int[][] grid;
   private int[][] overlay;

   public Board()
   {
      grid = new int[boardSize][boardSize];
      overlay = new int[boardSize][boardSize];
      reset(grid);
      reset(overlay);
    }

    //to check if its a valid move
    public boolean validMove(Piece p, int horizontal, int vertical, boolean firstMove) throws IllegalMoveException
    {
       boolean corner = false;
       for (int x = 0; x < Piece.shapeSize; x++)
       {
          for (int y = 0; y < Piece.shapeSize; y++)
          {
             int value = p.getValue(x, y);
             boolean inBounds = isInBounds(x + horizontal, y + vertical);

             if (inBounds)
             {
                int gridValue = grid[x + horizontal][y + vertical];
                if (gridValue != blank)
                {
                   if (value == Piece.inner) throw new IllegalMoveException(overlap_error); // cannot overlap pieces error
                   if (gridValue == p.getColor())
                   {
                      if (value == Piece.side) throw new IllegalMoveException(edges_error); // cannot share edge erorr
                      if (value == Piece.corner) corner = true;
                   }
                }
                else
                {
                   if (firstMove && value == Piece.inner && new Point(x + horizontal, y + vertical).equals(getCorner(p.getColor())))
                      corner = true;
                }
             }
             else
             {
                if (value == Piece.inner) throw new IllegalMoveException(out_of_border_error); //to output out of border error
             }
          }
       }

       if (!corner)
       {
         if (firstMove)
         {
           throw new IllegalMoveException(start_corner_error); // start from your corner
         }
         else
         {
           throw new IllegalMoveException(same_color_corner_error); //
         }

       }


       return true;
    }

    // if not a valid move throw exception
    public boolean validMove(Piece p, int horizontal, int vertical) throws IllegalMoveException
    {
       return validMove(p, horizontal, vertical, false);
    }

    // to place pieces
   public void placePiece(Piece p, int xOff, int yOff, boolean firstMove) throws IllegalMoveException
   {

     validMove(p, xOff, yOff, firstMove);

      for (int x = 0; x < Piece.shapeSize; x++)
      {
         for (int y = 0; y < Piece.shapeSize; y++)
         {
            if (p.getValue(x, y) == Piece.inner) grid[x + xOff][y + yOff] = p.getColor();
         }
      }
   }

   // place piece error
   public void placePiece(Piece p, int xOff, int yOff) throws IllegalMoveException
   {
      placePiece(p, xOff, yOff, false);
   }

   public Point getCoordinates(Point pixel, int res)
   {
      return new Point(pixel.x / (res / boardSize), pixel.y / (res / boardSize));
   }

   public void overlay(Piece p, int xOff, int yOff)
   {
      reset(overlay);

      for (int x = 0; x < Piece.shapeSize; x++)
      {
         for (int y = 0; y < Piece.shapeSize; y++)
         {
            if (isInBounds(x + xOff - Piece.shapeSize / 2, y + yOff - Piece.shapeSize / 2) && p.getValue(x, y) == Piece.inner)
            {
               overlay[x + xOff - Piece.shapeSize / 2][y + yOff - Piece.shapeSize / 2] = p.getColor();
            }
         }
      }
   }

   public BufferedImage render()
   {
      return render(DEFAULT_RESOLUTION);
   }

   public BufferedImage render(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int cellSize = size / (boardSize);
      Graphics2D g = (Graphics2D) image.getGraphics();

      for (int x = 0; x < boardSize; x++)
      {
         for (int y = 0; y < boardSize; y++)
         {
            g.setColor(getColor(grid[x][y]));
            if (overlay[x][y] != blank)
            {
               g.setColor(mix(g.getColor(), getColor(overlay[x][y]), 0.1f)); // 0.1f to tell program to assign 0.1 as a float
            }
            g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
            g.setColor(gridColor);
            g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);

            if (grid[x][y] == blank)
            {
               boolean corner = false;
               Point p = new Point(x, y);

               //to color corner with color
               if (getCorner(BLUE).equals(p))
               {
                  g.setColor(getColor(BLUE));
                  corner = true;
               }
               else if (getCorner(RED).equals(p))
               {
                  g.setColor(getColor(RED));
                  corner = true;
               }
               else if (getCorner(GREEN).equals(p))
               {
                  g.setColor(getColor(GREEN));
                  corner = true;
               }
               else if (getCorner(YELLOW).equals(p))
               {
                  g.setColor(getColor(YELLOW));
                  corner = true;
               }
               if (corner)
               { // draw corner shape as rectangle
                  g.fillRect(x * cellSize + cellSize / 2 - cellSize / 6, y * cellSize + cellSize / 2 - cellSize / 6, cellSize / 3, cellSize / 3);
               }
            }

         }
      }
      return image;
   }

   private Color mix(Color color1, Color color2, float weight)
   {
     //to get mix of color
     int red = (int)(color1.getRed() * weight + color2.getRed() * (1 - weight)); //get red color
     int green = (int)(color1.getGreen() * weight + color2.getGreen() * (1 - weight)); //get green color
     int blue = (int)(color1.getBlue() * weight + color2.getBlue() * (1 - weight)); //get blue color
     return new Color(red, green, blue); // can mix color
   }

   public void resetOverlay()
   {
      reset(overlay);
   }

   private void reset(int[][] array)
   {
      for (int x = 0; x < boardSize; x++)
         for (int y = 0; y < boardSize; y++)
            array[x][y] = blank;
   }

   // if inside the board
   private boolean isInBounds(int x, int y)
   {
      return (x >= 0 && y >= 0 && x < boardSize && y < boardSize);
   }


   // to get each color starting corner
   private Point getCorner(int color)
   {
      switch (color)
      {
         case BLUE:
            return new Point(0, 0);
         case GREEN:
            return new Point(0, boardSize - 1);
         case YELLOW:
            return new Point(boardSize - 1, boardSize - 1);
         case RED:
            return new Point(boardSize - 1, 0);
         default:
            throw new IllegalArgumentException();
      }
   }


   public static Color getColor(int color)
   {
      //switch case to get color
      switch (color)
      {
         case BLUE:
            return Color.BLUE;
         case RED:
            return Color.RED;
         case YELLOW:
            return Color.YELLOW;
         case GREEN:
            return Color.GREEN;
         default:
            return boardColor;
      }
   }

   public static String getColorName(int color)
   {
      switch (color)
      {
         case BLUE:
          return "Blue";
         case RED:
          return "Red";
         case YELLOW:
          return "Yellow";
         case GREEN:
          return "Green";
         default: return "Unknown";
      }
   }

   public static class IllegalMoveException extends Exception
   {
      public IllegalMoveException()
      {
         super();
      }

      //to apply string to illegal move exception
      public IllegalMoveException(String message)
      {
        super(message);
      }

   }
}
