package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVMetadataTag extends Structure<AVMetadataTag, AVMetadataTag.ByValue, AVMetadataTag.ByReference > {
	/// C type : char*
	public Pointer key;
	/// C type : char*
	public Pointer value;
	public AVMetadataTag() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"key", "value"});
	}
	/**
	 * @param key C type : char*<br>
	 * @param value C type : char*
	 */
	public AVMetadataTag(Pointer key, Pointer value) {
		super();
		this.key = key;
		this.value = value;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVMetadataTag newInstance() { return new AVMetadataTag(); }
	public static AVMetadataTag[] newArray(int arrayLength) {
		return Structure.newArray(AVMetadataTag.class, arrayLength);
	}
	public static class ByReference extends AVMetadataTag implements Structure.ByReference {
		
	};
	public static class ByValue extends AVMetadataTag implements Structure.ByValue {
		
	};
}
