package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
/**
 * <i>native declaration : src/main/headers/libavformat/avio.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class URLPollEntry extends Structure<URLPollEntry, URLPollEntry.ByValue, URLPollEntry.ByReference > {
	/// C type : URLContext*
	public avformat.URLContext.ByReference handle;
	public int events;
	public int revents;
	public URLPollEntry() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"handle", "events", "revents"});
	}
	/// @param handle C type : URLContext*
	public URLPollEntry(avformat.URLContext.ByReference handle, int events, int revents) {
		super();
		this.handle = handle;
		this.events = events;
		this.revents = revents;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected URLPollEntry newInstance() { return new URLPollEntry(); }
	public static URLPollEntry[] newArray(int arrayLength) {
		return Structure.newArray(URLPollEntry.class, arrayLength);
	}
	public static class ByReference extends URLPollEntry implements Structure.ByReference {
		
	};
	public static class ByValue extends URLPollEntry implements Structure.ByValue {
		
	};
}