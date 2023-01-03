package com.tsyrkunou.jmpwep.application.model.graph;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Vertex<T> {
    private T t;
    private boolean beingVisited;
    private boolean visited;
    private List<Vertex<T>> adjacencyList = new ArrayList<>();

    public Vertex(T t) {
        this.t = t;
    }

    public void addNeighbor(Vertex<T> adjacent) {
        this.adjacencyList.add(adjacent);
    }
}