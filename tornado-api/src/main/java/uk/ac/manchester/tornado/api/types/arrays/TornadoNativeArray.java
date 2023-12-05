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
package uk.ac.manchester.tornado.api.types.arrays;

import java.lang.foreign.MemorySegment;

import uk.ac.manchester.tornado.api.types.arrays.natives.NativeVectorByte;
import uk.ac.manchester.tornado.api.types.arrays.natives.NativeVectorDouble;
import uk.ac.manchester.tornado.api.types.arrays.natives.NativeVectorFloat;
import uk.ac.manchester.tornado.api.types.arrays.natives.NativeVectorInt;
import uk.ac.manchester.tornado.api.types.arrays.natives.NativeVectorShort;

/**
 * This abstract sealed class represents the common functionality of the TornadoVM custom off-heap data structures,
 * i.e., native arrays ({@link ByteArray}, {@link IntArray}, etc.) and native vector collections ({@link NativeVectorByte},
 * {@link NativeVectorDouble}, etc.).
 *
 * <p>
 * The class provides methods for retrieving the number of elements stored in the native data structures,
 * for obtaining the underlying memory segment, for clearing the data, for calculating the total number of
 * bytes occupied by the memory segment, and for getting the number of bytes, excluding the array header size.
 * </p>
 *
 * <p>
 * The constant {@code ARRAY_HEADER} represents the size of the header in bytes.
 * </p>
 */
public abstract sealed class TornadoNativeArray permits //
        IntArray, FloatArray, DoubleArray, LongArray, ShortArray, //
        ByteArray, CharArray, NativeVectorByte, NativeVectorDouble, //
        NativeVectorShort, NativeVectorFloat, NativeVectorInt {

    /**
     * The size of the header in bytes. The default value is 24, but it can be configurable through
     * the "tornado.panama.objectHeader" system property.
     */
    public static final long ARRAY_HEADER = Long.parseLong(System.getProperty("tornado.panama.objectHeader", "24"));

    /**
     * Returns the number of elements stored in the native array or vector.
     * @return The number of elements of the native data structure.
     */
    public abstract int getSize();

    /**
     * Returns the underlying {@link MemorySegment} of the native data structure.
     * @return The {@link MemorySegment} associated with the native data structure instance.
     */
    public abstract MemorySegment getSegment();

    /**
     * Returns the total number of bytes that the {@link MemorySegment} occupies.
     * @return The total number of bytes of the {@link MemorySegment}.
     */
    public abstract long getNumBytesOfSegment();

    /**
     * Returns the number of bytes of the {@link MemorySegment}, excluding the header bytes.
     * @return The number of bytes of the raw data in the {@link MemorySegment}.
     */
    public abstract long getNumBytesWithoutHeader();

    /**
     * Clears the contents of the native data structure.
     */
    protected abstract void clear();

}
