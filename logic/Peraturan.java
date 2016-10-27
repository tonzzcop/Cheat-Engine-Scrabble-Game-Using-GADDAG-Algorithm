package logic;
public class Peraturan{
  public int[] arrJumlahHuruf;
  public int[] jumlahHuruf;

  public Peraturan(){
    arrJumlahHuruf = new int[26];
    arrJumlahHuruf[0] = 9;//A
    arrJumlahHuruf[1] = 2;//B
    arrJumlahHuruf[2] = 2;//C
    arrJumlahHuruf[3] = 4;//D
    arrJumlahHuruf[4] = 12;//E
    arrJumlahHuruf[5] = 2;//F
    arrJumlahHuruf[6] = 3;//G
    arrJumlahHuruf[7] = 2;//H
    arrJumlahHuruf[8] = 9;//I
    arrJumlahHuruf[9] = 1;//J
    arrJumlahHuruf[10] = 1;//K
    arrJumlahHuruf[11] = 4;//L
    arrJumlahHuruf[12] = 2;//M
    arrJumlahHuruf[13] = 6;//N
    arrJumlahHuruf[14] = 8;//O
    arrJumlahHuruf[15] = 2;//P
    arrJumlahHuruf[16] = 1;//Q
    arrJumlahHuruf[17] = 6;//R
    arrJumlahHuruf[18] = 4;//S
    arrJumlahHuruf[19] = 6;//T
    arrJumlahHuruf[20] = 4;//U
    arrJumlahHuruf[21] = 2;//V
    arrJumlahHuruf[22] = 2;//W
    arrJumlahHuruf[23] = 1;//X
    arrJumlahHuruf[24] = 2;//Y
    arrJumlahHuruf[25] = 1;//Z
    jumlahHuruf = arrJumlahHuruf.clone();
  }

  public void cetakJumlahHuruf(){
    System.out.println("======");
    System.out.println("A : " + jumlahHuruf[0]);
    System.out.println("B : " + jumlahHuruf[1]);
    System.out.println("C : " + jumlahHuruf[2]);
    System.out.println("D : " + jumlahHuruf[3]);
    System.out.println("E : " + jumlahHuruf[4]);
    System.out.println("F : " + jumlahHuruf[5]);
    System.out.println("G : " + jumlahHuruf[6]);
    System.out.println("H : " + jumlahHuruf[7]);
    System.out.println("I : " + jumlahHuruf[8]);
    System.out.println("J : " + jumlahHuruf[9]);
    System.out.println("K : " + jumlahHuruf[10]);
    System.out.println("L : " + jumlahHuruf[11]);
    System.out.println("M : " + jumlahHuruf[12]);
    System.out.println("N : " + jumlahHuruf[13]);
    System.out.println("O : " + jumlahHuruf[14]);
    System.out.println("P : " + jumlahHuruf[15]);
    System.out.println("Q : " + jumlahHuruf[16]);
    System.out.println("R : " + jumlahHuruf[17]);
    System.out.println("S : " + jumlahHuruf[18]);
    System.out.println("T : " + jumlahHuruf[19]);
    System.out.println("U : " + jumlahHuruf[20]);
    System.out.println("V : " + jumlahHuruf[21]);
    System.out.println("W : " + jumlahHuruf[22]);
    System.out.println("X : " + jumlahHuruf[23]);
    System.out.println("Y : " + jumlahHuruf[24]);
    System.out.println("Z : " + jumlahHuruf[25]);

  }

  public int getValue(char a){
    int value = 0;

    //1 Point - A, E, I, L, N, O, R, S, T and U.
    if( (a == 'A') || (a == 'E') || (a == 'I')  || (a == 'L')  || (a == 'N')
       || (a == 'O') || (a == 'R') || (a == 'S') || (a == 'T') || (a == 'U')
    ){
      value = 1;
    }else if( (a == 'D') || (a == 'G') ){//2 Points - D and G.
      value = 2;
    }else if( (a == 'B') || (a == 'C') || (a == 'M') || (a == 'P') ){//3 Points - B, C, M and P.
      value = 3;
    }else if( (a == 'F') || (a == 'H') || (a == 'V') || (a == 'W') || (a == 'Y') ){//4 Points - F, H, V, W and Y.
      value = 4;
    }else if( a == 'K' ){//5 Points - K.
      value = 5;
    }else if( (a == 'J') || (a == 'X') ){//8 Points - J and X.
      value = 8;
    }else if( (a == 'Q') || (a == 'Z') ){//10 Points - Q and Z.
      value = 10;
    }else{
      value = 0;
    }

    return value;
  }
}
