// import javax.swing.*;
// import javax.swing.event.*;
// import java.awt.event.*;
// import java.awt.*;
// import javax.imageio.*;
// import java.io.File;
//
// public class GUI{
//
//   public static void mygui()
//   {
//      class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener
//      {
//         // if mouse clicked, if its button 3 rotate, if else place the actual piece to the board.
//         public void mouseClicked(MouseEvent e)
//         {
//            if (e.getButton() == MouseEvent.BUTTON3) //right click
//            {
//               Blokus b = new Blokus();
//               b.flipPiece(); //flip the piece
//            }
//            else
//            {
//               try
//               {
//                  board.placePiece(players[turn].pieces.get(
//                     pieceIndex), selected.x - Piece.shapeSize / 2,
//                     selected.y - Piece.shapeSize / 2, players[turn].firstMove);
//                  drawBoard(); // draw the piece into the board. (actuall placement)
//                  players[turn].pieces.remove(pieceIndex); //remove piece if already placed from scroll menu
//                  players[turn].firstMove = false;
//                  players[turn].canPlay = players[turn].pieces.size() != 0;
//                  start();
//               }
//               catch (Board.IllegalMoveException ex)
//               {
//                  displayMessage(ex.getMessage(), "ERROR!");
//               }
//            }
//         }
//
//         public void mousePressed(MouseEvent e)
//         {
//
//         }
//
//         public void mouseReleased(MouseEvent e)
//         {
//
//         }
//
//         public void mouseEntered(MouseEvent e)
//         {
//
//         }
//
//         // if mouse exit the board, un draw the piece
//         public void mouseExited(MouseEvent e)
//         {
//            selected = null;
//            board.resetOverlay();
//            drawBoard();
//         }
//
//         public void mouseDragged(MouseEvent e)
//         {
//
//         }
//
//         //if mouse moved on the the board, draw the object (the helper).
//         public void mouseMoved(MouseEvent e)
//         {
//             // get the coordinates to draw the pieces that has been selected
//            Point p = board.getCoordinates(e.getPoint(), Board.DEFAULT_RESOLUTION);
//            if (!p.equals(selected))
//            {
//               selected = p;
//               board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y); //choose the pieces based on current pieces that user choose
//               drawBoard();  //draw the pieces to the board (the helper)
//            }
//         }
//
//         public void mouseWheelMoved(MouseWheelEvent e)
//         {
//            if (e.getWheelRotation() > 0)
//            {
//               rotateCW(); // rotate clockwise if mouse wheel moved
//            }
//            else
//            {
//               rotateCCW(); //else do counter clockwise
//            }
//         }
//      }
//
//      class out_of_movesListener implements ActionListener{
//        public void actionPerformed(ActionEvent event){
//          players[turn].canPlay = false;
//          start();
//        }
//      }
//
//
//      mainPanel = new JPanel();
//      piecesPanel = new JPanel();
//      piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));
//
//      JScrollPane scrollPanel = new JScrollPane(piecesPanel);
//      scrollPanel.getVerticalScrollBar().setUnitIncrement(Piece.DEFAULT_RESOLUTION / 3);
//
//      scrollPanel.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION - 80, Board.DEFAULT_RESOLUTION - 30));
//
//      list = new JLabel("List of Piece");
//      list.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 30));
//
//      sidePanel = new JPanel();
//      sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
//
//      out_of_moves = new JButton("OUT OF MOVES");
//      out_of_moves.setPreferredSize(new Dimension(Piece.DEFAULT_RESOLUTION, 30));
//      out_of_moves.addActionListener(new out_of_movesListener());
//
//      boardPanel = new JPanel();
//
//      boardImage = new ImageIcon(board.render());
//
//      label = new JLabel(boardImage);
//      BoardClickListener listener = new BoardClickListener();
//
//      label.addMouseListener(listener);
//      label.addMouseMotionListener(listener);
//      label.addMouseWheelListener(listener);
//
//      // add all item to the frame
//      boardPanel.add(label);
//      sidePanel.add(list);
//      sidePanel.add(out_of_moves);
//      sidePanel.add(scrollPanel);
//      mainPanel.add(boardPanel);
//      mainPanel.add(sidePanel);
//
//      getContentPane().add(mainPanel); // add componenent to the main container
//      setVisible(true); //show the board
//   }
// }
