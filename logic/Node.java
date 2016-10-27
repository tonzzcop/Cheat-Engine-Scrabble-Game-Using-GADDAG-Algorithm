package logic;
class Node{
	private NodeTrie nodeTrie;
	private Node next;

	public Node(NodeTrie n){
		nodeTrie = n;
		next = null;
	}

	public NodeTrie getNodeTrie(){
		return nodeTrie;
	}

	public Node getNext(){
		return next;
	}

	public void setNext(Node n){
		next = n;
	}

}
