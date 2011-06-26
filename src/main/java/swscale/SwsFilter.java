package swscale;
import com.ochafik.lang.jnaerator.runtime.Structure;
/**
 * <i>native declaration : src/main/headers/libswscale/swscale.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class SwsFilter extends Structure<SwsFilter, SwsFilter.ByValue, SwsFilter.ByReference > {
	/// C type : SwsVector*
	public swscale.SwsVector.ByReference lumH;
	/// C type : SwsVector*
	public swscale.SwsVector.ByReference lumV;
	/// C type : SwsVector*
	public swscale.SwsVector.ByReference chrH;
	/// C type : SwsVector*
	public swscale.SwsVector.ByReference chrV;
	public SwsFilter() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"lumH", "lumV", "chrH", "chrV"});
	}
	/**
	 * @param lumH C type : SwsVector*<br>
	 * @param lumV C type : SwsVector*<br>
	 * @param chrH C type : SwsVector*<br>
	 * @param chrV C type : SwsVector*
	 */
	public SwsFilter(swscale.SwsVector.ByReference lumH, swscale.SwsVector.ByReference lumV, swscale.SwsVector.ByReference chrH, swscale.SwsVector.ByReference chrV) {
		super();
		this.lumH = lumH;
		this.lumV = lumV;
		this.chrH = chrH;
		this.chrV = chrV;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected SwsFilter newInstance() { return new SwsFilter(); }
	public static SwsFilter[] newArray(int arrayLength) {
		return Structure.newArray(SwsFilter.class, arrayLength);
	}
	public static class ByReference extends SwsFilter implements Structure.ByReference {
		
	};
	public static class ByValue extends SwsFilter implements Structure.ByValue {
		
	};
}