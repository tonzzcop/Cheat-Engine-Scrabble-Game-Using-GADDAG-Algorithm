package logic;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BacaFile{
  private String namaFile;

  public BacaFile(String n){
    namaFile = n;
  }

  public Trie bacaFile(Trie trie){
    try (BufferedReader br = new BufferedReader(new FileReader("Data/"+namaFile)))
		{

			String sCurrentLine;
      GADDAG gaddag = new GADDAG(trie);

			while ((sCurrentLine = br.readLine()) != null) {
        gaddag.generate(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
    return trie;
  }

}
