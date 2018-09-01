package pro.jing.mq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author JING
 * @date 2018年8月31日
 * @describe
 */
public class ConsumerWithQueue {

	public static void main(String[] args) {
		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.10:61616");
		Connection con = null;
		Session session = null;
		try {
			con = cf.createConnection();
			con.start();
			session = con.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			Destination desc = session.createQueue("queue-test");
			MessageConsumer consumer = session.createConsumer(desc);
			while (true) {
				TextMessage msg = (TextMessage) consumer.receive();
				System.out.println("receive from ->" + msg.getText());
				session.commit();
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
