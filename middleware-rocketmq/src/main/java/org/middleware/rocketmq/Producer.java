package org.middleware.rocketmq;

import java.util.Date;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;


public class Producer {
	
	public static void main(String[] args) throws MQClientException, InterruptedException {
		DefaultMQProducer producer = new DefaultMQProducer("rmq-group");
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("rmq-instance");
//		producer.setVipChannelEnabled(false);// // 必须设为false否则连接broker10909端口
		producer.start();
		System.out.println("开始发送数据");
		try {
			for (int i = 0; i < 3; i++) {
				Message msg = new Message("test1", // topic
						"TagA", // tag
						(new Date() + "这里是一条消息" + i).getBytes()// body
				);
				SendResult sendResult = producer.send(msg);
				System.out.println("发送成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		producer.shutdown();
	}

}
