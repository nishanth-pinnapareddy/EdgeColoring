package cs255.code;

import java.util.LinkedList;

public class Graph {
	private int vertices;
	private int[] degrees;
	private LinkedList<Integer>[] adjList;
	
	public Graph(int vertices) {
		this.vertices = vertices;
		this.degrees = new int[vertices];
		
		this.adjList = new LinkedList[vertices];
		for (int i = 0; i < vertices; i++) {
			this.adjList[i] = new LinkedList<Integer>();
		}
	}
	
	public int getVertices() {
		return vertices;
	}
	public void setVertices(int vertices) {
		this.vertices = vertices;
	}
	public LinkedList<Integer>[] getAdjList() {
		return adjList;
	}
	public void setAdjList(LinkedList<Integer>[] adjList) {
		this.adjList = adjList;
	}
	public int[] getDegrees() {
		return degrees;
	}
	
	public void addEdge(int src, int dest){
		this.adjList[src-1].add(dest-1);
		this.degrees[src-1] += 1; 
		this.degrees[dest-1] += 1; 
	}
}
