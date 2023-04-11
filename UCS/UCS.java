import java.util.Scanner;
import java.util.ArrayList;

class UCS{

	//Global metablhtes gia na xrhimopoioyme mesa stis sunarthseis
	public static ArrayList<Node> field = new ArrayList<Node>();	//Pedio anazzhthshs
	public static ArrayList<String> visited = new ArrayList<String>();	//Komboi pou exoume episkeutei
	public static ArrayList<Node> path = new ArrayList<Node>();	//Monopati
	public static int n_elements;	//Ta N stoixeia

	//H sunartish auth blepei an mia sumboloseira einai egkyrh telikh katastash
	public static boolean is_final(String word){
		int m=n_elements;	//thetoume ton arithmo twn M iso me to N
		for(int i=0;i<word.length();i++){
			//Oso briskoume M meiwnoume ton metrith m
			if(word.charAt(i)=='M') m--;
			//An broume A kai den exoun eksantlithei ta M auto shmainei oti brikame A prin teleiwsoun ta M 
			if(word.charAt(i)=='A' && m!=0) return false;
		}
		//Elegxoume an to teleutaio gramma ths sumboloseiras einai A
		if(word.charAt(word.length()-1)!='A') return false;
		return true;
	}

	//Olh h leitourgia ginetai edw
	public static void start(String word){
		int finalcost=0;
		Node currentNode = new Node(word,null,0);	//Thetoume ton arxiko kombo me ton patera tou na dixnei se NULL
		Node min_node = new Node(word,null,-1);		//Prosorinos kombos gia sugkrish metaksy twn lysewn
		field.add(currentNode);		//Prosthetoume ton arxiko kombo
		while(field.size()!=0){		//An to metopo ths anazhthshs einai keno stamatame
			currentNode=get_min();	//Briskoume apo to metopo anazhthshs thn katastash me to mikrotero kostos
			field.remove(currentNode);	//Bgazoume apo to metopo anazhthshs thn katastash me to mikrotero kostos
			if(visited.indexOf(currentNode.get_word())!=-1) continue;	//Elegxoume an exoume episkeutei ksana ton kombo
			if(is_final(currentNode.get_word())){	//Elegxoume an einai telikh katastash kai kratame auth me to mikrotero kostos
				if(min_node.get_k()==-1){	//Gia arxikopoihsh
					min_node=currentNode;
				}
				else{
					if(currentNode.get_k()<=min_node.get_k()) min_node=currentNode;
				}
			}
			search_field(currentNode); //Enhmerwnoume to pedio anazhthsh gia ton kainourgio kombo
			visited.add(currentNode.get_word());	//Episkeuthkame ton kombo opote ton bazoume sthn lista me tou kombous pou episkeutikame
		}
		// Briskoume to monopati mesw twn gonewn tou telikou kombou
		currentNode=min_node;	
		while(currentNode.get_parent()!=null){
			path.add(currentNode);
			currentNode=currentNode.get_parent();
		}
		path.add(currentNode);

		//Ektupwsh
		System.out.println("Minimum cost="+min_node.get_k());
		for(int i=path.size()-1;i>=0;i--){
			System.out.print(path.get(i).get_word()+"     ");
		}
	}

	// Anazhthsh kai enhmerwsh tou tou pediou anazhthshs
	public static void search_field(Node currentNode){
		StringBuilder word = new StringBuilder(currentNode.get_word());
		int paula=0;
		char temp;
		int pos_dist;
		//Briskoume thn thesh ths paulas sthn sumboloseira mas
		for(int i=0;i<word.length();i++){
			if(word.charAt(i)=='-'){
				paula=i;
				break;
			}
		}
		// Kanoume swap kathe xaraktira me thn paula kai thn bazzoume sto pedio anazhthshs an kalhptei tiw proupotheseis
		for(int i=0;i<word.length();i++){
			word = new StringBuilder(currentNode.get_word());
			if(word.charAt(i)!='-'){
				temp=word.charAt(i);
				word.setCharAt(i, '-');
				word.setCharAt(paula, temp);
				pos_dist=Math.abs(i-paula);

				// Elegxos an k<=N
				if(pos_dist<=n_elements){
					String newword = word.toString();
					Node newnode = new Node(newword,currentNode,currentNode.get_k()+pos_dist);
					field.add(newnode);
				}
			}
		}
	}

	//Briskoume apo olous tous kombous tou pediou anazhthshs auton me to mikrotero kostos
	public static Node get_min(){
		// Arxikopoihsh
		int min_value=field.get(0).get_k();
		Node min_node=field.get(0);
		//Elegxoume kathe kombo to pedio anazhthshs
		for(int i=0;i<field.size();i++){
			if(field.get(i).get_k()<min_value){
				min_value=field.get(i).get_k();
				min_node=field.get(i);
			}
		}
		return min_node;
	}

	//Elegxoume an to input tou xrhsth einai egkuro
	public static boolean is_valid(String word){
		int m=0;
		int a=0;
		int paula=0;
		//Metrame ta M,A,-
		for(int i=0;i<word.length();i++){
			if(word.charAt(i)=='M') m++;
			if(word.charAt(i)=='A') a++;
			if(word.charAt(i)=='-') paula++;
		}
		if(m!=a || paula!=1) return false;
		n_elements=m;	//Thetoume san N ton arithmo twn M (h kai ton A tha mporouse)
		return true;
	}

	public static void main(String[] args) {
		Scanner user_input = new Scanner(System.in);
		System.out.print("Give your word: ");
		String word = user_input.nextLine();  //Eisodos xrhsth
		if(is_valid(word)) start(word);
		else System.out.println("You gave invalid input!");
	}
}