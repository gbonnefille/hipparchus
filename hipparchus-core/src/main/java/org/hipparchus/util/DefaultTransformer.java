/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hipparchus.util;

import java.io.Serializable;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;
import org.hipparchus.exception.NullArgumentException;

/**
 * A Default NumberTransformer for java.lang.Numbers and Numeric Strings. This
 * provides some simple conversion capabilities to turn any java.lang.Number
 * into a primitive double or to turn a String representation of a Number into
 * a double.
 * <p>
 * This class is a singleton.
 */
public class DefaultTransformer implements NumberTransformer, Serializable {

    /** Serializable version identifier */
    private static final long serialVersionUID = 20160327L;

    /** Static instance */
    private static final DefaultTransformer INSTANCE = new DefaultTransformer();

    /**
     * Factory returning the singleton instance.
     *
     * @return the singleton instance
     */
    public static DefaultTransformer getInstance() {
        return DefaultTransformer.INSTANCE;
    }

    /**
     * Class is a singleton, prevent instantiation.
     */
    private DefaultTransformer() {}

    /**
     * @param o  the object that gets transformed.
     * @return a double primitive representation of the Object o.
     * @throws NullArgumentException if Object <code>o</code> is {@code null}.
     * @throws MathIllegalArgumentException if Object <code>o</code>
     * cannot successfully be transformed
     * @see <a href="http://commons.apache.org/collections/api-release/org/apache/commons/collections/Transformer.html">Commons Collections Transformer</a>
     */
    @Override
    public double transform(Object o)
        throws MathIllegalArgumentException, NullArgumentException {

        MathUtils.checkNotNull(o, LocalizedCoreFormats.OBJECT_TRANSFORMATION);

        if (o instanceof Number) {
            return ((Number)o).doubleValue();
        }

        try {
            return Double.parseDouble(o.toString());
        } catch (NumberFormatException e) {
            throw new MathIllegalArgumentException(LocalizedCoreFormats.CANNOT_TRANSFORM_TO_DOUBLE,
                                                   o.toString());
        }
    }

    /**
     * Always return the singleton instance when de-serializing this transformer.
     */
    private Object readResolve() {
        return INSTANCE;
    }

}
