package cs255.code;

import java.util.List;

public class Driver {

	public static void main(String[] args) {
		EdgeColoringBipartiteGraph edgeColoringBipartiteGraph = new EdgeColoringBipartiteGraph();

		Graph graph = edgeColoringBipartiteGraph
				.loadDatasetIntoGraph("D:/Eclipse Workspace/edgecoloring/src/main/resources/movie_actor_dataset");
		
		edgeColoringBipartiteGraph.colorEdgesBasedOnCombinedDegree(graph, edgeColoringBipartiteGraph.edgesList);
		
		List<Integer> orderedVertices = edgeColoringBipartiteGraph.orderVerticesBasedOnDegree(graph);
		edgeColoringBipartiteGraph.colorEdgesBasedOnDegre(graph, orderedVertices);

		List<Edge> orderedEdges = edgeColoringBipartiteGraph.orderVerticesBasedOnCombinedDegree(graph);
		edgeColoringBipartiteGraph.colorEdgesBasedOnCombinedDegree(graph, orderedEdges);

	}

}
