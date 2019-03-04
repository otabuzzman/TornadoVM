/*
 * Copyright (c) 2013-2019, APT Group, School of Computer Science,
 * The University of Manchester.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package uk.ac.manchester.tornado.unittests.matrices;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import uk.ac.manchester.tornado.api.TaskSchedule;
import uk.ac.manchester.tornado.api.annotations.Parallel;
import uk.ac.manchester.tornado.api.collections.types.MatrixFloat;
import uk.ac.manchester.tornado.unittests.common.TornadoTestBase;

public class TestMatrix2DTypes extends TornadoTestBase {

    private static final int N = 1024;

    public static void computeMatrixSum(MatrixFloat a, MatrixFloat b) {
        for (@Parallel int i = 0; i < N; i++) {
            for (@Parallel int j = 0; j < N; j++) {
                b.set(i, j, a.get(i, j) + a.get(i, j));
            }
        }
    }

    public static void computeMatrixMultiplication(MatrixFloat a, MatrixFloat b, MatrixFloat c) {
        for (@Parallel int i = 0; i < a.M(); i++) {
            for (@Parallel int j = 0; j < a.N(); j++) {
                float sum = 0.0f;
                for (int k = 0; k < a.N(); k++) {
                    sum += a.get(i, k) + a.get(k, j);
                }
                c.set(i, j, sum);
            }
        }
    }

    @Test
    public void testMatrix01() {
        MatrixFloat matrixA = new MatrixFloat(N, N);
        MatrixFloat matrixB = new MatrixFloat(N, N);
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrixA.set(i, j, r.nextFloat());
            }
        }

        TaskSchedule ts = new TaskSchedule("s0");
        ts.task("t0", TestMatrix2DTypes::computeMatrixSum, matrixA, matrixB);
        ts.streamOut(matrixB);
        ts.execute();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assertEquals(matrixA.get(i, j) + matrixA.get(i, j), matrixB.get(i, j), 0.01f);
            }
        }
    }

    @Test
    public void testMatrix02() {
        float[][] a = new float[N][N];
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = r.nextFloat();
            }
        }
        MatrixFloat matrixA = new MatrixFloat(a);
        MatrixFloat matrixB = new MatrixFloat(N, N);
        TaskSchedule ts = new TaskSchedule("s0");
        ts.task("t0", TestMatrix2DTypes::computeMatrixSum, matrixA, matrixB);
        ts.streamOut(matrixB);
        ts.execute();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assertEquals(matrixA.get(i, j) + matrixA.get(i, j), matrixB.get(i, j), 0.01f);
            }
        }
    }

    @Test
    public void testMatrix03() {
        MatrixFloat matrixA = new MatrixFloat(N, N);
        MatrixFloat matrixB = new MatrixFloat(N, N);
        MatrixFloat matrixC = new MatrixFloat(N, N);
        MatrixFloat sequential = new MatrixFloat(N, N);
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrixA.set(i, j, r.nextFloat());
                matrixB.set(i, j, r.nextFloat());
            }
        }

        TaskSchedule ts = new TaskSchedule("s0");
        ts.task("t0", TestMatrix2DTypes::computeMatrixMultiplication, matrixA, matrixB, matrixC);
        ts.streamOut(matrixC);
        ts.execute();

        computeMatrixMultiplication(matrixA, matrixB, sequential);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assertEquals(sequential.get(i, j), matrixC.get(i, j), 0.01f);
            }
        }
    }

}
