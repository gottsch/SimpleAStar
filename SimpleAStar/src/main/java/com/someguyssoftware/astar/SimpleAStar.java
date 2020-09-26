package com.someguyssoftware.astar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Based off project by Suwadith @ https://github.com/Suwadith/A-Star-Shortest-Pathfinding-Algorithm-Square-Grid-Java
 * @author Mark Gottschling on Sep 25, 2020
 *
 */
public class SimpleAStar {

	private boolean[][] mapMatrix;
	private Vertex[][] pathMatrix;
	private Coords2D startPoint;
	private Coords2D destinationPoint;
	
	ArrayList<Vertex> pathList = new ArrayList<>();
    ArrayList<Vertex> closedList = new ArrayList<>();
	
	private List<Rectangle2D> obstacles = new ArrayList<>();
	
	private int width = 96;
	private int height = 96;
	
	private int gCost = 0;
	
	public SimpleAStar() {
		pathMatrix = new Vertex[width][height];
	}
	
	public List<Vertex> findPath() {
        // method to generate Chebyshev path. both Horizontal and Diagonal pathways are possible.
        generateHValue(mapMatrix, getStartPoint(), getDestinationPoint(), getWidth(), 10, 10);
        
        if (pathMatrix[getStartPoint().getX()][getStartPoint().getY()].getHValue() != -1 && pathList.contains(pathMatrix[getDestinationPoint().getX()][getDestinationPoint().getY()])) {
//            StdDraw.setPenColor(Color.orange);
//            StdDraw.setPenRadius(0.006);

        	// draw the path
            for (int i = 0; i < pathList.size() - 1; i++) {
            /*System.out.println(pathList.get(i).x + " " + pathList.get(i).y);*/
            /*StdDraw.filledCircle(pathList.get(i).y, n - pathList.get(i).x - 1, .2);*/
            	
// >>                StdDraw.line(pathList.get(i).y, n - 1 - pathList.get(i).x, pathList.get(i + 1).y, n - 1 - pathList.get(i + 1).x);
                
//                gCost += pathList.get(i).gValue;
                /*fCost += pathList.get(i).fValue;*/
            }

            System.out.println("Manhattan Path Found");
//            System.out.println("Total Cost: " + gCost/10.0);
            /*System.out.println("Total fCost: " + fCost);*/
            gCost = 0;
            /*fCost = 0;*/

        } else {
            System.out.println("Manhattan Path Not found");
        }
        
        return pathList;
	}
	
    /**
     * @param matrix         The boolean map matrix that is being navigated
     * @param Ai             Starting point's x value
     * @param Aj             Starting point's y value
     * @param Bi             Ending point's x value
     * @param Bj             Ending point's y value
     * @param width              Length of one side of the matrix
     * @param cost              Cost between 2 cells located horizontally or vertically next to each other
     * @param diagonalCost              Cost between 2 cells located Diagonally next to each other
     * @param additionalPath Boolean to decide whether to calculate the cost of through the diagonal path
     * @param h              int value which decides the correct method to choose to calculate the Heuristic value
     */
    public void generateHValue(boolean matrix[][], Coords2D source, Coords2D destination, int width, int cost, int diagonalCost) {

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix.length; y++) {
                // creating a new Vertex object for each and every cell of the path matrix
                pathMatrix[x][y] = new Vertex(x, y);
                //Checks whether a cell is Blocked or Not by checking the boolean value
                if (matrix[x][y]) {
                       // assigning the Manhattan Heuristic value by calculating the absolute length (x+y) from the ending point to the starting point
                        pathMatrix[x][y].setHValue(Math.abs(x - destination.getX()) + Math.abs(y - destination.getY()));

                } else {
                    // if the boolean value is false, then assigning -1 instead of the absolute length
                    pathMatrix[x][y].setHValue(-1);
                }
            }
        }
        generatePath(pathMatrix, source, destination, width, cost, diagonalCost);
    }
    
    /**
     * @param hValue         Vertex type 2D Array (Matrix)
     * @param Ai             Starting point's y value
     * @param Aj             Starting point's x value
     * @param Bi             Ending point's y value
     * @param Bj             Ending point's x value
     * @param n              Length of one side of the matrix
     * @param v              Cost between 2 cells located horizontally or vertically next to each other
     * @param d              Cost between 2 cells located Diagonally next to each other
     * @param additionalPath Boolean to decide whether to calculate the cost of through the diagonal path
     */
    public void generatePath(Vertex hValue[][], Coords2D source, Coords2D destination, int n, int v, int d) {

        //Creation of a PriorityQueue and the declaration of the Comparator
        @SuppressWarnings("unchecked")
		PriorityQueue<Vertex> openList = new PriorityQueue<>(11, new Comparator() {
            @Override
            //Compares 2 Vertex objects stored in the PriorityQueue and Reorders the Queue according to the object which has the lowest fValue
            public int compare(Object cell1, Object cell2) {
                return ((Vertex) cell1).getFValue() < ((Vertex) cell2).getFValue() ? -1 :
                        ((Vertex) cell1).getFValue() > ((Vertex) cell2).getFValue() ? 1 : 0;
            }
        });

        //Adds the Starting cell inside the openList
        openList.add(pathMatrix[source.getX()][source.getY()]);

        //Executes the rest if there are objects left inside the PriorityQueue
        while (true) {

            //Gets and removes the objects that's stored on the top of the openList and saves it inside node
            Vertex node = openList.poll();

            //Checks if whether node is empty and f it is then breaks the while loop
            if (node == null) {
                break;
            }

            //Checks if whether the node returned is having the same node object values of the ending point
            //If it des then stores that inside the closedList and breaks the while loop
            if (node == pathMatrix[destination.getX()][destination.getY()]) {
                closedList.add(node);
                break;
            }

            closedList.add(node);

            //Left Cell
            try {
                if (pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].getHValue() != -1
                        && !openList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1])
                        && !closedList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1])) {
                    double tCost = node.getFValue() + v;
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].setGValue(v);
                    double cost = pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].getHValue() + tCost;
                    if (pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].getFValue() > cost || !openList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1]))
                        pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].setFValue(cost);

                    openList.add(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1]);
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].setParent(node);
                }
            } catch (IndexOutOfBoundsException e) {
            	e.printStackTrace();
            }

            //Right Cell
            try {
                if (pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].getHValue() != -1
                        && !openList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1])
                        && !closedList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1])) {
                    double tCost = node.getFValue() + v;
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].setGValue(v);
                    double cost = pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].getHValue() + tCost;
                    if (pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].getFValue() > cost || !openList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1]))
                        pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].setFValue(cost);

                    openList.add(pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1]);
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].setParent(node);
                }
            } catch (IndexOutOfBoundsException e) {
            }

            //Bottom Cell
            try {
                if (pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].getHValue() != -1
                        && !openList.contains(pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()])
                        && !closedList.contains(pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()])) {
                    double tCost = node.getFValue() + v;
                    pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].setGValue(v);
                    double cost = pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].getHValue() + tCost;
                    if (pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].getFValue() > cost || !openList.contains(pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()]))
                        pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].setFValue(cost);

                    openList.add(pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()]);
                    pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].setParent(node);
                }
            } catch (IndexOutOfBoundsException e) {
            	e.printStackTrace();
            }

            //Top Cell
            try {
                if (pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].getHValue() != -1
                        && !openList.contains(pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()])
                        && !closedList.contains(pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()])) {
                    double tCost = node.getFValue() + v;
                    pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].setGValue(v);
                    double cost = pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].getHValue() + tCost;
                    if (pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].getFValue() > cost || !openList.contains(pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()]))
                        pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].setFValue(cost);

                    openList.add(pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()]);
                    pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].setParent(node);
                }
            } catch (IndexOutOfBoundsException e) {
            	e.printStackTrace();
            }
        }

        /*for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(pathMatrix[i][j].fValue + "    ");
            }
            System.out.println();
        }*/

        //Assigns the last Object in the closedList to the endVertex variable
        Vertex endVertex = closedList.get(closedList.size() - 1);

        //Checks if whether the endVertex variable currently has a parent Vertex. if it doesn't then stops moving forward.
        //Stores each parent Vertex to the PathList so it is easier to trace back the final path
        while (endVertex.getParent() != null) {
            Vertex currentVertex = endVertex;
            pathList.add(currentVertex);
            endVertex = endVertex.getParent();
        }

        pathList.add(pathMatrix[source.getX()][source.getY()]);
        // clears the openList
        openList.clear();
    }
    
	/**
	 * 
	 * @param obstacles
	 * @return
	 */
	public boolean[][] buildMatrix(List<Rectangle2D> obstacles) {
		return null;
	}

	public Coords2D getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Coords2D startPoint) {
		this.startPoint = startPoint;
	}

	public Coords2D getDestinationPoint() {
		return destinationPoint;
	}

	public void setDestinationPoint(Coords2D destinationPoint) {
		this.destinationPoint = destinationPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
