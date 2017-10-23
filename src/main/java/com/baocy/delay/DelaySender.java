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

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class DelaySender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Exchange custom;

    @Scheduled(fixedDelay = 10000, initialDelay = 500)
    public void send() throws UnsupportedEncodingException {

        Map<String, Object> headers2 = new HashMap<String, Object>();
        headers2.put("x-delay", 1000);
        byte[] bytes = "123tet".getBytes("utf-8");
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("x-delay", "5000");
        MessageBuilder.withBody(bytes).andProperties(messageProperties);
        template.send(custom.getName(), "orange", MessageBuilder.withBody(bytes).andProperties(messageProperties).setContentEncoding("utf-8").copyHeadersIfAbsent(headers2).setContentType("text/plain").build());
//        template.convertAndSend(custom.getName(), "orange", MessageBuilder.withBody(bytes).andProperties(messageProperties).setContentEncoding("utf-8").build());

    }

}
