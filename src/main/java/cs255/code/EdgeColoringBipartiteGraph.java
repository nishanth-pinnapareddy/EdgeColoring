package cs255.code;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class EdgeColoringBipartiteGraph {
	
	public List<Edge> edgesList = new ArrayList<Edge>();

	// Load the graph from data set in to adjacency list
	public Graph loadDatasetIntoGraph(String fileName) {

		try {
			File f = new File(fileName);
			List<String> lines = FileUtils.readLines(f, "UTF-8");

			Graph graph = new Graph(Integer.valueOf(lines.get(0).trim()));
			for (int index = 1; index < lines.size(); index++) {
				String[] nodes = lines.get(index).split(" ");
				graph.addEdge(Integer.valueOf(nodes[0].trim()), Integer.valueOf(nodes[1].trim()));
				edgesList.add(new Edge(Integer.valueOf(nodes[0].trim()), Integer.valueOf(nodes[1].trim())));
			}

			return graph;
		} catch (Exception e) {
			System.err.println("Unable to create graph out of dataset. Exception : " + e.getMessage());
		}

		return null;
	}

	// Greedy approach to order the edges

	// 2. Highest Degree first
	public List<Integer> orderVerticesBasedOnDegree(Graph graph) {

		HashMap<Integer, Integer> degree_map = new HashMap<Integer, Integer>();
		for (int vertex = 0; vertex < graph.getVertices(); vertex++) {
			degree_map.put(vertex, graph.getDegrees()[vertex]);
		}

		Set<Entry<Integer, Integer>> set = degree_map.entrySet();
		List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> order1, Map.Entry<Integer, Integer> order2) {
				return order2.getValue().compareTo(order1.getValue());
			}
		});

		List<Integer> orderedVertices_degree = new ArrayList<Integer>();
		for (Entry<Integer, Integer> entry : list) {
			orderedVertices_degree.add(entry.getKey());

		}

		return orderedVertices_degree;
	}

	// 3. Highest Combined Degree first
	public List<Edge> orderVerticesBasedOnCombinedDegree(Graph graph) {
		
		HashMap<Edge, Integer> combinedDegree_map = new HashMap<Edge, Integer>();
		for (int src = 0; src < graph.getVertices(); src++){
			LinkedList<Integer> destVertices = graph.getAdjList()[src];
			for(Integer dest : destVertices){
				Edge edge = new Edge(src, dest);
				int combinedDegree = graph.getDegrees()[src] + graph.getDegrees()[dest];
				combinedDegree_map.put(edge, combinedDegree);
			}
		}
		
		Set<Entry<Edge, Integer>> set = combinedDegree_map.entrySet();
		List<Entry<Edge, Integer>> list = new ArrayList<Entry<Edge, Integer>>(set);
		Collections.sort(list, new Comparator<Map.Entry<Edge, Integer>>() {
			public int compare(Map.Entry<Edge, Integer> order1, Map.Entry<Edge, Integer> order2) {
				return order2.getValue().compareTo(order1.getValue());
			}
		});

		List<Edge> orderedVertices_combinedDegree = new ArrayList<Edge>();
		for (Entry<Edge, Integer> entry : list) {
			orderedVertices_combinedDegree.add(entry.getKey());

		}
		return orderedVertices_combinedDegree;
	}

	// Color the edges based on degree
	public void colorEdgesBasedOnDegre(Graph graph, List<Integer> orderedVertices) {
		List<Edge> orderedEdges = new ArrayList<Edge>();
		for (Integer src : orderedVertices) {
			for (Object dest : graph.getAdjList()[src]) {
				orderedEdges.add(new Edge(src, (Integer) dest));
			}
		}

		HashMap<Integer, List<Edge>> edgeColors = new HashMap<Integer, List<Edge>>();
		int color = 0;

		while (orderedEdges.size() >= 1) {
			color++;
			int len = orderedEdges.size();
			;
			for (int index = 0; index < len; index++) {
				Edge curEdge = orderedEdges.get(index);
				if (!edgeColors.containsKey(color)) {
					List<Edge> edges = new ArrayList<Edge>();
					edges.add(curEdge);
					edgeColors.put(color, edges);
					orderedEdges.remove(curEdge);
					len--;
					index--;
				} else {
					List<Edge> edges = edgeColors.get(color);
					if (!checkWhetherSameColor(edges, curEdge)) {
						edges.add(curEdge);
						orderedEdges.remove(curEdge);
						len--;
						index--;
					}
				}
			}
		}

		for (Integer c : edgeColors.keySet()) {
			List<Edge> edges = edgeColors.get(c);
			System.out.println("Color : " + c);
			for (Edge edge : edges) {
				System.out.print(edge.getSrc() + " ==> " + edge.getDest() + " " + "\n");
			}
			System.out.println();
		}
		System.out.println("Number of colors required for this graph: " + color);
	}
	
	//color edges based on combined degree
	public void colorEdgesBasedOnCombinedDegree(Graph graph, List<Edge> orderedEdges) {
		
		HashMap<Integer, List<Edge>> edgeColors = new HashMap<Integer, List<Edge>>();
		int color = 0;

		while (orderedEdges.size() >= 1) {
			color++;
			int len = orderedEdges.size();
			;
			for (int index = 0; index < len; index++) {
				Edge curEdge = orderedEdges.get(index);
				if (!edgeColors.containsKey(color)) {
					List<Edge> edges = new ArrayList<Edge>();
					edges.add(curEdge);
					edgeColors.put(color, edges);
					orderedEdges.remove(curEdge);
					len--;
					index--;
				} else {
					List<Edge> edges = edgeColors.get(color);
					if (!checkWhetherSameColor(edges, curEdge)) {
						edges.add(curEdge);
						orderedEdges.remove(curEdge);
						len--;
						index--;
					}
				}
			}
		}

		for (Integer c : edgeColors.keySet()) {
			List<Edge> edges = edgeColors.get(c);
			System.out.println("Color : " + c);
			for (Edge edge : edges) {
				System.out.print(edge.getSrc() + " ==> " + edge.getDest() + " " + "\n");
			}
			System.out.println();
		}
		System.out.println("Number of colors required for this graph: " + color);
	}

	// color comparison
	public boolean checkWhetherSameColor(List<Edge> edges, Edge curEdge) {
		for (Edge e : edges) {
			if (e.getSrc() == curEdge.getSrc() || e.getSrc() == curEdge.getDest() || e.getDest() == curEdge.getSrc()
					|| e.getDest() == curEdge.getDest()) {
				return true;
			}
		}

		return false;

	}

	// Display the results

	// Visualize the colored graph

}
