/*
 * Copyright (c) 2013-2023, APT Group, Department of Computer Science,
 * The University of Manchester.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.manchester.tornado.api.types.arrays.natives;

import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import uk.ac.manchester.tornado.api.types.arrays.TornadoNativeArray;

public final class NativeVectorDouble extends TornadoNativeArray {
    private final int DOUBLE_BYTES = 8;
    private MemorySegment segment;
    private int numberOfElements;

    private long segmentByteSize;

    public NativeVectorDouble(int numberOfElements) {
        this.numberOfElements = numberOfElements;
        segmentByteSize = numberOfElements * DOUBLE_BYTES;
        segment = Arena.ofAuto().allocate(segmentByteSize, 1);
    }

    public void set(int index, double value) {
        segment.setAtIndex(JAVA_DOUBLE, index, value);
    }

    public double get(int index) {
        return segment.getAtIndex(JAVA_DOUBLE, index);
    }

    public void init(double value) {
        for (int i = 0; i < getSize(); i++) {
            segment.setAtIndex(JAVA_DOUBLE, +i, value);
        }
    }

    @Override
    public int getSize() {
        return numberOfElements;
    }

    @Override
    public MemorySegment getSegment() {
        return segment;
    }

    @Override
    public long getNumBytesOfSegment() {
        return segmentByteSize;
    }

    @Override
    public long getNumBytesWithoutHeader() {
        return segmentByteSize;

    }

    @Override
    public void clear() {
        init(0.0);
    }

}
