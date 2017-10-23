/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baocy.delay;

import com.baocy.tut2.Tut2Receiver;
import com.baocy.tut4.Tut4Receiver;
import com.baocy.tut4.Tut4Sender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
@Profile({"delay"})
@Configuration
public class DelayConfig {

    @Bean
    public CustomExchange custom() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange("delay-exchange", "x-delayed-message", true, false, args);
    }

    @Profile("delay")
    private static class ReceiverConfig {

        @Bean
        public Queue delayqueue() {
            return new Queue("my-delay-queue");
        }

        @Bean
        public Binding binding(CustomExchange custom, Queue delayqueue) {
            return BindingBuilder.bind(delayqueue).to(custom).with("orange").noargs();
        }
        @Bean
        public DelayReceiver receiver() {
            return new DelayReceiver();
        }

    }

    @Profile("delay")
    @Bean
    public DelaySender sender() {
        return new DelaySender();
    }

}
