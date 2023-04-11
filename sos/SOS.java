import java.util.ArrayList;
import java.util.Scanner;

public class SOS{
	public static ArrayList<int[][]> biggrids = new ArrayList<int[][]>();	//Global arraylist gia na kratame tous teleutaious pinakes
	public static ArrayList<Integer> bigscore = new ArrayList<Integer>();	//Global arraylist gia na kratame ta teleutaia score

	public static int minimax(int[][] grid,int[][] locked,int depth)
	{
		ArrayList<int[][]> grids = new ArrayList<int[][]>();	//Kratame tous pinakes twn paidiwn
		ArrayList<Integer> score = new ArrayList<Integer>();	//Kratame ta score twn paidiwn

		int[][] newgrid=(int[][]) copy_matrix(grid);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(locked[i][j]==1) continue;	//Elegxoume an mporoume na peiraksoume thn thesh
				if(newgrid[i][j]==-1){
					newgrid[i][j]=1;
					grids.add((int[][]) copy_matrix(newgrid));	//Prosthetoume sto arraylist grids to kainourgio grid
					if(is_win(newgrid)){	//Elegxoume an o pinakas brisketai se nikithria katatash
						if(depth%2==0) score.add(1);	//An paizei o MAX
						else score.add(-1);	//An paizei o MIN
					}
					else{
						//An o pinakas einai gematos kai den einai nikithria katastah tote prosthetoume 0 gia isopalia
						if(is_filled(newgrid)) score.add(0);	
						//Alliws sunexizoume me to epomeno paidi
						else score.add(minimax(copy_matrix(newgrid),locked,depth+1));					
					}

					newgrid[i][j]=0;
					grids.add((int[][]) copy_matrix(newgrid));	//Prosthetoume sto arraylist grids to kainourgio grid
					if(is_win(newgrid)){	//Elegxoume an o pinakas brisketai se nikithria katatash
						if(depth%2==0) score.add(1);	//An paizei o MAX
						else score.add(-1);	//An paizei o MIN
					}
					else{
						//An o pinakas einai gematos kai den einai nikithria katastah tote prosthetoume 0 gia isopalia
						if(is_filled(newgrid)) score.add(0);
						//Alliws sunexizoume me to epomeno paidi
						else score.add(minimax(copy_matrix(newgrid),locked,depth+1));					
					}
					newgrid[i][j]=-1;	//Epanafora tou pinaka sthn proigoumenh katastash
				}
			}
		}
		biggrids=grids;	//Kratame se global ta paidia tou twrinou gonea (sto telos tha einai goneas h riza)  
		bigscore=score;	//Kratame se global ta score tou twrinou gonea (sto telos tha einai goneas h riza)
		if(depth%2==0) return get_max(score)[0];	//An paizei o MAX epestrepse to megalytero score apo ta paidia tou twrinou gonea
		else return get_min(score)[0];	//An paizei o MIN epestrepse ton megalytero score apo ta paidia tou twrinou gonea
	}

	//Kanoume deep copy gia na mhn exoume problhmata
	public static int[][] copy_matrix(int[][] grid){
		int[][] out_matrix=new int[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				out_matrix[i][j]=grid[i][j];
			}
		}
		return out_matrix;
	}

	//Typwnoume ton pinaka
	public static void printnew(int[][] grid){
		for(int i=0;i<3;i++){
			System.out.println();
			for(int j=0;j<3;j++){
				System.out.print(grid[i][j]);
			}
		}
		System.out.println();
	}

	//Pernoume thn megith timh apo ena arraylist kai epitrefei thn timh ayth kai thn thesh ths sto arraylist
	public static int[] get_max(ArrayList<Integer> counter_array){
		int[] value={-2,0};	//Arxikopoihsh
		for(int i=0;i<counter_array.size();i++){
			if(counter_array.get(i)>value[0]){
				value[0]=counter_array.get(i);
				value[1]=i;
			}			
		}
		return value;
	}

	//Pernoume thn mikroterh timh apo ena arraylist kai epitrefei thn timh ayth kai thn thesh ths sto arraylist
	public static int[] get_min(ArrayList<Integer> counter_array){
		int[] value={2,0};	//Arxikopoihsh

		for(int i=0;i<counter_array.size();i++){
			if(counter_array.get(i)<value[0]){
				value[0]=counter_array.get(i);
				value[1]=i;
			}			
		}
		return value;	
	}

	//Elegxoume an o pinakas brisketai se nikithria katastash
	public static boolean is_win(int[][] grid){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(grid[i][j]==0){	//Briskoume pou uparxei 0 (O) 
					if(j-1>=0 && j+1<3){	//Elegxoume an yparxei 1 (S) panw kai katw apo to 0 (O)
						if((grid[i][j-1]==1 && grid[i][j+1]==1)){
							return true;
						}
					}
					if(i-1>=0 && i+1<3){	//Elegxoume an yparxei 1 (S) aristera kai deksia apo to 0 (O)
						if((grid[i-1][j]==1 && grid[i+1][j]==1)){
							return true;
						}
					}
					//Elegxoume an yparxei 1 (S) diagwnia apo to 0 (O)
					if(i-1>=0 && j-1>=0 && i+1<3 && j+1<3){
						if((grid[i-1][j-1]==1 && grid[i+1][j+1]==1)){
							return true;
						}
					}
					if(i-1>=0 && j+1<3 && i+1<3 && j-1>=0){
						if((grid[i-1][j+1]==1 && grid[i+1][j-1]==1)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	//Elegxoume an o pinakas einai gematos
	public static boolean is_filled(int[][] grid){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(grid[i][j]==-1){
					return false;
				}
			}
		}
		return true;
	}

	//Dhmiourgoume enan pinaka me tis theseis pou DEN prepei na allaksoume
	public static int[][] locked_values(int[][] grid){
		int[][] locked={{0,0,0},
			  		   {0,0,0},
			  		   {0,0,0}};
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(grid[i][j]==-1){
					locked[i][j]=0;
				}
				else{
					locked[i][j]=1;
				}
			}
		}
		return locked;	
	}

	public static void main(String[] args) {
		// Arxikos pinakas (akoma kai an mpei to 0 sth thesh (1,0) leitourgei kanonika to programma)
		int[][] grid={{-1,-1,-1},
			  	  	  {-1,-1, 0},
				  	  {-1,-1,-1}};	

		int[][] newgrid=new int[3][3];	//Arxikopoihsh enos neou pinaka (tha enhmerwnetai pio katws)
		int[][] locked=locked_values(grid);	//Dhmiourgoume ton pipaka me tis theseis pou DEN mporoun na allaxtoun ta stoixeia
		Scanner user_i=new Scanner(System.in);
		newgrid=copy_matrix(grid);
		while(true){
			printnew(newgrid);
			if(is_win(newgrid)){	//Elegxoume an kerdise o MIN
				System.out.println("MIN Wins");
				return;
			}
			if(is_filled(newgrid)){	//Elegxoume an einai isopalia
				System.out.println("IS TIE");
				return;
			}	
			System.out.println("MAX turn!");
			locked=locked_values(newgrid);
			minimax(newgrid,locked,0);	//Paizei o MAX
			newgrid=biggrids.get(get_max(bigscore)[1]);	//Pernoume to grid me to kalytero score
			printnew(newgrid);
			if(is_win(newgrid)){	//Elegxoume an kerdise o MAX
				System.out.println("MAX wins");
				return;
			}
			if(is_filled(newgrid)){	//Elegxoume an einai isopalia
				System.out.println("IS TIE");
				return;
			}
			locked=locked_values(newgrid);	//Enhmerwnoume ton pinaka locked
			System.out.println("MIN turn!");
			while(true){	//Paizei o MIN (ginetai elegxos an oi times einai egkures)
				System.out.println("Type coordinate x (row 0-2)");
				int x = user_i.nextInt();
				System.out.println("Type coordinate y (column 0-2)");
				int y = user_i.nextInt();
				System.out.println("Type choice 0 (O) or 1 (S)");
				int choice = user_i.nextInt();
				if(choice!=0 && choice!=1){	//Elegxos choise tou xrhsth
					System.out.println("Wrong choice");
					continue;
				}
				if(locked[x][y]==0){
					newgrid[x][y]=choice;
					break;
				}
				System.out.println("Wrong values try again");
			}
		}
	}
}