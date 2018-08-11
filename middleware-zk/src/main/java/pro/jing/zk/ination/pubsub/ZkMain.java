package pro.jing.zk.ination.pubsub;

public class ZkMain {

	public static void main(String[] args) {
		
		String conString = "192.168.1.46:2181";
		ZkConfigServer configServer = new ZkConfigServer(conString);
		ZkWorkServer workServer = new ZkWorkServer(conString);
		String path = "/config";
		byte[] data01 = "qiqi01".getBytes();
		configServer.create(path, data01);
		byte[] data02 = "qiqi02".getBytes();
		configServer.modify(path, data02);
		configServer.delete(path);
		
		
	}
}
