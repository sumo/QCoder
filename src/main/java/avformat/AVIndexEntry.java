package avformat;
import com.ochafik.lang.jnaerator.runtime.Bits;
import com.ochafik.lang.jnaerator.runtime.Structure;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVIndexEntry extends Structure<AVIndexEntry, AVIndexEntry.ByValue, AVIndexEntry.ByReference > {
	public long pos;
	public long timestamp;
	@Bits(2) 
	public byte flags;
	/// Yeah, trying to keep the size of this small to reduce memory requirements (it is 24 vs. 32 bytes due to possible 8-byte alignment).
	@Bits(30) 
	public int size;
	/// < Minimum distance between this and the previous keyframe, used to avoid unneeded searching.
	public int min_distance;
	public AVIndexEntry() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"pos", "timestamp", "flags", "size", "min_distance"});
	}
	/**
	 * @param size Yeah, trying to keep the size of this small to reduce memory requirements (it is 24 vs. 32 bytes due to possible 8-byte alignment).<br>
	 * @param min_distance < Minimum distance between this and the previous keyframe, used to avoid unneeded searching.
	 */
	public AVIndexEntry(long pos, long timestamp, byte flags, int size, int min_distance) {
		super();
		this.pos = pos;
		this.timestamp = timestamp;
		this.flags = flags;
		this.size = size;
		this.min_distance = min_distance;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVIndexEntry newInstance() { return new AVIndexEntry(); }
	public static AVIndexEntry[] newArray(int arrayLength) {
		return Structure.newArray(AVIndexEntry.class, arrayLength);
	}
	public static class ByReference extends AVIndexEntry implements Structure.ByReference {
		
	};
	public static class ByValue extends AVIndexEntry implements Structure.ByValue {
		
	};
}