package pro.jing.mq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ComsumerWitherTopic {

	public static void main(String[] args) {

		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.10:61616");
		Connection con = null;
		Session session = null;

		try {
			con = cf.createConnection();
			con.setClientID("client01");

			session = con.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("topic-test");
			TopicSubscriber sub = session.createDurableSubscriber(topic, "tic");
			
			con.start();
			while (true) {
				TextMessage receive = (TextMessage) sub.receive();
				System.out.println("receive from topic -> " + receive.getText());
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null)
				try {
					session.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			if (con != null)
				try {
					con.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

	}
}
