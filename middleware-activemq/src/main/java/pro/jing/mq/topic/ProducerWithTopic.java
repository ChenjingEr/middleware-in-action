package pro.jing.mq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ProducerWithTopic {

	public static void main(String[] args) {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.10:61616");
		Connection con = null;
		Session session = null;

		try {
			con = cf.createConnection();

			session = con.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Destination desc = session.createTopic("topic-test");
			MessageProducer producer = session.createProducer(desc);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			con.start();
			for (int i = 0; i < 3; i++) {
				TextMessage msg = session.createTextMessage("msg -> " + i);
				producer.send(msg);
				System.out.println("send to topic -> " + msg.getText());
			}
			session.commit();

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
