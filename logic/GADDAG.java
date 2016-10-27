package logic;
import gui.Board;
import java.util.LinkedList;
import java.awt.Color;

public class GADDAG{
  private Trie trie;
  private Board[][] arrBoard;
  private String record = "";
  LinkedList<RecordWord> recordWord ;

  public GADDAG(){

  }

  public GADDAG(Trie t){
    trie = t;
  }

  public GADDAG(Trie t,Board[][] ar){
    trie = t;
    arrBoard = ar;
    recordWord = new LinkedList<RecordWord>();
  }

  public LinkedList<RecordWord> getRecord(){
    return recordWord;
  }

  public String[] generate(String str2){

    String str = str2.toUpperCase();
    String[] result = new String[str.length()];
    String depan = ">" ,belakang = "";
    int prefix = 1;

    for(int i=0;i<str.length();i++){
      belakang = str.substring(prefix);
      depan = str.charAt(prefix-1) + depan;

      result[i] = depan + belakang;

      System.out.println(result[i]);
    	trie.addWord(result[i]);
    	System.out.println("====");

      prefix++;
    }
    return result;
  }

  public RecordWord getBestSolution(){
    if(!record.isEmpty()){
      LinkedList<RecordWord> record = sortingRecord();
      return record.get(0);
    }else{
      RecordWord val = null;
      return val;
    }
  }

  public LinkedList<AnchorSquare> getAnchorSquare(Board[][] arrBoard){
    String result = "";
    LinkedList<AnchorSquare> boardList = new LinkedList<AnchorSquare>();
    int count = 0;
      for (int i=0;i<arrBoard.length ;i++ ) {
        for (int o=0;o<arrBoard[0].length ;o++ ) {
          if(!arrBoard[i][o].getValue().equals("")){
            AnchorSquare tmp = null;
            if((o-1) >= 0){
              if(arrBoard[i][o-1].getValue().equals("")){
                tmp = new AnchorSquare(i,o-1,i,o,true);//kiri
                boardList.add(tmp);
                System.out.println(i+","+(o-1)+"kiri "+arrBoard[i][o].getValue()+i+","+o);
                //arrBoard[i][o-1].getBoard().setBackground(Color.YELLOW);
              }
            }

            if((o+1) < 15){
              if(arrBoard[i][o+1].getValue().equals("")){
                tmp = new AnchorSquare(i,o+1,i,o,true);//kanan
                boardList.add(tmp);
                System.out.println(i+","+(o+1)+"kanan "+arrBoard[i][o].getValue()+i+","+o);
                //arrBoard[i][o+1].getBoard().setBackground(Color.YELLOW);
              }
            }

            if((i-1) >= 0){
              if(arrBoard[i-1][o].getValue().equals("")){
                tmp = new AnchorSquare(i-1,o,i,o,false);//atas
                boardList.add(tmp);
                System.out.println((i-1)+","+o+"atas "+arrBoard[i][o].getValue()+i+","+o);
                //arrBoard[i-1][o].getBoard().setBackground(Color.YELLOW);
              }
            }

            if((i+1) < 15){
              if(arrBoard[i+1][o].getValue().equals("")){
                tmp = new AnchorSquare(i+1,o,i,o,false);//bawah
                boardList.add(tmp);
                System.out.println((i+1)+","+o+"bawah "+arrBoard[i][o].getValue()+i+","+o);
                //arrBoard[i+1][o].getBoard().setBackground(Color.YELLOW);
              }
            }
          }
        }
      }
    return boardList;
  }

  public boolean Gen(int pos,String word,String rack,NodeTrie arc,AnchorSquare koordinat,boolean horisontal,int level,boolean setarti){
    boolean returnVal = false;
    level++;
    int x = koordinat.x;
    int y = koordinat.y;

    if(horisontal){
      if(koordinat.xParent<koordinat.x && pos <= 0){
        pos = 1;
      }
      if(pos == 1){
        x = koordinat.xParent + 1;
      }else if(pos > 1)
        x = koordinat.xParent + pos;
      else
        x = koordinat.x + pos;

    }else{
      if(koordinat.yParent<koordinat.y && pos <= 0){
        pos = 1;
      }
      if(pos == 1){
        y = koordinat.yParent + 1;
      }else if(pos > 1)
        y = koordinat.yParent + pos;
      else
        y = koordinat.y + pos;
    }
    //JIKA AnchorSquare BERADA DI BELAKANG SEBUAH KATA
    if((koordinat.yParent < koordinat.y && pos == 0) || (koordinat.xParent < koordinat.x && pos == 0)){
      Gen(-2,word,rack,arc,koordinat,horisontal,level,setarti);
    }

    if( y < 15 && y >= 0 && x < 15 && x >= 0 ){//PEMBATAS GERAK RECURSIVE
      if(!arrBoard[y][x].getValue().equals("")){//JIKA KOORDINAT SUDAH ADA HURUF
        System.out.println(level+" Papan " + y + "," + x + " Ada huruf " + arrBoard[y][x].getValue().charAt(0));

        NodeTrie[] newarc = arc.getChildWhere(arrBoard[y][x].getValue().charAt(0));

        GoOn(pos,arrBoard[y][x].getValue().charAt(0),word,rack,newarc[0],arc,koordinat,horisontal,level,setarti);
        GoOn(pos,arrBoard[y][x].getValue().charAt(0),word,rack,newarc[1],arc,koordinat,horisontal,level,setarti);
      }else{//MENGECEK HURUF DI RACK YANG BISA DIPASANG
        for (int i=0;i<rack.length() ;i++ ) {
          System.out.println(level+" Papan " + y + "," + x + " Dipasang huruf " + rack.charAt(i) + " POS:"+pos);

          StringBuilder tmpRackBuild = new StringBuilder(rack);
          tmpRackBuild.deleteCharAt(i);

          String tmpRack = tmpRackBuild.toString();

          NodeTrie[] newarc = arc.getChildWhere(rack.charAt(i));

          if(newarc[0]!=null && setarti == false){
            if(newarc[1]!=null){
              System.out.println("-SET ARTI BERUBAH JADI " + setarti);
              pos = 1;
            }
            setarti = true;
            System.out.println("-SET ARTI BERUBAH JADI " + setarti);
          }
          boolean tmpreturnVal1 = GoOn(pos,rack.charAt(i),word,tmpRack,newarc[0],arc,koordinat,horisontal,level,setarti);
          setarti = false;
          boolean tmpreturnVal2 = GoOn(pos,rack.charAt(i),word,tmpRack,newarc[1],arc,koordinat,horisontal,level,setarti);
          if(tmpreturnVal2 || tmpreturnVal1)
            returnVal = true;
        }
      }
      System.out.println(level+" RECORD : "+record);
    }

    return returnVal;
  }

  private boolean GoOn(int pos, char l, String word,String rack,NodeTrie newarc,NodeTrie arc,AnchorSquare koordinat,boolean horisontal,int level,boolean setarti){
    boolean returnVal = false;
    level++;
    int x = koordinat.x;
    int y = koordinat.y;

    if(horisontal){
      if(koordinat.xParent<koordinat.x && pos <= 0){
        pos = 1;
      }
      if(pos == 1){
        x = koordinat.xParent + 1;
      }else if(pos > 1)
        x = koordinat.xParent + pos;
      else
        x = koordinat.x + pos;

    }else{
      if(koordinat.yParent<koordinat.y && pos <= 0){
        pos = 1;
      }
      if(pos == 1){
        y = koordinat.yParent + 1;
      }else if(pos > 1)
        y = koordinat.yParent + pos;
      else
        y = koordinat.y + pos;
    }

    if(newarc == null){//HURUF TIDAK BISA DIPASANG
      System.out.println(level+" Huruf " + l + " Tidak Bisa dipasang di " + y + "," + x + " Karena tidak ada di " + arc);
    }else{
      System.out.println(level+" Huruf " + newarc + " Bisa dipasang di " + y + "," + x + " Karena ada di " + arc);

      if(pos <= 0){//MEMBACA PAPAN KE KIRI
        boolean tmpP = false;
        if(newarc.getHuruf() != '>'){//JIKA MENEMUKAN PREFIX POS JADI 1
          word = word + "" + l ;
          pos = pos - 1;
        }else{
          word = word + ">";
          if(pos == 0){
            setarti = false;
            System.out.println("3SET ARTI BERUBAH JADI " + setarti);
          }
          pos = 1;
          tmpP = true;
          if(horisontal)
            System.out.println("Posisi jadi " + y + "," + (koordinat.xParent+1));
          else
            System.out.println("Posisi jadi " + (koordinat.yParent+1) + "," + x);

        }
        if(koordinat.xParent < x){
          x = koordinat.xParent - 1;
        }
        if(newarc.getArti() && setarti){//JIKA MEMILIKI ARTI
          if(cekJalurPerHuruf(rack,word,koordinat,horisontal,level))
            cekJalur(pos,y,x,word,koordinat,horisontal,level);
        }

        if(tmpP && setarti){
          Gen(pos,word,(l+rack),newarc,koordinat,horisontal,level,setarti);
        }else{
          Gen(pos,word,rack,newarc,koordinat,horisontal,level,setarti);
        }
        System.out.println("");
      }else if(pos > 0){//MEMBACA PAPAN KE KANAN
        boolean tmpP = false;
        if(newarc.getHuruf() != '>'){
          word = word + "" + l ;
        }else{
          word = word + ">";
          tmpP = true;
          System.out.println("Posisi jadi "+pos);
        }
        if(newarc.getArti() && setarti){
          System.out.println("SETARTI : " + setarti);
          returnVal = true;
          if(cekJalurPerHuruf(rack,word,koordinat,horisontal,level))
            cekJalur(pos,y,x,word,koordinat,horisontal,level);
        }
        if(tmpP){
          Gen(pos,word,(l+rack),newarc,koordinat,horisontal,level,setarti);
        }else{
          Gen(pos+1,word,rack,newarc,koordinat,horisontal,level,setarti);
        }
      }
    }
    return returnVal;
  }

  public boolean cekJalur(int pos,int y,int x,String word,AnchorSquare koordinat,boolean horisontal,int level){
    boolean hasil = false;
    if( (x+1) < 15 && (y+1) < 15 && (x-1) >= 0 && (y-1) >= 0 && x>=0 && y>=0 && (x-word.length()) >= 0 && (y-word.length()) >= 0 ){
      if(pos <= 0){
        if(horisontal){
          if(arrBoard[koordinat.yParent][koordinat.xParent+1].getValue().equals("")){//JIKA DIBELAKANG KATA ADA HURUF
            if(arrBoard[y][x-1].getValue().equals("")){//JIKA DIDEPAN KATA ADA HURUF
              record = record + word + ",";
              int bonus = getBonusValue(pos,word,koordinat,horisontal,level);
              RecordWord tmpRecord = new RecordWord(koordinat,word,bonus);
              recordWord.add(tmpRecord);
              System.out.println("PAPAN:"+arrBoard[y][x-1].getValue()+"-"+word + " Memiliki Arti " + tmpRecord);
            }else{
              //JIKA ADA HURUF DI DEPAN KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
              System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y][x-1].getValue()+" di "+y+","+(x-1));
            }
          }else{
            //JIKA ADA HURUF DI BELAKANG KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
            System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[koordinat.yParent][koordinat.xParent+1].getValue()+" di "+koordinat.yParent+","+koordinat.xParent);
          }
        }else{
          if(arrBoard[koordinat.yParent+1][koordinat.xParent].getValue().equals("")){//JIKA DIBELAKANG KATA ADA HURUF
            if(arrBoard[y-1][x].getValue().equals("")){//JIKA DIDEPAN KATA ADA HURUF
              record = record + word + ",";
              int bonus = getBonusValue(pos,word,koordinat,horisontal,level);
              RecordWord tmpRecord = new RecordWord(koordinat,word,bonus);
              recordWord.add(tmpRecord);
              System.out.println("PAPAN:"+arrBoard[y-1][x].getValue()+"-"+word + " Memiliki Arti " + tmpRecord);
            }else{
              //JIKA ADA HURUF DI DEPAN KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
              System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y-1][x].getValue()+" di "+(y-1)+","+x);
            }
          }else{
            //JIKA ADA HURUF DI BELAKANG KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
            System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[koordinat.yParent+1][koordinat.xParent].getValue()+" di "+(koordinat.yParent+1)+","+koordinat.xParent);
          }
        }
      }else{
        if(horisontal){
          if(arrBoard[y][x-word.length()].getValue().equals("")){//JIKA DIBELAKANG KATA ADA HURUF
            if(arrBoard[y][x+1].getValue().equals("")){
              record = record + word + ",";
              int bonus = getBonusValue(pos,word,koordinat,horisontal,level);
              RecordWord tmpRecord = new RecordWord(koordinat,word,bonus);
              recordWord.add(tmpRecord);
              System.out.println(word + " Memiliki Arti " + tmpRecord);
              hasil = true;
            }else{
              //JIKA ADA HURUF DI DEPAN KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
              System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y][x+1].getValue()+" di "+y+","+(x+1));
              hasil = false;
            }
          }else{
            //JIKA ADA HURUF DI BELAKANG KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
            System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y][x-word.length()].getValue()+" di "+koordinat.yParent+","+(koordinat.xParent-word.length()));
            hasil = false;
          }
        }else{
          if((y-word.length()) > 0){
            if(arrBoard[y-word.length()][x].getValue().equals("")){//JIKA DIBELAKANG KATA ADA HURUF
              if(arrBoard[y+1][x].getValue().equals("")){
                record = record + word + ",";
                int bonus = getBonusValue(pos,word,koordinat,horisontal,level);
                RecordWord tmpRecord = new RecordWord(koordinat,word,bonus);
                recordWord.add(tmpRecord);
                System.out.println(word + " Memiliki Arti " + tmpRecord);
                hasil = true;
              }else{
                //JIKA ADA HURUF DI DEPAN KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
                System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y+1][x].getValue()+" di "+(y+1)+","+x);
                hasil = false;
              }
            }else{
              //JIKA ADA HURUF DI BELAKANG KATA YANG TIDAK MENJADI KATA SAAT DIKASI HURUF TERTENTU
              System.out.println(word + " Memiliki Arti, Tidak Valid ada huruf "+arrBoard[y-word.length()][x].getValue()+" di "+(koordinat.yParent-word.length())+","+koordinat.xParent);
              hasil = false;
            }
          }
        }
      }
    }
    return hasil;
  }

  public boolean cekJalurPerHuruf(String rack,String word,AnchorSquare koordinat,boolean horisontal,int level){
    System.out.println("=cekJalurPerHuruf= word:" + word);
    int y = koordinat.y;
    int x = koordinat.x;
    boolean hasil = false;
    int pos = 0;

    for (int i=0;i<word.length() ;i++ ) {
      if(word.charAt(i) == '>'){
        pos = 1;
      }else{
        if((koordinat.yParent < koordinat.y && pos == 0) || (koordinat.xParent < koordinat.x && pos == 0)){
          pos = -2;
        }
        if(horisontal){
          // if(koordinat.xParent<koordinat.x && pos <= 0){
          //   pos = 1;
          // }
          if(pos == 1){
            x = koordinat.xParent + 1;
            pos++;
          }else if(pos > 1){
            x = koordinat.xParent + pos;
            pos++;
          }else{
            x = koordinat.x + pos;
            pos--;
          }
          if(arrBoard[y+1][x].getValue().equals("") && arrBoard[y-1][x].getValue().equals("")){
            hasil = true;
            System.out.println("Papan "+(y+1)+","+x+" dan "+(y-1)+","+x+" kosong maka hasil " + hasil);
          }else{
            hasil = false;

            System.out.println("Papan "+(y+1)+","+x+" dan "+(y-1)+","+x+" ada huruf "+arrBoard[y+1][x].getValue()+","+arrBoard[y-1][x].getValue()+" maka hasil " + hasil);

            if(!arrBoard[y+1][x].getValue().equals("")){

              char tmpChar = word.charAt(i);

              NodeTrie[] tmpNode = trie.getRoot().getChildWhere(tmpChar);

              AnchorSquare koordinatBaru = new AnchorSquare((y+1),x,y,x,horisontal);

              System.out.println(koordinatBaru);

              if(tmpNode[0] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y+1][x].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y+1][x].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y+1][x].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

              if(tmpNode[1] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y+1][x].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y+1][x].getValue().charAt(0),"","",newarc[0],tmpNode[1],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y+1][x].getValue().charAt(0),"","",newarc[1],tmpNode[1],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

            }

            if(!arrBoard[y-1][x].getValue().equals("")){

              char tmpChar = word.charAt(i);

              NodeTrie[] tmpNode = trie.getRoot().getChildWhere(tmpChar);

              AnchorSquare koordinatBaru = new AnchorSquare((y-1),x,y,x,horisontal);

              System.out.println(koordinatBaru);

              if(tmpNode[0] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y-1][x].getValue().charAt(0));

                boolean val1 = GoOn(-1,arrBoard[y-1][x].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(-1,arrBoard[y-1][x].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

              if(tmpNode[1] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y-1][x].getValue().charAt(0));

                boolean val1 = GoOn(-1,arrBoard[y-1][x].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(-1,arrBoard[y-1][x].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

            }
            i=100;
          }
        }else{
          // if(koordinat.yParent<koordinat.y && pos <= 0){
          //   pos = 1;
          // }
          if(pos == 1){
            y = koordinat.yParent + 1;
            pos++;
          }else if(pos > 1){
            y = koordinat.yParent + pos;
            pos++;
          }else{
            y = koordinat.y + pos;
            pos--;
          }
          if(arrBoard[y][x+1].getValue().equals("") && arrBoard[y][x-1].getValue().equals("")){
            hasil = true;
            System.out.println("Papan "+y+","+(x+1)+" dan "+y+","+(x-1)+" kosong maka hasil " + hasil);
          }else{
            hasil = false;
            boolean val = false;
            System.out.println("Papan "+y+","+(x+1)+" dan "+y+","+(x-1)+" ada huruf "+arrBoard[y][x+1].getValue()+","+arrBoard[y][x-1].getValue()+" maka hasil " + hasil);

            if(!arrBoard[y][x+1].getValue().equals("")){

              char tmpChar = word.charAt(i);

              NodeTrie[] tmpNode = trie.getRoot().getChildWhere(tmpChar);

              AnchorSquare koordinatBaru = new AnchorSquare(y,(x+1),y,x,horisontal);

              System.out.println(koordinatBaru);

              if(tmpNode[0] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y][x+1].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y][x+1].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y][x+1].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

              if(tmpNode[1] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y][x+1].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y][x+1].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y][x+1].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }
            }

            if(!arrBoard[y][x-1].getValue().equals("")){

              char tmpChar = word.charAt(i);

              NodeTrie[] tmpNode = trie.getRoot().getChildWhere(tmpChar);

              AnchorSquare koordinatBaru = new AnchorSquare(y,(x-1),y,x,horisontal);

              System.out.println(koordinatBaru);

              if(tmpNode[0] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y][x-1].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y][x-1].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y][x-1].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

              if(tmpNode[1] != null){
                NodeTrie[] newarc = tmpNode[0].getChildWhere(arrBoard[y][x-1].getValue().charAt(0));

                boolean val1 = GoOn(1,arrBoard[y][x-1].getValue().charAt(0),"","",newarc[0],tmpNode[0],koordinat,horisontal,level,true);
                boolean val2 = GoOn(1,arrBoard[y][x-1].getValue().charAt(0),"","",newarc[1],tmpNode[0],koordinat,horisontal,level,true);

                if(val1 || val2)
                  hasil = true;
              }

            }

            i = 100;
          }
        }
      }
    }

    System.out.println("=EndcekJalurPerHuruf=" + hasil);
    return hasil;
  }

  public int getBonusValue(int pos,String word,AnchorSquare koordinat,boolean horisontal,int level){
    System.out.println("=getBonusValue=" + word);
    int y = koordinat.y;
    int x = koordinat.x;
    int dw = 0;int tw = 0;
    int value = 0;int tmpValue = 0;
    String tmp = "";
    pos = 0;

    Peraturan peraturan = new Peraturan();

    for (int i=0;i<word.length() ;i++ ) {
      if(word.charAt(i) == '>'){
        pos = 1;
      }else{
        if((koordinat.yParent < koordinat.y && pos == 0) || (koordinat.xParent < koordinat.x && pos == 0)){
          pos = -2;
        }
        if(horisontal){
          // if(koordinat.xParent<koordinat.x && pos <= 0){
          //   pos = 1;
          // }
          if(pos == 1){
            x = koordinat.xParent + 1;
            pos--;
          }else if(pos > 1){
            x = koordinat.xParent + pos;
            pos++;
          }else{
            x = koordinat.x + pos;
            pos++;
          }
        }else{
          // if(koordinat.yParent<koordinat.y && pos <= 0){
          //   pos = 1;
          // }
          if(pos == 1){
            y = koordinat.yParent + 1;
            pos++;
          }else if(pos > 1){
            y = koordinat.yParent + pos;
            pos++;
          }else{
            y = koordinat.y + pos;
            pos--;
          }
        }

        tmpValue = peraturan.getValue(word.charAt(i));

        if(arrBoard[y][x].getBonus().equals("DL")){
          tmpValue = tmpValue * 2;
        }else if(arrBoard[y][x].getBonus().equals("TL")){
          tmpValue = tmpValue * 3;
        }else if(arrBoard[y][x].getBonus().equals("DW")){
          dw++;
        }else if(arrBoard[y][x].getBonus().equals("TW")){
          tw++;
        }

        System.out.println("PAPAN : "+y+","+x+"=>"+word.charAt(i) + ":" + tmpValue + " BONUS : " + arrBoard[y][x].getBonus());

        value = value + tmpValue;
      }
    }

    y = koordinat.yParent;
    x = koordinat.xParent;

    tmpValue = peraturan.getValue(arrBoard[y][x].getValue().charAt(0));

    if(arrBoard[y][x].getBonus().equals("DL")){
      tmpValue = tmpValue * 2;
    }else if(arrBoard[y][x].getBonus().equals("TL")){
      tmpValue = tmpValue * 3;
    }else if(arrBoard[y][x].getBonus().equals("DW")){
      dw++;
    }else if(arrBoard[y][x].getBonus().equals("TW")){
      tw++;
    }

    System.out.println("PAPAN : "+y+","+x+"=>"+arrBoard[y][x].getValue().charAt(0) + ":" + tmpValue);

    value = value + tmpValue;

    if(dw!=0)
      value = value * 2;

    if(tw!=0)
      value = value * 3;

    System.out.println("=END OF getBonusValue=");

    return value;
  }

  public LinkedList<RecordWord> sortingRecord(){
    LinkedList<RecordWord> tmpRecordWord = recordWord;
    RecordWord tmpRecord = null;
    boolean loop = true;

    if(!tmpRecordWord.isEmpty()){
      int batas = tmpRecordWord.indexOf(tmpRecordWord.getLast());
      batas--;

      while(loop){
        loop = false;
        for (int i=0;i<=batas;i++ ) {
          if(tmpRecordWord.get(i).bonus < tmpRecordWord.get(i+1).bonus){
            tmpRecord = tmpRecordWord.get(i+1);
            tmpRecordWord.set(i+1,tmpRecordWord.get(i));
            tmpRecordWord.set(i,tmpRecord);
            loop = true;
          }
        }
      }
    }
    return tmpRecordWord;
  }


}
// c > a r e
// a c > r e
// r a c > e
// e r a c >
// a>bler
// ba>ler
// lba>er
// elba>r
// relba>
