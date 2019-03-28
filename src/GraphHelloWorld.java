/*
 * (C) Copyright 2003-2018, by Barak Naveh and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */

import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.attribute.Records.turn;
import static guru.nidi.graphviz.model.Compass.*;
import static guru.nidi.graphviz.model.Factory.*;

/**
 * A simple introduction to using JGraphT.
 *
 * @author Barak Naveh
 */
public class GraphHelloWorld {
    public GraphHelloWorld() {
    }
    private static final Logger logger = LogManager.getLogger(GraphHelloWorld.class);

    public static void main(String[] args) {
        Node
                node0 = node("node0").with(Records.of(rec("f0", "ali"), rec("f1", ""), rec("f2", ""),
                                                            rec("f3", "mmd"), rec("f4", ""))),
                node1 = node("node1").with(Records.of(turn(rec("n4"), rec("v", "719"), rec("")))),
                node2 = node("node2").with(Records.of(turn(rec("a1"), rec("805"), rec("p", "")))),
                node3 = node("node3").with(Records.of(turn(rec("i9"), rec("718"), rec("")))),
                node4 = node("node4").with(Records.of(turn(rec("e5"), rec("989"), rec("p", "")))),
                node5 = node("node5").with(Records.of(turn(rec("t2"), rec("v", "959"), rec("")))),
                node6 = node("node6").with(Records.of(turn(rec("o1"), rec("794"), rec("")))),
                node7 = node("node7").with(Records.of(turn(rec("s7"), rec("659"), rec(""))));
        Graph g = graph("example3").directed()
                .graphAttr().with(RankDir.LEFT_TO_RIGHT)
                .with(
                        node0.link(
                                between(port("f0"), node1.port("v", SOUTH)),
                                between(port("f1"), node2.port(WEST)),
                                between(port("f2"), node3.port(WEST)),
                                between(port("f3"), node4.port(WEST)),
                                between(port("f4"), node5.port("v", NORTH))),
                        node2.link(between(port("p"), node6.port(NORTH_WEST))),
                        node4.link(between(port("p"), node7.port(SOUTH_WEST))));
        try {
            Graphviz.fromGraph(g).width(900).height(900).render(Format.PNG).toFile(new File("example/ex.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
