
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kunwar
 */

/*
Node Class that contains the name, the cost and the name is also assigned a unique
ID that is represented by the name of 'node'. The Comparator class is also applied
that will compare the costs of all the paths, and will sort the routes from one
node to other in sorted order (increasing)
*/
class Node implements Comparator<Node>
{
    public int node;
    public int cost;
    public String name;
    public Node()
    {
 
    }
    
    public Node(String name){
        this.name = name;
    }
    
    public Node(int node, int cost)
    {
        this.node = node;
        this.cost = cost;
    }
 
    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        if (node1.node < node2.node)
            return -1;
        return 0;
    }
 
    public boolean equals(Object obj)
    {
        if (obj instanceof Node)
        {
            Node node = (Node) obj;
            if (this.node == node.node)
            {
                return true;
            }
        }
        return false;
    }
}

/*
    This class is called in the main method, and it contains the whole algorithm.
    The File is read, and all the parents are stored in the form of a matrix, and 
    then there children are being marked with the cost value. Rest of the values
    are assigned a value of 999, marking them as INFINITY. A boolean array, settled
    is made that will keep a check on the children that are already visited. A 
    priority queue is added that will contain the possible path from the source to 
    destination. It will also keep the record of the path in a LinkedList 'path'.
*/

class UniformCostSearch
{
    private PriorityQueue<Node> priorityQueue;
    private Set<Integer> settled;
    private int distances[];
    private int numberOfNodes;
    private int adjacencyMatrix[][];
    public LinkedList<Integer> path;
    private int[] parent;
    private int source, destination;
    public static final int MAX_VALUE = 999; 
 
    public UniformCostSearch(int numberOfNodes)
    {
        this.numberOfNodes = numberOfNodes;
        this.settled = new HashSet<Integer>();
        this.priorityQueue = new PriorityQueue<Node>(numberOfNodes, new Node());
        this.distances = new int[numberOfNodes + 1];
        this.path = new LinkedList<Integer>();
        this.adjacencyMatrix = new int[numberOfNodes + 1][numberOfNodes + 1]; 
        this.parent = new int[numberOfNodes + 1];
    }
 
    public int uniformCostSearch(int adjacencyMatrix[][], int source, int destination)
    {
        int evaluationNode;
        this.source = source;
        this.destination = destination;
 
        for (int i = 1; i <= numberOfNodes; i++)
        {
            distances[i] = MAX_VALUE;
        }
 
        for (int sourcevertex = 1; sourcevertex <= numberOfNodes; sourcevertex++)
        {
            for (int destinationvertex = 1; destinationvertex <= numberOfNodes; destinationvertex++)
            {
                this.adjacencyMatrix[sourcevertex][destinationvertex] =
                       adjacencyMatrix[sourcevertex][destinationvertex];
	    }
        }
 
        priorityQueue.add(new Node(source, 0));
        distances[source] = 0;
        long start = System.currentTimeMillis();
        int status=0;
        while (!priorityQueue.isEmpty())
        {
            evaluationNode = getNodeWithMinDistanceFromPriorityQueue();
            if (evaluationNode == destination)
            {
                status=1;
            } 
            long stop = System.currentTimeMillis();
                
            settled.add(evaluationNode);
            addFrontiersToQueue(evaluationNode);
        }
        
        if(status==1)
        return distances[destination];
        else
            return 999;
    }
 
    private void addFrontiersToQueue(int evaluationNode)
    {
        for (int destination = 1; destination <= numberOfNodes; destination++)
        {
            if (!settled.contains(destination))
            {
                if (adjacencyMatrix[evaluationNode][destination] != MAX_VALUE)
                {
                    if (distances[destination] > adjacencyMatrix[evaluationNode][destination]  
                                    + distances[evaluationNode])
                    {
                        distances[destination] = adjacencyMatrix[evaluationNode][destination]	
                                               + distances[evaluationNode]; 				 		
                        parent[destination] = evaluationNode;
                    }
 
                    Node node = new Node(destination, distances[destination]);
                    if (priorityQueue.contains(node))
                    {
                        priorityQueue.remove(node);
                    }
                    priorityQueue.add(node);
                }
            }
        }
    }
 
    private int getNodeWithMinDistanceFromPriorityQueue()
    {
        Node node = priorityQueue.remove();
        return node.node;
    }
 
    public void printPath()
    {
        path.add(destination);
        boolean found = false;
        int vertex = destination;
        while (!found)
        {
            if (vertex == source)
            {
                found = true;
                continue;
            }
            path.add(parent[vertex]);
            vertex = parent[vertex];
        }       
    }
}

/*
    This is the class which will be called, This class will read the file, and
    fill the matrix with the required values. Then it will call the class, by passing
    the matrix, and execute the functions, and print the required result.
*/

public class find_route
{
    private static String line;
    public static HashSet<String> hp ;
    public static ArrayList<String> copy;
    
    private static int getIndex(String name){
        int p=-1;
        if(copy.contains(name)){
            for(int i=0;i<copy.size();i++){
                if(copy.get(i).equals(name))
                    return i+1;
            }
        }
        return p;
    }
 
    public static void main(String[] args) throws IOException
    {
        hp = new HashSet<String>();
        
        String fileName = args[0];
        String split[];
        try {
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(line.equals("END OF INPUT"))
                    break;
                split = line.split(" ");
                
                hp.add((split[0]));
                hp.add((split[1]));
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }

        copy = new ArrayList<String>(hp);
        int[][] adjacencyMatrix = new int[hp.size()+1][hp.size()+1];
        
        try {
            FileReader fileReader = 
                new FileReader(fileName);
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(line.equals("END OF INPUT"))
                    break;
                split = line.split(" ");
                
                int i = getIndex(split[0]);
                int j = getIndex(split[1]);
                adjacencyMatrix[j][i] = adjacencyMatrix[i][j] = Integer.parseInt(split[2]);
                
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
        
        for(int i=0;i<adjacencyMatrix.length;i++){
            for(int j=0;j<adjacencyMatrix.length;j++){
                if(adjacencyMatrix[i][j] == 0)
                    adjacencyMatrix[i][j] = 9999;
            }
        }
        
        UniformCostSearch ucs = new UniformCostSearch(copy.size());
        String source = args[1];
        String destination = args[2];
        if(hp.contains(source) && hp.contains(destination)){
        int src = getIndex(source);
        int dst = getIndex(destination);
        int distance = ucs.uniformCostSearch(adjacencyMatrix, src, dst);
        if(distance == 999){
            System.out.println();System.out.println();System.out.println();
            System.out.println("Distance: INFINITY");
            System.out.println("Route:\nNONE");
            System.out.println();System.out.println();System.out.println();
        }else{
            ucs.printPath();
            Iterator<Integer> iterator = ucs.path.descendingIterator();
            System.out.println();System.out.println();System.out.println();
            System.out.println("Distance: "+distance);
            String[] traverse = new String[ucs.path.size()];
            int pp = 0;
            while(iterator.hasNext()){
                String aa = copy.get(iterator.next()-1);
                traverse[pp++] = aa;
            }
            System.out.println("Route:");
            for(int i=0;i<traverse.length-1;i++){
                int j = i+1;
                System.out.println(traverse[i]+" -> "+traverse[j]+", "+adjacencyMatrix[getIndex(traverse[i])][getIndex(traverse[j])]);
            }
            System.out.println("");System.out.println("");System.out.println("");}
        }else{
            System.out.println();System.out.println();System.out.println();
            System.out.println("Specified Location does not exist");
            System.out.println();System.out.println();System.out.println();
        }
    }
    
}
