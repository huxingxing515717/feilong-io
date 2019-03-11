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
package com.feilong.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.core.UncheckedIOException;
import com.feilong.core.lang.StringUtil;

/**
 * {@link java.io.InputStream} 工具类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see ReaderUtil
 * @see java.io.InputStream
 * @since 1.0.9
 */
public final class InputStreamUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamUtil.class);

    /** Don't let anyone instantiate this class. */
    private InputStreamUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 构造一个 {@link ByteArrayInputStream}.
     *
     * @param str
     *            the str
     * @param charsetType
     *            受支持的 charset 名称,比如 utf-8, {@link CharsetType}
     * @return 如果 <code>str</code> 是null,抛出 {@link NullPointerException}<br>
     * @see org.apache.commons.io.IOUtils#toInputStream(String, Charset)
     * @since 1.12.1
     */
    public static ByteArrayInputStream newByteArrayInputStream(String str,String charsetType){
        Validate.notNull(str, "str can't be null!");
        return new ByteArrayInputStream(StringUtil.getBytes(str, charsetType));
    }

    //---------------------------------------------------------------

    /**
     * 使用默认的编码集 {@link Charset#defaultCharset()} 将 {@link java.io.InputStream} 转成string.
     * 
     * <p>
     * 如果需要将 {@link String} 转成 {@link InputStream} 可以调用 {@link IOUtils#toInputStream(String, Charset)}
     * </p>
     * 
     * @param inputStream
     *            the input stream
     * @return 将 {@link java.io.InputStream} 转成string <br>
     *         如果 <code>inputStream</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #toString(InputStream, String)
     */
    public static String toString(InputStream inputStream){
        Validate.notNull(inputStream, "inputStream can't be null!");

        Charset defaultCharset = Charset.defaultCharset();
        String charsetName = defaultCharset.name();
        LOGGER.debug("will use defaultCharset:[{}]", charsetName);
        return toString(inputStream, charsetName);
    }

    /**
     * 将 {@link java.io.InputStream} 转成string.
     * 
     * <p>
     * 读取cmd命令结果时候, 有时候读取的是乱码,需要传递 <code>charsetName</code>字符集.
     * </p>
     * 
     * <p>
     * 如果需要将 {@link String} 转成 {@link InputStream} 可以调用 {@link IOUtils#toInputStream(String, Charset)}
     * </p>
     * 
     * @param inputStream
     *            the input stream
     * @param charsetName
     *            指定受支持的 charset 的名称
     * @return 将 {@link java.io.InputStream} 转成string<br>
     *         如果 <code>inputStream</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #toBufferedReader(InputStream, String)
     * @see ReaderUtil#toString(Reader)
     */
    public static String toString(InputStream inputStream,String charsetName){
        Validate.notNull(inputStream, "inputStream can't be null!");

        BufferedReader bufferedReader = toBufferedReader(inputStream, charsetName);
        return ReaderUtil.toString(bufferedReader);
    }

    //---------------------------------------------------------------

    /**
     * 将 {@link java.io.InputStream} 转成 {@link java.io.BufferedReader} ({@link java.io.BufferedReader} 缓冲 高效读取).
     *
     * @param inputStream
     *            the input stream
     * @param charsetName
     *            the charset name
     * @return 如果 <code>inputStream</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.io.BufferedReader
     * @see java.io.InputStreamReader#InputStreamReader(InputStream, String)
     * @see org.apache.commons.io.IOUtils#toBufferedReader(Reader)
     */
    public static BufferedReader toBufferedReader(InputStream inputStream,String charsetName){
        Validate.notNull(inputStream, "inputStream can't be null!");

        try{
            Reader reader = new InputStreamReader(inputStream, charsetName);

            // 缓冲 高效读取  bufferedReader 
            // 包装所有其 read() 操作可能开销很高的 Reader(如 FileReader 和 InputStreamReader).
            return IOUtils.toBufferedReader(reader);
        }catch (UnsupportedEncodingException e){
            throw new UncheckedIOException(e);
        }
    }
}