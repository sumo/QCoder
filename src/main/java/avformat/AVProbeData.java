package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVProbeData extends Structure<AVProbeData, AVProbeData.ByValue, AVProbeData.ByReference > {
	/// C type : const char*
	public Pointer filename;
	/**
	 * < Buffer must have AVPROBE_PADDING_SIZE of extra allocated bytes filled with zero.<br>
	 * C type : unsigned char*
	 */
	public Pointer buf;
	/// < Size of buf except extra allocated bytes
	public int buf_size;
	public AVProbeData() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"filename", "buf", "buf_size"});
	}
	/**
	 * @param filename C type : const char*<br>
	 * @param buf < Buffer must have AVPROBE_PADDING_SIZE of extra allocated bytes filled with zero.<br>
	 * C type : unsigned char*<br>
	 * @param buf_size < Size of buf except extra allocated bytes
	 */
	public AVProbeData(Pointer filename, Pointer buf, int buf_size) {
		super();
		this.filename = filename;
		this.buf = buf;
		this.buf_size = buf_size;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVProbeData newInstance() { return new AVProbeData(); }
	public static AVProbeData[] newArray(int arrayLength) {
		return Structure.newArray(AVProbeData.class, arrayLength);
	}
	public static class ByReference extends AVProbeData implements Structure.ByReference {
		
	};
	public static class ByValue extends AVProbeData implements Structure.ByValue {
		
	};
}
