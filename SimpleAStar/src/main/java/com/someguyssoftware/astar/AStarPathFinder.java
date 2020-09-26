package com.someguyssoftware.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * Based off project by Suwadith @ https://github.com/Suwadith/A-Star-Shortest-Pathfinding-Algorithm-Square-Grid-Java
 * @author Mark Gottschling on Sep 25, 2020
 *
 */
public class AStarPathFinder {
    /*
     * the map that is to be navigated 
     */
    private boolean[][] mapMatrix;
    /*
     * the matrix that stores the path
     */
    private Vertex[][] pathMatrix;

    /**
     * the starting point
     */
    private Coords2D start;
    /**
     * the end point
     */
	private Coords2D end;
	/**
     * a list of vertexs that represents the path taken
     */
    private List<Vertex> path;
	
	private List<Rectangle2D> obstacles = new ArrayList<>();
    
    // TODO these two are actually unnecessary as the map has the dimensions
    /**
     * width of the map
     */
    private int width = 96;
    /** 
     * height of the map
     */
    private int height = 96;
    
	
	private int gCost = 0;
    
    /**
     * 
     */
    public AStarPathFinder() {
	}
    
    /**
     * 
     * @return
     */
	public Optional<List<Vertex>> findPath() {
        // NOTE true values in the map matrix are travsable and false is blocked.
        pathMatrix = new Vertex[mapMatrix.length][mapMatrix[0].length];
        
        // method to generate Manhattan path. both Horizontal and Diagonal pathways are possible.
        generatePathMatrix(mapMatrix, getStart(), getEnd(), 10);
        
        if (pathMatrix[getStart().getX()][getStart().getY()].getHValue() != -1 
            && path.contains(pathMatrix[getEnd().getX()][getEnd().getY()])) {
            System.out.println("Manhattan Path Found");

            // TODO convert List<Vertex> to List<Coords2s>;
            // TODO make Vertex an inner class of AStarPathFinder
            // TODO rename Vertex to Node

        } else {
            System.out.println("Manhattan Path Not found");
            return Optional.empty();
        } 
        return Optional.of(getPath());
	}
	
    /**
     * @param matrix         The boolean map matrix that is being navigated
     * @param Ai             Starting point's x value
     * @param Aj             Starting point's y value
     * @param Bi             Ending point's x value
     * @param Bj             Ending point's y value
     * @param width              Length of one side of the matrix
     * @param cost              Cost between 2 cells located horizontally or vertically next to each other
     */
    private void generatePathMatrix(boolean matrix[][], Coords2D start, Coords2D end, int cost) {

        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                // creating a new Vertex object for each and every cell of the path matrix
                pathMatrix[x][y] = new Vertex(x, y);
                //Checks whether a cell is Blocked or Not by checking the boolean value
                if (matrix[x][y]) {
                       // assigning the Manhattan Heuristic value by calculating the absolute length (x+y) from the ending point to the starting point
                        pathMatrix[x][y].setHValue(Math.abs(x - end.getX()) + Math.abs(y - end.getY()));

                } else {
                    // if the boolean value is false, then assigning -1 instead of the absolute length
                    pathMatrix[x][y].setHValue(-1);
                }
            }
        }
        generatePath(start, end, cost);
    }
    
    /**

     * @param start        Starting coords;
     * @param end  Ending coords;
     * @param cost            Cost between 2 cells located horizontally or vertically next to each other
     */
    private void generatePath(Coords2D start, Coords2D end, int cost) {

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
        List<Vertex> closedList = new ArrayList<>();

        //Adds the Starting cell inside the openList
        openList.add(pathMatrix[start.getX()][start.getY()]);

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
            if (node == pathMatrix end.getX()] end.getY()]) {
                closedList.add(node);
                break;
            }

            closedList.add(node);

            //Left Cell
            try {
                if (pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].getHValue() != -1
                        && !openList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1])
                        && !closedList.contains(pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1])) {
                    double tCost = node.getFValue() + cost;
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() - 1].setGValue(cost);
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
                    double tCost = node.getFValue() + cost;
                    pathMatrix[node.getCoords().getX()][node.getCoords().getY() + 1].setGValue(cost);
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
                    double tCost = node.getFValue() + cost;
                    pathMatrix[node.getCoords().getX() + 1][node.getCoords().getY()].setGValue(cost);
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
                    double tCost = node.getFValue() + cost;
                    pathMatrix[node.getCoords().getX() - 1][node.getCoords().getY()].setGValue(cost);
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


        //Assigns the last Object in the closedList to the endVertex variable
        Vertex endVertex = closedList.get(closedList.size() - 1);

        //Checks if whether the endVertex variable currently has a parent Vertex. if it doesn't then stops moving forward.
        //Stores each parent Vertex to the PathList so it is easier to trace back the final path
        while (endVertex.getParent() != null) {
            Vertex currentVertex = endVertex;
            path.add(currentVertex);
            endVertex = endVertex.getParent();
        }

        path.add(pathMatrix[start.getX()][start.getY()]);
        // clears the openList
        openList.clear();
    }
    
	/**
	 * 
	 * @param obstacles
	 * @return
	 */
	public AStarPathFinder withMap(final List<Rectangle2D> obstacles) {
        final boolean[][] map = new boolean[width][height];
        obstacles.forEach(obstacle -> {
            for(int x = 0; x < obstacle.getWidth(); x++) {
                for(int y = 0; y < obstacle.getHeight(); y++) {
                    map[obstacle.getOrigin().getX() + x][obstacle.getOrigin().getY() + y] = true;
                }
            }
        });
        this.mapMatrix = map;
        return this;
	}

	public Coords2D getStart() {
		return start;
	}

	public AStarPathFinder withStart(Coords2D start) {
        this.start = start;
        return this;
	}

	public Coords2D getEnd() {
		return end;
	}

	public AStarPathFinder withEnd(Coords2D end) {
		this.end = end;
        return this;
	}

	public int getWidth() {
		return width;
	}

	public AStarPathFinder withWidth(int width) {
        this.width = width;
        return this;
	}

	public int getHeight() {
		return height;
	}

	public AStarPathFinder withHeight(int height) {
        this.height = height;
        return this;
	}

    public List<Vertex> getPath() {
        if (path == null) {
            path = new ArrayList<>();
        }
        return path;
    }

    private void setPath(List<Vertex> path) {
        this.path = path;
    }
}
