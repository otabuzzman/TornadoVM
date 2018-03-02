/*
 * This file is part of Tornado: A heterogeneous programming framework: 
 * https://github.com/beehive-lab/tornado
 *
 * Copyright (c) 2013-2018, APT Group, School of Computer Science,
 * The University of Manchester. All rights reserved.
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
 * Authors: James Clarkson
 *
 */
package uk.ac.manchester.tornado.drivers.opencl.builtins;

import static uk.ac.manchester.tornado.drivers.opencl.builtins.Intrinsics.getGlobalId;
import static uk.ac.manchester.tornado.drivers.opencl.builtins.Intrinsics.getGroupId;
import static uk.ac.manchester.tornado.drivers.opencl.builtins.Intrinsics.getLocalId;
import static uk.ac.manchester.tornado.drivers.opencl.builtins.Intrinsics.localBarrier;

import uk.ac.manchester.tornado.api.ReductionOp;
import uk.ac.manchester.tornado.collections.types.Float2;
import uk.ac.manchester.tornado.collections.types.Float4;
import uk.ac.manchester.tornado.collections.types.VectorFloat4;

public class ReductionIntrinsic {

    public static <T> T op(T a, T b) {
        return a;
    }

    public static void reduceLocalThreadsPrimitive4(float[] global, float[] local, float value) {

        final int index = getLocalId(0);
        local[index] = value;
        localBarrier();

        VectorFloat4 values = new VectorFloat4(local);
        Float4 a = values.get(index);
        for (int i = values.getLength() >> 1; i > 0; i >>= 1) {
            if (index < i) {
                Float4 b = values.get(index + i);
                a = op(a, b);
                values.set(index, a);
            }
            localBarrier();
        }

        Float2 x = op(a.getHi(), a.getLo());
        global[getGroupId(0)] = op(x.getX(), x.getY());
    }

    public static float reduceGF4(ReductionOp op, float[] global) {

        final int index = getGlobalId(0);
        VectorFloat4 values = new VectorFloat4(global);
        Float4 a = values.get(index);
        for (int i = values.getLength() >> 1; i > 0; i >>= 1) {
            if (index < i) {
                Float4 b = values.get(index + i);
                a = Intrinsics.op(op, a, b);
                values.set(index, a);
            }
            localBarrier();
        }

        Float2 x = Intrinsics.op(op, a.getHi(), a.getLo());
        return Intrinsics.op(op, x.getX(), x.getY());
    }

}