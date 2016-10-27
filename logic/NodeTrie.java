package logic;
public class NodeTrie{
	private char huruf;
	private boolean arti,prefix;
	private int tingkat;

	private LinkedList child;

	public NodeTrie(char h,boolean a,int t){
		huruf = h;
		arti = a;
		tingkat = t;
		child = new LinkedList();
	}

	public NodeTrie addChild(char h,boolean a,int t){
		NodeTrie newNodeTrie =  new NodeTrie(h,a,t);
		child.addLast(newNodeTrie);
		return newNodeTrie;
	}

	public LinkedList getChild(){
		return child;
	}

	public NodeTrie[] getChildWhere(char h){
		NodeTrie[] result = child.getChildWhere(h);
		return result;
	}

	public char getHuruf(){
		return huruf;
	}

	public boolean getArti(){
		return arti;
	}

	public void setPrefixTrue(){
		prefix = true;
	}

	public boolean getPrefix(){
		return prefix;
	}

	public String toString(){
		return "["+Integer.toHexString(hashCode())+","+huruf+","+arti+","+tingkat+"]";
	}
}
