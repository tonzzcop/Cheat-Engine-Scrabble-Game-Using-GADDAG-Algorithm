package logic;
public class TestDrive{
	public static void main(String args[]){
		// DATA STRUKTUR
		Trie trie = new Trie();
		// trie.add('A',false,1);
		// trie.add('B',false,1);
		// trie.add('C',false,1);
		// trie.add('H',false,1);
		// trie.getChild('E',trie.getRoot());
		// trie.addWord("ARE");
		// System.out.println("====");
		// trie.addWord("AREN");
		// END OF DATA STRUKTUR

		// KAMUS TO TRIE
		BacaFile bacaFile = new BacaFile("Kamus.txt");
		trie = bacaFile.bacaFile(trie);

		// GADDAG
		// System.out.println("Pola Gaddag");
		// GADDAG gaddag = new GADDAG();
		// String[] arrStr = gaddag.generate("CARE");
		// for (int i=0;i<arrStr.length;i++ ) {
		// 	System.out.println(arrStr[i]);
		// }
		// END OF GADDAG

		// System.out.println("Mencari sebuah kata :");
		// trie.searchWord("Z>ARIS");
		// trie.searchWord("Z>ARISS");
		// trie.searchWord("Z>A");
		// System.out.println("===");
		// NodeTrie tmpRoot = trie.getRoot();
		// tmpRoot = tmpRoot.getChildWhere('A');
		// System.out.println(tmpRoot);
		// tmpRoot = tmpRoot.getChildWhere('C');
		// System.out.println(tmpRoot);
		// tmpRoot = tmpRoot.getChildWhere('R');
		// System.out.println(tmpRoot);
		// tmpRoot = tmpRoot.getChildWhere('E');
		// System.out.println(tmpRoot);

	}

}
