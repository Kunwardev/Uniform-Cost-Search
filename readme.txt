1. The Program contains three classes: find_route, node, UniformCostSearch. All three classes are explained in the followin
   points:
	a) The Node class contains the information of each point. It has a String name, an ID named node, that acts as
	   an index for the adjacency matrix formed in the find_route class. There is a variable named 'cost' that is 
	   initialized when the file is read. The node also implements COMPARATOR, that will sort the path to their children
	   in increasing order.
	b) UniformCostSearch is the class that contains all the function, and is called in the main method of the find_route
	   The functions takes the matrix, that is read by the main class and the source and destination that are passed
	   as arguments will be stored, and that index will be searched, all of its children will be searched, and it will
	   keep on working until the optimal path is found or the priority queue becomes empty.
	c) find_route is the main class that will read the text file, and put the values in the matrix. This adjacency matrix
	   will put the cost in the required index, the path that are considered to be 0, will be changed to a maximum 
	   value like 999. Then it will call the class, by passing the matrix, and execute the functions, and print the 
	   required result.

2. The Program is made in java. It is running in omega.uta.edu. First you need to call a command that will create 3 .class
   files that is "javac find_route.java". Then a second command will be entered that will run the program. The command is
   "java find_route input1.txt source destination " input1.txt is the name of the file that contains the node and the cost of
   the path. In the place of source, type the name of the place that you want the path from, and in place of destination, 
   type the name of the placem that you want the path to.

3. This program is submitted by "SINGH, KUNWAR DEV" , "ID: 1001448465".