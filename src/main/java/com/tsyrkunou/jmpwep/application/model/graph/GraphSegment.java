package com.tsyrkunou.jmpwep.application.model.graph;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GraphSegment<T> {
    private Vertex<T> from;
    private List<Vertex<T>> to;
}
