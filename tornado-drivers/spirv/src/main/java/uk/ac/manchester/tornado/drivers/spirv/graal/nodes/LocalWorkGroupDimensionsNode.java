/*
 * This file is part of Tornado: A heterogeneous programming framework:
 * https://github.com/beehive-lab/tornadovm
 *
 * Copyright (c) 2021, APT Group, Department of Computer Science,
 * School of Engineering, The University of Manchester. All rights reserved.
 * Copyright (c) 2009-2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */
package uk.ac.manchester.tornado.drivers.spirv.graal.nodes;

import org.graalvm.compiler.core.common.type.StampFactory;
import org.graalvm.compiler.graph.NodeClass;
import org.graalvm.compiler.nodeinfo.NodeInfo;
import org.graalvm.compiler.nodes.calc.FloatingNode;
import org.graalvm.compiler.nodes.spi.LIRLowerable;
import org.graalvm.compiler.nodes.spi.NodeLIRBuilderTool;

import jdk.vm.ci.meta.JavaKind;

@NodeInfo
public class LocalWorkGroupDimensionsNode extends FloatingNode implements LIRLowerable {

    public int oneD;
    public int twoD;
    public int threeD;

    public static final NodeClass<LocalWorkGroupDimensionsNode> TYPE = NodeClass.create(LocalWorkGroupDimensionsNode.class);

    public LocalWorkGroupDimensionsNode(int valueOne, int valueTwo, int valueThree) {
        super(TYPE, StampFactory.forKind(JavaKind.Int));
        assert stamp != null;
        oneD = valueOne;
        twoD = valueTwo;
        threeD = valueThree;
    }

    @Override
    public void generate(NodeLIRBuilderTool nodeLIRBuilderTool) {
    }

    public int getOneD() {
        return oneD;
    }

    public int getTwoD() {
        return twoD;
    }

    public int getThreeD() {
        return threeD;
    }
}
