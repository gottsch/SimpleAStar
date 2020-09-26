package com.someguyssoftware.astar;

public class Vertex {
	private Coords2D coords;
	private int gValue;
	private double hValue;
	private double fValue;
	private Vertex parent;
	
	public Vertex(int x, int y) {
		coords = new Coords2D(x, y);
	}
	
	public Vertex(Coords2D coords) {
		this(coords.getX(), coords.getY());
	}

	public Coords2D getCoords() {
		return coords;
	}

	public void setCoords(Coords2D coords) {
		this.coords = coords;
	}

	public int getGValue() {
		return gValue;
	}

	public void setGValue(int gValue) {
		this.gValue = gValue;
	}

	public double getHValue() {
		return hValue;
	}

	public void setHValue(double hValue) {
		this.hValue = hValue;
	}

	public double getFValue() {
		return fValue;
	}

	public void setFValue(double fValue) {
		this.fValue = fValue;
	}

	public Vertex getParent() {
		return parent;
	}

	public void setParent(Vertex parent) {
		this.parent = parent;
	}
}
