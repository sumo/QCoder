package avformat;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVProgram extends Structure<AVProgram, AVProgram.ByValue, AVProgram.ByReference > {
	public int id;
	public int flags;
	/**
	 * @see avcodec.AvcodecLibrary#AVDiscard<br>
	 * < selects which program to discard and which to feed to the caller<br>
	 * C type : AVDiscard
	 */
	public int discard;
	/// C type : unsigned int*
	public IntByReference stream_index;
	public int nb_stream_indexes;
	/// C type : AVMetadata*
	public Pointer metadata;
	public AVProgram() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"id", "flags", "discard", "stream_index", "nb_stream_indexes", "metadata"});
	}
	/**
	 * @param discard @see avcodec.AvcodecLibrary#AVDiscard<br>
	 * < selects which program to discard and which to feed to the caller<br>
	 * C type : AVDiscard<br>
	 * @param stream_index C type : unsigned int*<br>
	 * @param metadata C type : AVMetadata*
	 */
	public AVProgram(int id, int flags, int discard, IntByReference stream_index, int nb_stream_indexes, Pointer metadata) {
		super();
		this.id = id;
		this.flags = flags;
		this.discard = discard;
		this.stream_index = stream_index;
		this.nb_stream_indexes = nb_stream_indexes;
		this.metadata = metadata;
		initFieldOrder();
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVProgram newInstance() { return new AVProgram(); }
	public static AVProgram[] newArray(int arrayLength) {
		return Structure.newArray(AVProgram.class, arrayLength);
	}
	public static class ByReference extends AVProgram implements Structure.ByReference {
		
	};
	public static class ByValue extends AVProgram implements Structure.ByValue {
		
	};
}
