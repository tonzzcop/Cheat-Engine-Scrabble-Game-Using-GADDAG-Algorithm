package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.*;
import java.util.LinkedList;
import java.util.Random;
import logic.*;
public class FrameCoba extends JInternalFrame implements ActionListener {
    private Board[][] arrBoard;
    private Board[] arrRack;
    private Trie trie;
    private JLabel lblKata,lblPoin;
    private Peraturan peraturan;
    public FrameCoba(Trie t) {
        super("Scrabble Cheat Enggine",
          true, //resizable
          true, //closable
          true, //maximizable
          true);//iconifiable
        trie = t;
        setSize(950,500);

        peraturan = new Peraturan();

        Font font1 = new Font("SansSerif", Font.BOLD, 20);

        GridLayout boardLayout = new GridLayout(15,15);
        GridLayout rackLayout = new GridLayout(0,8);
        GridLayout keteranganLayout = new GridLayout(5,2);

       	JPanel boardPanel = new JPanel();
       	boardPanel.setLayout(boardLayout);

      	arrBoard = new Board[15][15];
        Color boardColor = Color.WHITE;
        String bonus = "";
      	for(int i=0;i<15;i++){
      		for(int j=0;j<15;j++){

            if( (i == 0 || i== 7 || i== 14) && (j==0 || j==7 || j==14) ){
              boardColor = Color.ORANGE;
              bonus = "TW";
            }else{
              if( (i == 1 || i== 5 || i== 9 || i == 13) && (j==5 || j==9) ){
                boardColor = Color.BLUE;
                bonus = "TL";
              }else{
                boardColor = Color.WHITE;
                bonus = "";
              }
            }

            if((i==7 && j==7) || (i==1 && j==1) || (i==2 && j==2) || (i==3 && j==3) || (i==4 && j==4) ||
                (i==1 && j==13) || (i==2 && j==12) || (i==3 && j==11) || (i==4 && j==10) ||
                (j==1 && i==13) || (j==2 && i==12) || (j==3 && i==11) || (j==4 && i==10) ||
                (i==13 && j==13) || (i==12 && j==12) || (i==11 && j==11) || (i==10 && j==10)
            ){
              boardColor = Color.PINK;
                bonus = "DW";
            }
            if((i==0 && j==3) || (i==0 && j==11) || (i==2 && j==6) || (i==2 && j==8) || (i==3 && j==0) ||
                (i==3 && j==7) || (i==3 && j==14) || (i==6 && j==2) || (i==6 && j==6) ||
                (i==6 && j==8) || (i==6 && j==12) || (i==7 && j==3) || (i==7 && j==11) ||
                (i==8 && j==2) || (i==8 && j==6) ||(i==8 && j==8) || (i==8 && j==12) ||
                (i==11 && j==0) || (i==11 && j==7) || (i==11 && j==14) ||
                (i==12 && j==6) || (i==12 && j==8) ||
                (i==14 && j==3) || (i==14 && j==11)
            ){
              boardColor = new Color(107,188,250);
              bonus = "DL";
            }
      			arrBoard[i][j] = new Board(bonus,boardColor,peraturan);

      			boardPanel.add(arrBoard[i][j].getPanelBoard());
      		}
      	}

        arrRack = new Board[7];
       	JPanel rackPanel = new JPanel();
       	rackPanel.setLayout(rackLayout);
       	rackPanel.setSize(300,200);

      	for(int j=0;j<7;j++){
      		arrRack[j] = new Board("",Color.WHITE,peraturan);
      		rackPanel.add(arrRack[j].getBoard());
      	}

        JButton btnGo = new JButton("Go");
        btnGo.setActionCommand("go");
        btnGo.addActionListener(this);

        JButton btnRandom = new JButton("Random");
        btnRandom.setActionCommand("random");
        btnRandom.addActionListener(this);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        panelButton.add(btnRandom);
        panelButton.add(btnGo);

        rackPanel.add(panelButton);

        JPanel keteranganPanel = new JPanel();
        keteranganPanel.setLayout(keteranganLayout);
        JLabel label = new JLabel("BIRU MUDA", SwingConstants.CENTER);
        label.setBackground(new Color(107,188,250));
        label.setOpaque(true);
        keteranganPanel.add(label);
        keteranganPanel.add(new JLabel("<html>DL (Double Letter)<br> Nilai tiap huruf x2</html>", SwingConstants.CENTER));
        JLabel label2 = new JLabel("BIRU TUA", SwingConstants.CENTER);
        label2.setBackground(new Color(0,0,255));
        label2.setOpaque(true);
        keteranganPanel.add(label2);
        keteranganPanel.add(new JLabel("<html>TL (Triple Letter)<br> Nilai tiap huruf x3</html>", SwingConstants.CENTER));
        JLabel label3 = new JLabel("PINK", SwingConstants.CENTER);
        label3.setBackground(new Color(230,0,255));
        label3.setOpaque(true);
        keteranganPanel.add(label3);
        keteranganPanel.add(new JLabel("<html>DW (Double Word)<br> Nilai kata x2</html>", SwingConstants.CENTER));
        JLabel label4 = new JLabel("ORANGE", SwingConstants.CENTER);
        label4.setBackground(new Color(255,171,0));
        label4.setOpaque(true);
        keteranganPanel.add(label4);
        keteranganPanel.add(new JLabel("<html>TW (Triple Word)<br> Nilai kata x3</html>", SwingConstants.CENTER));
        lblKata = new JLabel("", SwingConstants.CENTER);
        lblPoin = new JLabel("", SwingConstants.CENTER);
        lblKata.setOpaque(true);
        keteranganPanel.add(lblKata);
        keteranganPanel.add(lblPoin);
        getContentPane().add(boardPanel, BorderLayout.CENTER);
        getContentPane().add(rackPanel, BorderLayout.SOUTH);
        getContentPane().add(keteranganPanel, BorderLayout.EAST);

        //frame.pack();
        setVisible(true);
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("go".equals(e.getActionCommand())) { //new
            process();
        }else if ("random".equals(e.getActionCommand())) { //new
            random();
        }
    }

    public void random(){
      Random rand = new Random();
      int  n = rand.nextInt((25 - 0) + 1) + 0;
      boolean loop = true;
      for (int i=0;i<arrRack.length ;i++ ) {
        System.out.println("RACK : " + i + " : " + arrRack[i].getValue());
        if(arrRack[i].getValue().equals("")){
          loop = true;
          while(loop){
            n = rand.nextInt((25 - 0) + 1) + 0;
            if(peraturan.jumlahHuruf[n] != 0){
              String str = Character.toString ((char) (n+65));
              arrRack[i].setValue(str);
              loop = false;
            }
          }
        }
      }
    }

    public LinkedList<RecordWord> process(){
      for (int i=0;i<arrBoard.length ;i++ ) {
        for (int o=0;o<arrBoard[0].length ;o++ ) {
            arrBoard[i][o].setValue(arrBoard[i][o].getBoard().getText());
        }
      }
      for (int o=0;o<arrRack.length ;o++ ) {
          arrRack[o].setValue(arrRack[o].getBoard().getText());
      }

      GADDAG gaddag = new GADDAG(trie,arrBoard);
      LinkedList<AnchorSquare> result = gaddag.getAnchorSquare(arrBoard);
      String msg = "";
      String rack = "";

      int tmpBatas = result.indexOf(result.getLast());
      //JOptionPane.showMessageDialog(this,result+","+tmpBatas);

      for (int i=0;i<arrRack.length ;i++ ) {
        rack += arrRack[i].getValue();
      }

      int batas = result.indexOf(result.getLast());
      for (int i=0;i<=batas;i++ ) {
        System.out.println(" ANCHOR SQUARE "+i);
        String tmpString = arrBoard[result.get(i).yParent][result.get(i).xParent].getValue();
        //System.out.println(result.get(i).y+","+result.get(i).x+"-"+result.get(i).yParent+","+result.get(i).xParent+"-"+tmpString);
        char tmpChar = tmpString.charAt(0);

        NodeTrie[] tmpNode = trie.getRoot().getChildWhere(tmpChar);
        if(tmpNode!=null){

          if(tmpNode[0]!=null){
            System.out.println("FrameCoba : gaddag.Gen(0,'',"+rack+","+tmpNode[0]+","+result.get(i)+");");
            gaddag.Gen(0,"",rack,tmpNode[0],result.get(i),result.get(i).horisontal,-1,false);
          }

          if(tmpNode[1]!=null){
            System.out.println("FrameCoba : gaddag.Gen(0,'',"+rack+","+tmpNode[1]+","+result.get(i)+");");
            gaddag.Gen(0,"",rack,tmpNode[1],result.get(i),result.get(i).horisontal,-1,false);
          }else{
            System.out.println("KOSONG");
          }
        }
      }

      //LinkedList<RecordWord> recordWord = gaddag.getRecord();
      LinkedList<RecordWord> recordWord = gaddag.sortingRecord();

      String hasil = "<html>Hasil Generate : <br>";

      if(!recordWord.isEmpty()){
        batas = recordWord.indexOf(recordWord.getLast());
        for (int i=0;i<=batas;i++ ) {
          hasil = hasil + recordWord.get(i) + "<br>";
        }
      }
      hasil = hasil + "<html>";
      JOptionPane.showMessageDialog(this,hasil);

      if(!recordWord.isEmpty()){
          updateBoard(gaddag.getBestSolution());
      }

      return recordWord;
    }

    public void updateBoard(RecordWord bestrecordWord){
      String word = bestrecordWord.value;
      String wordShow = "";
      int y = bestrecordWord.koordinat.y;
      int x = bestrecordWord.koordinat.x;
      int pos = 0;
      boolean horisontal = bestrecordWord.koordinat.horisontal;

      for (int i=0;i<word.length() ;i++ ) {
        if(word.charAt(i) == '>'){
          wordShow += arrBoard[bestrecordWord.koordinat.yParent][bestrecordWord.koordinat.xParent].getValue()+"";
          pos = 1;

        }else{
          if((bestrecordWord.koordinat.yParent < bestrecordWord.koordinat.y && pos == 0) ||
              (bestrecordWord.koordinat.xParent < bestrecordWord.koordinat.x && pos == 0)){
            pos = -2;
          }
          if(horisontal){
            // if(koordinat.xParent<koordinat.x && pos <= 0){
            //   pos = 1;
            // }
            if(pos == 1){
              x = bestrecordWord.koordinat.xParent + 1;
              pos++;
            }else if(pos > 1){
              x = bestrecordWord.koordinat.xParent + pos;
              pos++;
            }else{
              x = bestrecordWord.koordinat.x + pos;
              pos--;
            }
          }else{
            // if(koordinat.yParent<koordinat.y && pos <= 0){
            //   pos = 1;
            // }
            if(pos == 1){
              y = bestrecordWord.koordinat.yParent + 1;
              pos++;
            }else if(pos > 1){
              y = bestrecordWord.koordinat.yParent + pos;
              pos++;
            }else{
              y = bestrecordWord.koordinat.y + pos;
              pos--;
            }
          }
          System.out.println("SET PAPAN "+y+","+x+" -> "+word.charAt(i));

          if(arrBoard[y][x].getValue().equals("")){
            updateRack(word.charAt(i));
            arrBoard[y][x].getBoard().setForeground(Color.red);
          }
          arrBoard[y][x].setValue(word.charAt(i)+"");
          if(pos<=0)
            wordShow = word.charAt(i) + wordShow;
          else
            wordShow = wordShow + word.charAt(i) ;
        }
      }
      lblKata.setText("<html>WORD : <br>" + wordShow + "</html>");
      lblPoin.setText("<html>POINT : <br>" + Integer.toString(bestrecordWord.bonus) + "</html>");
    }

    public void updateRack(char v){
        for (int i=0;i<arrRack.length ;i++ ) {
          if(!arrRack[i].getValue().equals("")){
            if(arrRack[i].getValue().charAt(0) == v){
                arrRack[i].setValue("");
                i = 10;
            }
          }
        }
    }

    public Board[][] getArrBoard(){
      return arrBoard;
    }

    public Board[] getRackBoard(){
      return arrRack;
    }
}
