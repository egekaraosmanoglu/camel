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
package org.apache.camel.support;

import org.apache.camel.Exchange;
import org.apache.camel.spi.Configurer;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.spi.Metadata;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This strategy is used for accepting all headers.
 * The intention is for use with development and troubleshooting where you want Camel to keep all headers
 * when sending and receiving using components that uses {@link HeaderFilterStrategy}.
 */
@Metadata(label = "bean",
          description = "This strategy is used for accepting all headers.The intention is for use with development and troubleshooting where you want Camel to keep all headers\n" +
                        " * when sending and receiving using components that uses HeaderFilterStrategy",
          annotations = { "interfaceName=org.apache.camel.spi.HeaderFilterStrategy" })
@Configurer(metadataOnly = true)
public class AcceptAllHeaderFilterStrategy implements HeaderFilterStrategy {

    @Override
    public boolean applyFilterToCamelHeaders(String headerName, Object headerValue, Exchange exchange) {
        return false; // return false to accept header
    }

    @Override
    public boolean applyFilterToExternalHeaders(String headerName, Object headerValue, Exchange exchange) {
        return false; // return false to accept header
    }
}
