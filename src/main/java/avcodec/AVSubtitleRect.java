package avcodec;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavcodec/avcodec.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVSubtitleRect extends Structure<AVSubtitleRect, AVSubtitleRect.ByValue, AVSubtitleRect.ByReference > {
	/// < top left corner  of pict, undefined when pict is not set
	public int x;
	/// < top left corner  of pict, undefined when pict is not set
	public int y;
	/// < width            of pict, undefined when pict is not set
	public int w;
	/// < height           of pict, undefined when pict is not set
	public int h;
	/// < number of colors in pict, undefined when pict is not set
	public int nb_colors;
	/**
	 * data+linesize for the bitmap of this subtitle.<br>
	 * can be set for text/ass as well once they where rendered<br>
	 * C type : AVPicture
	 */
	public AVPicture pict;
	/**
	 * @see AVSubtitleType<br>
	 * C type : AVSubtitleType
	 */
	public int type;
	/**
	 * < 0 terminated plain UTF-8 text<br>
	 * C type : char*
	 */
	public Pointer text;
	/**
	 * 0 terminated ASS/SSA compatible event line.<br>
	 * The pressentation of this is unaffected by the other values in this<br>
	 * struct.<br>
	 * C type : char*
	 */
	public Pointer ass;
	public AVSubtitleRect() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"x", "y", "w", "h", "nb_colors", "pict", "type", "text", "ass"});
	}
	/**
	 * @param x < top left corner  of pict, undefined when pict is not set<br>
	 * @param y < top left corner  of pict, undefined when pict is not set<br>
	 * @param w < width            of pict, undefined when pict is not set<br>
	 * @param h < height           of pict, undefined when pict is not set<br>
	 * @param nb_colors < number of colors in pict, undefined when pict is not set<br>
	 * @param pict data+linesize for the bitmap of this subtitle.<br>
	 * can be set for text/ass as well once they where rendered<br>
	 * C type : AVPicture<br>
	 * @param type @see AVSubtitleType<br>
	 * C type : AVSubtitleType<br>
	 * @param text < 0 terminated plain UTF-8 text<br>
	 * C type : char*<br>
	 * @param ass 0 terminated ASS/SSA compatible event line.<br>
	 * The pressentation of this is unaffected by the other values in this<br>
	 * struct.<br>
	 * C type : char*
	 */
	public AVSubtitleRect(int x, int y, int w, int h, int nb_colors, AVPicture pict, int type, Pointer text, Pointer ass) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.nb_colors = nb_colors;
		this.pict = pict;
		this.type = type;
		this.text = text;
		this.ass = ass;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVSubtitleRect newInstance() { return new AVSubtitleRect(); }
	public static AVSubtitleRect[] newArray(int arrayLength) {
		return Structure.newArray(AVSubtitleRect.class, arrayLength);
	}
	public static class ByReference extends AVSubtitleRect implements Structure.ByReference {
		
	};
	public static class ByValue extends AVSubtitleRect implements Structure.ByValue {
		
	};
}