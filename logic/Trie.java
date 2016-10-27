package logic;
public class Trie{
	private NodeTrie root;

	public Trie(){
		root = new NodeTrie('*',false,0);
	}

	public void add(char h,boolean a,int t){
		root.addChild(h,a,t);
	}

	public void addWord(String newWord){
		NodeTrie tmpRoot = root;
		boolean isEOW = false;
		for (int i=0;i<newWord.length() ;i++ ) {
			NodeTrie returnNodeTrie = getChild(newWord.charAt(i),tmpRoot);
			if(returnNodeTrie==null){
				System.out.println("Tambah Kaki "+newWord.charAt(i)+" di "+tmpRoot+", turun ke tingkat berikutnya ");
				if(i == (newWord.length()-1) || (newWord.charAt(i+1) == '>' && (newWord.length()-2) == i))
					isEOW = true;
				tmpRoot = tmpRoot.addChild(newWord.charAt(i),isEOW,i+1);
			}else{
				tmpRoot = returnNodeTrie;
				System.out.println(newWord.charAt(i)+" Ditemukan di "+tmpRoot+", turun ke tingkat berikutnya ");
			}
		}
	}

	public NodeTrie getChild(char h,NodeTrie tmpRoot){
		NodeTrie resultSearch = searchChild(tmpRoot,h);
		if(resultSearch==null){
			System.out.println(h+" Item Tidak Ditemukan.");
		}

		return resultSearch;
	}

	public NodeTrie searchChild(NodeTrie r,char h){
		LinkedList tmpList = r.getChild();
		NodeTrie finItem = null;

		if(tmpList!=null){
			Node tmpHead = tmpList.getHead();
			while(tmpHead!=null){
				NodeTrie tmpNodeTrie = tmpHead.getNodeTrie();
				if(tmpNodeTrie.getHuruf() == h){
					//System.out.println(tmpNodeTrie.toString());
					finItem = tmpNodeTrie;
				}
				tmpHead = tmpHead.getNext();
			}
		}

		return finItem;
	}

	public String searchWord(String word){
		NodeTrie tmpRoot = root;
		String result = searchingWord(word,tmpRoot,1,"");

		if(result.equals(""))
			System.out.println(word + " Tidak Ditemukan");
		else
			System.out.println(result + " Ditemukan ");

		return result;
	}

	public String searchingWord(String word,NodeTrie tmpRoot,int pos,String result){
		NodeTrie finItem = null;

		finItem = searchChild(tmpRoot,word.charAt(pos-1));
		if(finItem != null && pos <= word.length()){
			result = result + word.charAt(pos-1);
			//System.out.println(tmpRoot + result);
			pos++;
			tmpRoot = finItem;
			if(pos <= word.length())
				result = searchingWord(word,tmpRoot,pos,result);
		}else{
			result = "";
			pos = 99;//Keluar dari recursive
		}

		return result;
	}

	public NodeTrie getRoot(){
		return root;
	}

}
