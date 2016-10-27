package logic;
class LinkedList{
	private Node head;

	public LinkedList(){
		head = null;
	}

	public Node getHead(){
		return head;
	}

	public Node addLast(NodeTrie n){
		Node newNode = new Node(n);
		if(head==null){
			head = newNode;
		}else{
			Node tmp = head ;
			while(tmp.getNext()!=null){
				tmp = tmp.getNext();
			}
			tmp.setNext(newNode);
		}
		return newNode;
	}
	public NodeTrie[] getChildWhere(char h){
		NodeTrie[] hasil = new NodeTrie[2];
		hasil[0] = null;
		hasil[1] = null;
		boolean found = false;

		if(head!=null){
			Node tmp = head ;
			while(tmp!=null){
					if(tmp.getNodeTrie().getHuruf() == h){
						hasil[0] = tmp.getNodeTrie();
						found = true;
						tmp = null;//Break
					}else{
						tmp = tmp.getNext();
					}
			}

			//if(!found){
			tmp = head ;
			while(tmp!=null){
				//System.out.println("getChildWhere:"+tmp.getNodeTrie().getHuruf() +"=="+h);
				if(tmp.getNodeTrie().getHuruf() == '>'){
					hasil = tmp.getNodeTrie().getChild().getChildWhere(h);
					if(hasil!=null){
						hasil[1] = tmp.getNodeTrie();
						System.out.print("FOUND > ");
					}else{

					}

					tmp = null;//Break
				}else{
					tmp = tmp.getNext();
				}
			}
			//}
		}
		return hasil;

	}
}
