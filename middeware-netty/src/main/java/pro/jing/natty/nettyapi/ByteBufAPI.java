package pro.jing.natty.nettyapi;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author JING
 * @date 2018年9月9日
 * @describe Netty ByteBuf API
 */
public class ByteBufAPI {

	public static void main(String[] args) {
		
		System.out.println("************顺序写****************");
		ByteBuf buf = Unpooled.buffer(20);
		System.out.println("申请一个buf -> " + buf );
		System.out.println("*************顺序读，写***************");
		buf.writeByte('1');
		System.out.println("顺序写  ->" + buf);
		buf.writeByte('2');
		System.out.println("顺序写  ->" + buf);
		buf.writeByte('3');
		System.out.println("顺序写  ->" + buf);
		System.out.println("顺序读  -> " + buf.readByte());
		System.out.println("顺序读 -> " + buf.readByte());
		System.out.println("顺序读 -> " + buf.readByte());
		System.out.println("***************释放*********************");
		System.out.println("释放前 -> " + buf);
		//discard会引起内存复制
		buf.discardReadBytes();
		System.out.println("释放后 -> " + buf);
		
		System.out.println("*************clear*****************");
		buf.writeBytes(new byte[] {'4','5','6','7'});
		System.out.println("clear之前 -> " + buf);
		buf.clear();
		System.out.println("clear之后 -> " + buf);
		System.out.println("****************mark VS rest***********************");
		buf.writeBytes(new byte[] {'4','5','6','7'});
		buf.readByte();
		buf.readByte();
		System.out.println("mark 之前 -> " + buf);
		buf.markReaderIndex();
		buf.readByte();
		System.out.println("mark之后，继续读 -> " + buf);
		buf.resetReaderIndex();
		System.out.println("rest -> " + buf);
		
		System.out.println("*****************复制************************");
		//duplicate共享内存，独立维护readIndex,writeIndex
		System.out.println("duplicate之前-> " + buf);
		ByteBuf duplicate = buf.duplicate();
		System.out.println("duplicate之后-> " + duplicate);
		//copy 独立维护内存，独立维护readIndex,writeIndex
		//slice 子缓冲区,共享子缓存，独立维护readIndex,writeIndex
		System.out.println("*****************ByteBuf -> ByteBuffer***************************");
		//共享内存，ByteBuffer无法感知ByteBuf动态扩展
		ByteBuffer nioBuffer = buf.nioBuffer();
		System.out.println("***********************随机读写 get VS set********************************");
		
	}
}
