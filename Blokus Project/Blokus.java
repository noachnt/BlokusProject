// BLOKUS PROJECT FOR AI LAB
// Group Member: Noach , Matius , Ferinzhy

//////////////////////////////////////////////////////////
//TO DO LIST//                                          //
//- Add 3 more players (DONE)                           //
//- Remove index if already placed (DONE)               //
//- Rotate pieces (DONE)                                //
//- Place Pieces  (DONE)                                //
//- Make corner detection (start from corner too)(DONE) //
//- Make error message if not fulfill rules  (DONE)     //
/////////////////////////////////////////////////////////

// scroll to move the piece , rightclick to flip the pieces :D

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.*;
import java.io.File;

public class Blokus
{
   public static final int players_num = 4; //current player count

   public static class MainFrame extends JFrame
   {
      private Board board;
      private Player[] players; //to assign player to array
      private int turn = -1; //turn holder, default is -1

      private int pieceIndex;
      public Point selected;

      private JPanel mainPanel;
      private JPanel sidePanel;
      private JPanel bottomPanel;
      private JPanel piecesPanel;
      private JPanel boardPanel;

      private JLabel label;
      private ImageIcon boardImage;
      private JButton out_of_moves;
      private JLabel list;

      public MainFrame()
      {
         super("Blokus Project"); //for naming mainframe
         board = new Board();
         players = new Player[players_num];
         players[0] = new Player(Board.BLUE);
         players[1] = new Player(Board.RED);
         players[2] = new Player(Board.YELLOW);
         players[3] = new Player(Board.GREEN);

         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         GUI(); //render the GUI
         start(); //start the game
      }


      private void GUI()
      {
         class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener
         {
            // if mouse clicked, if its button 3 rotate, if else place the actual piece to the board.
            public void mouseClicked(MouseEvent e)
            {
               if (e.getButton() == MouseEvent.BUTTON3) //right click
               {
                  flipPiece(); //flip the piece
               }
               else
               {
                  try
                  {
                     board.placePiece(players[turn].pieces.get(
                        pieceIndex), selected.x - Piece.shapeSize / 2,
                        selected.y - Piece.shapeSize / 2, players[turn].firstMove);
                     drawBoard(); // draw the piece into the board. (actuall placement)
                     players[turn].pieces.remove(pieceIndex); //remove piece if already placed from scroll menu
                     players[turn].firstMove = false;
                     players[turn].canPlay = players[turn].pieces.size() != 0;
                     start();
                  }
                  catch (Board.IllegalMoveException ex)
                  {
                     displayMessage(ex.getMessage(), "ERROR!");
                  }
               }
            }

            public void mousePressed(MouseEvent e)
            {

            }

            public void mouseReleased(MouseEvent e)
            {

            }

            public void mouseEntered(MouseEvent e)
            {

            }

            // if mouse exit the board, un draw the piece
            public void mouseExited(MouseEvent e)
            {
               selected = null;
               board.resetOverlay();
               drawBoard();
            }

            public void mouseDragged(MouseEvent e)
            {

            }

            //if mouse moved on the the board, draw the object (the helper).
            public void mouseMoved(MouseEvent e)
            {
                // get the coordinates to draw the pieces that has been selected
               Point p = board.getCoordinates(e.getPoint(), Board.DEFAULT_RESOLUTION);
               if (!p.equals(selected))
               {
                  selected = p;
                  board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y); //choose the pieces based on current pieces that user choose
                  drawBoard();  //draw the pieces to the board (the helper)
               }
            }

            public void mouseWheelMoved(MouseWheelEvent e)
            {
               if (e.getWheelRotation() > 0)
               {
                  rotateCW(); // rotate clockwise if mouse wheel moved
               }
               else
               {
                  rotateCCW(); //else do counter clockwise
               }
            }
         }

         // no more move .. :<
         class out_of_movesListener implements ActionListener{
           public void actionPerformed(ActionEvent event){
             players[turn].canPlay = false;
             start();
           }
         }


         mainPanel = new JPanel();
         piecesPanel = new JPanel();
         piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));

         JScrollPane scrollPanel = new JScrollPane(piecesPanel);
         scrollPanel.getVerticalScrollBar().setUnitIncrement(Piece.DEFAULT_RESOLUTION / 3);

         scrollPanel.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION - 70, Board.DEFAULT_RESOLUTION - 20));

         list = new JLabel("List of Pieces");
         list.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 30));
         Font jlabelfont = new Font("Courier", Font.BOLD,15);
         list.setFont(jlabelfont);


         sidePanel = new JPanel();
         sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));


         out_of_moves = new JButton("OUT OF MOVES :(");
         out_of_moves.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 100));
         out_of_moves.addActionListener(new out_of_movesListener());
         out_of_moves.setForeground(Color.RED);
         Font out_of_movesfont = new Font("Courier", Font.BOLD,12);
         out_of_moves.setFont(out_of_movesfont);


         boardPanel = new JPanel();

         boardImage = new ImageIcon(board.render());

         label = new JLabel(boardImage);
         BoardClickListener listener = new BoardClickListener();

         label.addMouseListener(listener);
         label.addMouseMotionListener(listener);
         label.addMouseWheelListener(listener);

         // add all item to the frame
         mainPanel.add(out_of_moves);
         boardPanel.add(label);
         sidePanel.add(list);
         sidePanel.add(scrollPanel);
         mainPanel.add(boardPanel);
         mainPanel.add(sidePanel);

         getContentPane().add(mainPanel); // add componenent to the main container
         setVisible(true); //show the board
      }

      //rotate clockwise
      private void rotateCW()
      {
         players[turn].pieces.get(pieceIndex).rotateCW();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }

      //rotate counter clockwise
      private void rotateCCW()
      {
         players[turn].pieces.get(pieceIndex).rotateCCW();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }

      //flip
      private void flipPiece()
      {
         players[turn].pieces.get(pieceIndex).flipOver();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }

      private void drawBoard()
      {
         boardImage.setImage(board.render());
         label.repaint();
      }

      // scroll bar selected border (pieces)
      private void drawBorder()
      {
         JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // current piece border
      }
       // scroll bar not selcted border (pieces)
      private void clearBorder()
      {
         JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // border after been selected by user
      }

      //to dispay error message later
      private void displayMessage(String message, String title)
      {
         JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
      }
      // blokus piece list
      private class PieceLabelClickListener implements MouseListener
      {
        // when mouse clicked the pieces it highlight the border
         public void mouseClicked(MouseEvent e)
         {

            PieceLabel p = (PieceLabel) e.getComponent();
            clearBorder(); //change to default
            pieceIndex = p.pieceIndex; //for choosing pieces.
            drawBorder(); //draw the border
         }

         public void mousePressed(MouseEvent e)
         {

         }

         public void mouseReleased(MouseEvent e)
         {

         }

         public void mouseEntered(MouseEvent e)
         {

         }

         public void mouseExited(MouseEvent e)
         {

         }
      }
      // to start player turn
      private void start()
      {
         turn++;
         turn = turn % players_num;

         if (gameOver())
         {
            gameEnd();
         }

         if (!players[turn].canPlay)
         {
            start();
            return;
         }

         piecesPanel.removeAll();

         for (int i = 0; i < players[turn].pieces.size(); i++)
         {
            PieceLabel pieceLabel =
               new PieceLabel(i, players[turn].pieces.get(i), Piece.DEFAULT_RESOLUTION);
            pieceLabel.addMouseListener(new PieceLabelClickListener());
            pieceLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            piecesPanel.add(pieceLabel);
         }

         pieceIndex = 0;
         drawBorder();
         piecesPanel.repaint();

         pack();
      }

      private boolean gameOver()
      {
         for (int i = 0; i < players_num; i++)
         {
            if (players[i].canPlay) return false;
         }
         return true;
      }


      private void gameEnd()
      {
         StringBuffer sb = new StringBuffer();
         for (int i = 0; i < players_num; i++)
         {
            sb.append(Board.getColorName(getPlayerColor(i)));
            sb.append(": ");
            sb.append(players[i].getScore());
            sb.append("\n");
         }
         JOptionPane.showMessageDialog(this, sb.toString(), "Scoreboard", JOptionPane.INFORMATION_MESSAGE );

         int response = JOptionPane.showConfirmDialog(null, "Play again?", "GAMEOVER!", JOptionPane.YES_NO_OPTION);
         if(response == JOptionPane.YES_OPTION)
         {
           board = new Board();
           players = new Player[players_num];
           players[0] = new Player(Board.BLUE);
           players[1] = new Player(Board.RED);
           players[2] = new Player(Board.YELLOW);
           players[3] = new Player(Board.GREEN);

           setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           GUI(); //render the GUI
           start(); //start the game

         }
         else if(response == JOptionPane.NO_OPTION)
         {
           System.exit(0);
         }

      }


      //get color from player
      private int getPlayerColor(int index)
      {
         switch (index)
         {
            case 0:
              return Board.BLUE;
            case 1:
              return Board.RED;
            case 2:
              return Board.YELLOW;
            case 3:
              return Board.GREEN;
            default: return 0;
         }
      }

   }

   // current piece index holder
   public static class PieceLabel extends JLabel
   {
      public int pieceIndex;

      public PieceLabel(int pieceIndex, Piece p, int size)
      {
         super(new ImageIcon(p.render(size)));
         this.pieceIndex = pieceIndex;
      }
   }


    //main
   public static void main(String[] args)
   {
      MainFrame mf = new MainFrame(); // output the frame
   }
}
