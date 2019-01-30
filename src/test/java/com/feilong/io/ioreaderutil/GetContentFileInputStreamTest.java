/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.io.ioreaderutil;

import static com.feilong.core.CharsetType.UTF8;

import java.io.FileInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.io.FileUtil;
import com.feilong.io.IOReaderUtil;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.5
 */
public class GetContentFileInputStreamTest extends AbstractReadFileToStringTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetContentFileInputStreamTest.class);

    //---------------------------------------------------------------

    @Test
    public void testReadFile(){
        String readFileToString = IOReaderUtil.getContent(FileUtil.getFileInputStream(filePath), UTF8);
        LOGGER.debug(readFileToString);

    }

    @Test(expected = NullPointerException.class)
    public void testGetContent(){
        IOReaderUtil.getContent((FileInputStream) null, UTF8);
    }

}
