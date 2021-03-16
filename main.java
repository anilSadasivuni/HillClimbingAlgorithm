package isProject2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main {

	static int size=0;
	static int run=0;
	static int type=0;
	static int succHeu=0;
	static int sideWayCount=0;
	static int[][] heuArray;
	static int[][] stateArray;
	static int currheu=0;
	static int rowMan=0;
	static int succRow=0;
	static int succCol=0;
	static int numStepsTook=0;
	static int numStepsPass=0;
	static int numStepsFail=0;
	static int numPass=0;
	static int numFail=0;
	static int passPerc=0;
	static int failPerc=0;
	static int numOfTimesRan=1;
	static int row;
	static int iterStop=0;
	static int num1=0;
	static int num2=0;
	static int num3=0;
	static int sizeCheck=0;
	static int left=0;
	static int right=0;
	static int loopCheck=1;
	static ArrayList randomPick=new ArrayList();
	static Random rand1 = new Random();
	static Random rand2 = new Random();
	
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
		
		//Ask User for the Input
		//Enter Number of Queens
		System.out.println("Enter the number of queens");
		Scanner in=new Scanner(System.in);
		size = in.nextInt();
		
		//Initialize Heuristic array and State array
		heuArray=new int[size][size];
		stateArray=new int[size][size];
		
		//Enter the type of Hill Climbing
		System.out.println("Enter the number for Type of Hill Climbing");
		System.out.println("1. ascent\n2. side-ways\n3. Random Restart without sideways move\n4. Random Restart with sideways move ");
		type= in.nextInt();
		
		//Enter the number of times the problem should execute to find average
		System.out.println("Enter the number of times the problem should execute:");
		run = in.nextInt();
		
		//Select the function based on type of hill climbing
		if( type == 1 || type == 2 )
		{
			conditionOneorTwo();
		}
		else
		{
			randomRestart();
		}
	}
	
	//If hill climbing is either ascent hill climbing or side way moves hill climbing then execute this function
	public static void conditionOneorTwo()
	{
		//Number of the times the problem should execute to get the average number of steps, pass and failure percentage 
		while(numOfTimesRan<run+1)
		{	 
			System.out.println("\n --------------------------------------------------- \n");
			//Initialize variables before each iteration
			 num1=0;
			 num2=0;
			 num3=0;
			 sizeCheck=0;
			 left=0;
			 right=0;
			 loopCheck=1;
			 randomPick.clear();
			 succHeu=0;
			 sideWayCount=0;
			 currheu=0;
			 rowMan=0;
			 succRow=0;
			 succCol=0;
			 numStepsTook=0;
			 
			 //call the function to generate initial state which initiate further sequence of actions
			 initialstate();
			 
			 //call the function to initialize all values of state array and heuristic array to zero before each iteration
			 initialize();
			 numOfTimesRan++;
			 System.out.println("\n --------------------------------------------------- \n");
		}
		//calculate average number of pass steps
		if(numPass!=0)
		{
			System.out.println("\nThe average number of steps when execution passed is "+(numStepsPass/numPass));
		}
		//calculate average number of failure steps
		if(numFail!=0)
		{
			System.out.println("\nThe average number of steps when execution failed is "+(numStepsFail/numFail));
		}
		//calculate pass percentage and fail percentage
		if(run!=0)
		{
			System.out.print("Pass Percentage is "+((int)(((float)numPass/run)*100)));
			System.out.print("\nFail Percentage is "+((int)(((float)numFail/run)*100)));
		}
	}
	//If hill climbing is random restart then execute this function
	public static void randomRestart()
	{
		int runLoop=1;
		numOfTimesRan=0;
		
		//number of times the problem has to be run to calculate average number of steps
		while(runLoop<run+1)
		{
			iterStop=0;
			System.out.println("\n Step "+(iterStop+1)+" \n");
			//Number of random restarts required
			while(iterStop!=1)
			{
				System.out.println("\n --------------------------------------------------- \n");
				 num1=0;
				 num2=0;
				 num3=0;
				 sizeCheck=0;
				 left=0;
				 right=0;
				 loopCheck=1;
				 randomPick.clear();
				 succHeu=0;
				 sideWayCount=0;
				 currheu=0;
				 rowMan=0;
				 succRow=0;
				 succCol=0;
				 numStepsTook=0;
				 initialstate();
				 initialize();
				 numOfTimesRan++;
				 System.out.println("\n --------------------------------------------------- \n");
			}
			runLoop++;
			System.out.println("\n End of step \n");
		}
		runLoop--;
		
		//calculate average number of times restarts required and average number of overall steps required
		System.out.println("\n Average Number of restarts required to get success "+((int)(numOfTimesRan/runLoop)));
		System.out.println("\n Average number of steps required "+((int)((numStepsPass+numStepsFail)/runLoop)));
	}
	//Initialize all the positions of state array and heuristic array to zero before every iteration
	public static void initialize()
	{
		for(int count=0;count<size;count++)
		{
			for(int count1=0;count1<size;count1++)
			{
				heuArray[count][count1]=0;
				stateArray[count][count1]=0;
			}
		}
	}
	//Generate successors of current state based on minimum heuristic and type of hill climbing
	public static void genSucc()
	{
		int sideMovement=0;
		
		//Generate successor based on minimum heuristic without sideways movement
		if(type == 1 || type == 3)
		{
			while(heuristicCal()!=0)
			{
				//printConfig(stateArray);
				
				currheu = heuristicCal();
				calculateSucc();
				succHeu = findMinHeuArray();
				
				System.out.println("\n Current Heuristic is "+currheu);
				System.out.println("\n Successor Heuristic is "+succHeu);
				
				System.out.println("-----------------Successor-----------------");
				
				if(succHeu < currheu)
				{
					selectSucc();
				}
				else
				{
					break;
				}
			}
		}
		//Generate successor based on minimum heuristic with sideways movement
		else if(type == 2  || type == 4)
		{
			while(heuristicCal() !=0)
			{
				//printConfig(stateArray);
				
				currheu = heuristicCal();
				calculateSucc();
				succHeu = findMinHeuArray();
				
				System.out.println("\n Current Heuristic is "+currheu);
				System.out.println("\n Successor Heuristic is "+succHeu);
				
				System.out.println("-----------------Successor-----------------");
				
				if(loopCheck==0)
				{
					break;
				}
				else if(succHeu < currheu)
				{
					sideWayCount=0;
					selectSucc();
				}
				else if(succHeu == currheu && sideWayCount<100)
				{
					sideMovement++;
					selectSideWaySucc();
					sideWayCount++;
				}
				else
				{
					break;
				}
			}
		}
		//Decide if the final state is goal state or not and add number of steps accordingly to specific variable
		if(heuristicCal() == 0)
		{
			iterStop=1;
			numPass++;
			numStepsPass=numStepsPass+numStepsTook;
			System.out.println("\n Execution passed \n");
			System.out.println("\n Final state");
			printConfig(stateArray);
		}
		else
		{
			numFail++;
			numStepsFail=numStepsFail+numStepsTook;
			System.out.println("\n Execution failed \n");
			System.out.println("\n Final state");
			printConfig(stateArray);
		}
	}
	//select successor without sideways movement based on minimum heuristic
	public static void selectSucc()
	{
		numStepsTook++;
		int rowMov=0;
		while(rowMov<size)
		{
			if(stateArray[rowMov][succCol] == 1)
			{
				stateArray[rowMov][succCol] = 0;
				break;
			}
			rowMov++;
		}
		stateArray[succRow][succCol] = 1;
	}
	
	//select successor with sideways movement based on equal heuristic
	public static void selectSideWaySucc()
	{	
		numStepsTook++;
		randomPick.clear();
		loopCheck=0;
		
		//Add positions of the state with equal heuristic to the array list
		for(int count=0;count<size;count++)
		{
			for(int count1=0;count1<size;count1++)
			{
				if(heuArray[count1][count] == succHeu)
				{
					if(stateArray[count1][count] !=  1)
					{
						num1=(10*count1);
						num2=num1+count;
						loopCheck=1;
						randomPick.add(num2);
					}
				}
			}
		}
		 // select a random position to pick up for successor state if one exists
		if(loopCheck==1)
		{
			sizeCheck=rand1.nextInt(randomPick.size());
			left=((int)randomPick.get(sizeCheck))/10;
			right=((int)randomPick.get(sizeCheck))%10;
			for(int count=0;count<size;count++)
			{
				if(stateArray[count][right]==1)
				{
					stateArray[count][right]=0;
					break;
				}
			}
			
			stateArray[left][right]=1;
		}
	}
	//calculate minimum heuristic in the heuristic array
	public static int findMinHeuArray()
	{
		int minHeuristic= heuArray[0][0];
		succRow=0;
		succCol=0;
		for(int count=0;count<size;count++)
		{
			for(int count1=0;count1<size;count1++)
			{
				if(heuArray[count][count1] <= minHeuristic)
				{
					minHeuristic=heuArray[count][count1];
					succRow=count;
					succCol=count1;
				}
			}
		}
		return minHeuristic;
	}
	//calculate heuristics of possible successors and store it in a heuristic array
	public static void calculateSucc()
	{
		int prev=0;
		
		for(int count1=0;count1<size;count1++)
		{
			for(int count=0;count<size;count++)
			{
				if(stateArray[count][count1] == 1)
				{
					
					stateArray[count][count1] = 0;
					heuArray[count][count1] = currheu;
					
					rowMan=count;
					rowMan=rowMan-1;
					
					while(rowMan>=0)
					{
						prev = stateArray[rowMan][count1] ;
						stateArray[rowMan][count1] = 1;
						heuArray[rowMan][count1] = heuristicCal();
						stateArray[rowMan][count1] = prev;
						rowMan--;
					}
					rowMan=count;
					rowMan=count+1;
					while(rowMan<size)
					{
						prev = stateArray[rowMan][count1];
						stateArray[rowMan][count1] = 1;
						heuArray[rowMan][count1] = heuristicCal();
						stateArray[rowMan][count1] = prev;
						rowMan++;
					}
					stateArray[count][count1] = 1;
				}
			}
		}
	}
	//Generates initial configuration of the problem
	public static void initialstate()
	{
		//Generate the position of queens randomly
		for(int count1=0;count1<size;count1++)
		{
			row=rand2.nextInt(size);
			stateArray[row][count1] = 1;
		}
		
		//checks if initial state is the goal state otherwise call function for generating successors
		if( heuristicCal() == 0)
		{
			numPass++;
			System.out.println("\nInitial state is the Goal State");
		}
		else
		{
			genSucc();
		}
	}
	
	//Heuristic calculation for conflicting pairs
	public static int heuristicCal() 
	{
		int heu=0;
		int mov=0;
		int mov1=0;
		
		for(int count=0;count<size;count++)
		{
			for(int count1=0;count1<size;count1++)
			{
				if(stateArray[count][count1]==1)
				{
					mov=count1+1;
					//horizontal check
					while(mov < size)
					{
						if(stateArray[count][mov]==1)
						{
							heu++;
						}
						mov++;
					}
					//vertical check
					mov=count+1;
					while(mov < size)
					{
						if(stateArray[mov][count1]==1)
						{
							heu++;
						}
						mov++;
					}
					//right diagonal check 
					mov=count+1;
					mov1=count1+1;
					while(mov < size && mov1 < size)
					{
						if(stateArray[mov][mov1]==1)
						{
							heu++;
						}
						mov++;
						mov1++;
					}
					//left diagonal check 
					mov=count-1;
					mov1=count1+1;
					while(mov < size && mov>=0 && mov1 < size)
					{
						if(stateArray[mov][mov1]==1)
						{
							heu++;
						}
						mov--;
						mov1++;
					}	
				}
			}
		}
		return heu;
	}
	//prints configuration of the state when called
	public static void printConfig(int[][] board)
	{
		System.out.println("\n");
		for(int count=0;count<size;count++)
		{
			System.out.print("\n");
			for(int count1=0;count1<size;count1++) 
			{
				if(board[count][count1] == 1)
				{
					System.out.print("Q ");
				}
				else
				{
					System.out.print("- ");
				}
			}
		}
	}
	//prints heuristic Array when called
	public static void printHeu(int[][] board)
	{
		System.out.println("\n");
		for(int count=0;count<size;count++)
		{
			System.out.print("\n");
			for(int count1=0;count1<size;count1++) 
			{
					System.out.print(board[count][count1]+" ");
			}
		}
	}
}
