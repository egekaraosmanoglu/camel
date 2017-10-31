/**
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
package org.apache.camel.component.aws.xray;

import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

public class ABCRouteTest extends CamelAwsXRayTestSupport {

  public ABCRouteTest() {
    super(
        TestDataBuilder.createTrace().inRandomOrder()
            .withSegment(TestDataBuilder.createSegment("start")
                .withSubsegment(TestDataBuilder.createSubsegment("SendingTo_direct_a")
                    .withSubsegment(TestDataBuilder.createSubsegment("a")
                        .withSubsegment(TestDataBuilder.createSubsegment("SendingTo_seda_b"))
                        .withSubsegment(TestDataBuilder.createSubsegment("SendingTo_seda_c"))
                    )
                )
            )
            .withSegment(TestDataBuilder.createSegment("b"))
            .withSegment(TestDataBuilder.createSegment("c")
                .withSubsegment(TestDataBuilder.createSubsegment("SendingTo_log_test"))
            )
            .withSegment(TestDataBuilder.createSegment("d"))
    );
  }

  @Test
  public void testRoute() throws Exception {
    template.requestBody("direct:start", "Hello");

    verify();
  }

  @Override
  protected RouteBuilder createRouteBuilder() throws Exception {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:start").routeId("start")
            .wireTap("seda:d")
            .to("direct:a");

        from("direct:a").routeId("a")
            .log("routing at ${routeId}")
            .to("seda:b")
            .delay(2000)
            .to("seda:c")
            .log("End of routing");

        from("seda:b").routeId("b")
            .log("routing at ${routeId}")
            .delay(simple("${random(1000,2000)}"));

        from("seda:c").routeId("c")
            .to("log:test")
            .delay(simple("${random(0,100)}"));

        from("seda:d").routeId("d")
            .log("routing at ${routeId}")
            .delay(simple("${random(10,50)}"));
      }
    };
  }
}