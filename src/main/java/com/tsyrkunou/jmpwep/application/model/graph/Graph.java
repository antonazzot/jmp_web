package com.tsyrkunou.jmpwep.application.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.util.Pair;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Graph<T> {
    private List<Vertex<T>> vertices = new ArrayList<>();
    private Pair<T, T> dependencyTree;

    public void addSegment(GraphSegment<T> segment) {
        var from = segment.getFrom();
        addVertex(from);
        segment.getTo().forEach(seg -> {
            addVertex(seg);
            addEdge(from, seg);
        });
    }

    private void addEdge(Vertex<T> from, Vertex<T> to) {
        from.addNeighbor(to);
    }

    public void addVertex(Vertex<T> vertex) {
        this.vertices.add(vertex);
    }

    public boolean hasCycle() {
        for (Vertex<T> vertex : vertices) {
            if (!vertex.isVisited() && hasCycle(vertex)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle(Vertex<T> sourceVertex) {
        sourceVertex.setBeingVisited(true);
        for (Vertex<T> neighbor : sourceVertex.getAdjacencyList()) {
            dependencyTree = Pair.of(sourceVertex.getT(), neighbor.getT());
            if (neighbor.isBeingVisited()) {
                return true;
            } else if (!neighbor.isVisited() && hasCycle(neighbor)) {
                dependencyTree = Pair.of(sourceVertex.getT(), neighbor.getT());
                return true;
            }
        }

        sourceVertex.setBeingVisited(false);
        sourceVertex.setVisited(true);
        return false;
    }
}