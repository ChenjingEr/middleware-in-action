package pro.jing.mq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author JING
 * @date 2018年8月31日
 * @describe
 */
public class ProducerWithQueue {

	public static void main(String[] args) {

		ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.10:61616");
		Connection con = null;
		Session session = null;
		Destination des = null;

		try {
			con = cf.createConnection();
			con.start();
			session = con.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			des = session.createQueue("queue-test");
			MessageProducer producer = session.createProducer(des);
			
			//非持久化，消费者延迟启动时可以接收到消息的，但是broker重启，就接收不到消息了
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			for (int i = 0; i < 3; i++) {
				TextMessage msg = session.createTextMessage("queue msg -> " + i);
				producer.send(msg);
				System.out.println("send to queue -> " + msg.getText());
			}
			session.commit();

		} catch (JMSException e) {
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
