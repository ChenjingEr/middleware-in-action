package pro.jing.zk.api.jute;

import java.io.IOException;

import org.apache.jute.InputArchive;
import org.apache.jute.OutputArchive;
import org.apache.jute.Record;

/**
 * @author JING
 * @Date 2018年8月11日
 * @description 序列化
 */
public class MockReqHandler implements Record {

	private long sessionId;
	private String type;

	public MockReqHandler() {
		// TODO Auto-generated constructor stub
	}

	public MockReqHandler(long sessionId, String type) {
		super();
		this.sessionId = sessionId;
		this.type = type;
	}

	@Override
	public void serialize(OutputArchive archive, String tag) throws IOException {
		archive.startRecord(this, tag);
		archive.writeLong(sessionId, "sessionId");
		archive.writeString(type, "type");
		archive.endRecord(this, tag);
		
	}

	@Override
	public void deserialize(InputArchive archive, String tag) throws IOException {

		archive.startRecord(tag);
		this.sessionId = archive.readLong("sessionId");
		this.type = archive.readString("type");
		archive.endRecord(tag);
	}

}
