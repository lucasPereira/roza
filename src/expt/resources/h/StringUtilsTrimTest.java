/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils} trim methods.
 */
class StringUtilsTrimTest extends AbstractLangTest {

    private static final String FOO = "foo";

    @Test
    void testTrim() {
        assertEquals(FOO, StringUtils.trim(FOO + "  "));
        assertEquals(FOO, StringUtils.trim(" " + FOO + "  "));
        assertEquals(FOO, StringUtils.trim(" " + FOO));
        assertEquals(FOO, StringUtils.trim(FOO + ""));
        assertEquals("", StringUtils.trim(" \t\r\n\b "));
        assertEquals("", StringUtils.trim(StringUtilsTest.TRIMMABLE));
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trim(StringUtilsTest.NON_TRIMMABLE));
        assertEquals("", StringUtils.trim(""));
        assertNull(StringUtils.trim(null));
    }

    @Test
    void testTrimToEmpty() {
        assertEquals(FOO, StringUtils.trimToEmpty(FOO + "  "));
        assertEquals(FOO, StringUtils.trimToEmpty(" " + FOO + "  "));
        assertEquals(FOO, StringUtils.trimToEmpty(" " + FOO));
        assertEquals(FOO, StringUtils.trimToEmpty(FOO + ""));
        assertEquals("", StringUtils.trimToEmpty(" \t\r\n\b "));
        assertEquals("", StringUtils.trimToEmpty(StringUtilsTest.TRIMMABLE));
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToEmpty(StringUtilsTest.NON_TRIMMABLE));
        assertEquals("", StringUtils.trimToEmpty(""));
        assertEquals("", StringUtils.trimToEmpty(null));
    }

    @Test
    void testTrimToNull() {
        assertEquals(FOO, StringUtils.trimToNull(FOO + "  "));
        assertEquals(FOO, StringUtils.trimToNull(" " + FOO + "  "));
        assertEquals(FOO, StringUtils.trimToNull(" " + FOO));
        assertEquals(FOO, StringUtils.trimToNull(FOO + ""));
        assertNull(StringUtils.trimToNull(" \t\r\n\b "));
        assertNull(StringUtils.trimToNull(StringUtilsTest.TRIMMABLE));
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToNull(StringUtilsTest.NON_TRIMMABLE));
        assertNull(StringUtils.trimToNull(""));
        assertNull(StringUtils.trimToNull(null));
    }
}
