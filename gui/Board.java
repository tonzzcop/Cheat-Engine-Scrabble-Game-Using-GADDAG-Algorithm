package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.*;
import logic.Peraturan;
public class Board {
  private JTextField board;
  private JPanel panel;
  private String bonus;
  private String value;
  private Color boardColor;
  private Peraturan peraturan;
  private String oldValue;
  public Board(String b,Color bc,Peraturan p){
    peraturan = p;

    oldValue = "";

    Font font1 = new Font("SansSerif", Font.BOLD, 20);

    boardColor = bc;

    board = new JTextField("",2);
    board.setLocation(5, 5);
    board.setSize(150,20);
    board.setFont(font1);
    board.setHorizontalAlignment(JTextField.CENTER);
    board.setBackground(boardColor);

    board.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if(!board.getText().equals("")){
          char chr = board.getText().charAt(0);
          int ascii = (int) chr;
          if(ascii >= 65 && ascii <= 90){
            oldValue = board.getText();
          }
        }
      }
    });

    board.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        int i = warn();
        if(i == 1){
          SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                board.setText("");
            }
          });
        }
        System.out.println("changedUpdate");
      }
      public void removeUpdate(DocumentEvent e) {
        int i = warn2();
        if(i == 1){
          SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                board.setText("");
            }
          });
        }
        System.out.println("removeUpdate");
      }
      public void insertUpdate(DocumentEvent e) {
        int i = warn();
        if(i == 1){
          SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                board.setText("");
            }
          });
        }
        System.out.println("insertUpdate");
      }

      public int warn() {
        int returnval = 0;
        if(!board.getText().equals("")){
          char chr = board.getText().charAt(0);
          int ascii = (int) chr;
          if(ascii >= 65 && ascii <= 90){
            int tmp = ascii - 65;
            if(peraturan.jumlahHuruf[tmp] > 0){
              oldValue = chr + "";
              peraturan.jumlahHuruf[tmp] -= 1;
              peraturan.cetakJumlahHuruf();
            }else{
              JOptionPane.showMessageDialog(null, "Huruf " + chr + " pada papan dan rack sudah maksimal ("+peraturan.arrJumlahHuruf[tmp]+")");
              returnval = 1;
            }
          }else{
            JOptionPane.showMessageDialog(null, "Input tidak valid. Input A - Z");
            returnval = 1;
          }
        }
        return returnval;
      }

      public int warn2() {
        int returnval = 0;
        System.out.println("oldValue " + oldValue);
        if(!oldValue.equals("")){
          int ascii = (int) oldValue.charAt(0);
          if(ascii >= 65 && ascii <= 90){

            int tmp = ascii - 65;
            if(peraturan.jumlahHuruf[tmp] < peraturan.arrJumlahHuruf[tmp]){
              peraturan.jumlahHuruf[tmp] += 1;
              peraturan.cetakJumlahHuruf();
              oldValue = "";
            }
          }else{
            JOptionPane.showMessageDialog(null, "Input tidak valid. Input A - Z");
            returnval = 1;
          }
        }
        return returnval;
      }

    });

    panel = new JPanel();
    panel.setSize(1,1);
    panel.add(board);

    bonus = b;

    value = "";
  }

  public void setValue(String letter){
    value = letter;
    board.setText(letter);
  }

  public String getValue(){
    return board.getText();
  }

  public String getBonus(){
    return bonus;
  }

  public JTextField getBoard(){
    return board;
  }

  public JPanel getPanelBoard(){
    return panel;
  }

  public void refresh(){
    value = board.getText();
  }

}
