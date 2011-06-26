package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVFrac extends Structure<AVFrac, AVFrac.ByValue, AVFrac.ByReference > {
	public long val;
	public long num;
	public long den;
	public AVFrac() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"val", "num", "den"});
	}
	public AVFrac(long val, long num, long den) {
		super();
		this.val = val;
		this.num = num;
		this.den = den;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVFrac newInstance() { return new AVFrac(); }
	public static AVFrac[] newArray(int arrayLength) {
		return Structure.newArray(AVFrac.class, arrayLength);
	}
	public static class ByReference extends AVFrac implements Structure.ByReference {
		
	};
	public static class ByValue extends AVFrac implements Structure.ByValue {
		
	};
}