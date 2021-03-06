package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavformat/avio.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class URLContext extends Structure<URLContext, URLContext.ByValue, URLContext.ByReference > {
	/// C type : URLProtocol*
	public avformat.URLProtocol.ByReference prot;
	public int flags;
	/// < true if streamed (no seek possible), default = false
	public int is_streamed;
	/// < if non zero, the stream is packetized with this max packet size
	public int max_packet_size;
	/// C type : void*
	public Pointer priv_data;
	/**
	 * < specified URL<br>
	 * C type : char*
	 */
	public Pointer filename;
	public URLContext() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"prot", "flags", "is_streamed", "max_packet_size", "priv_data", "filename"});
	}
	/**
	 * @param prot C type : URLProtocol*<br>
	 * @param is_streamed < true if streamed (no seek possible), default = false<br>
	 * @param max_packet_size < if non zero, the stream is packetized with this max packet size<br>
	 * @param priv_data C type : void*<br>
	 * @param filename < specified URL<br>
	 * C type : char*
	 */
	public URLContext(avformat.URLProtocol.ByReference prot, int flags, int is_streamed, int max_packet_size, Pointer priv_data, Pointer filename) {
		super();
		this.prot = prot;
		this.flags = flags;
		this.is_streamed = is_streamed;
		this.max_packet_size = max_packet_size;
		this.priv_data = priv_data;
		this.filename = filename;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected URLContext newInstance() { return new URLContext(); }
	public static URLContext[] newArray(int arrayLength) {
		return Structure.newArray(URLContext.class, arrayLength);
	}
	public static class ByReference extends URLContext implements Structure.ByReference {
		
	};
	public static class ByValue extends URLContext implements Structure.ByValue {
		
	};
}
