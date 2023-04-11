class Node{
	String current_string;
	Node parent;
	int k;
	public Node(String current_string,Node parent,int k){
		this.current_string=current_string;
		this.parent=parent;
		this.k=k;
	}

	public String get_word(){
		return current_string;
	}

	public int get_k(){
		return k;
	}

	public Node get_parent(){
		return parent;
	}
}