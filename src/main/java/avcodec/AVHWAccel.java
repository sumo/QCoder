package avcodec;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavcodec/avcodec.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVHWAccel extends Structure<AVHWAccel, AVHWAccel.ByValue, AVHWAccel.ByReference > {
	/**
	 * Name of the hardware accelerated codec.<br>
	 * The name is globally unique among encoders and among decoders (but an<br>
	 * encoder and a decoder can share the same name).<br>
	 * C type : const char*
	 */
	public Pointer name;
	/**
	 * Type of codec implemented by the hardware accelerator.<br>
	 * * See AVMEDIA_TYPE_xxx<br>
	 * @see AVMediaType<br>
	 * C type : AVMediaType
	 */
	public int type;
	/**
	 * Codec implemented by the hardware accelerator.<br>
	 * * See CODEC_ID_xxx<br>
	 * @see CodecID<br>
	 * C type : CodecID
	 */
	public int id;
	/**
	 * Supported pixel format.<br>
	 * * Only hardware accelerated formats are supported here.<br>
	 * @see PixelFormat<br>
	 * C type : PixelFormat
	 */
	public int pix_fmt;
	/**
	 * Hardware accelerated codec capabilities.<br>
	 * see FF_HWACCEL_CODEC_CAP_*
	 */
	public int capabilities;
	/// C type : AVHWAccel*
	public AVHWAccel.ByReference next;
	/**
	 * Called at the beginning of each frame or field picture.<br>
	 * * Meaningful frame information (codec specific) is guaranteed to<br>
	 * be parsed at this point. This function is mandatory.<br>
	 * * Note that buf can be NULL along with buf_size set to 0.<br>
	 * Otherwise, this means the whole frame is available at this point.<br>
	 * * @param avctx the codec context<br>
	 * @param buf the frame data buffer base<br>
	 * @param buf_size the size of the frame in bytes<br>
	 * @return zero if successful, a negative value otherwise<br>
	 * C type : start_frame_callback
	 */
	public AVHWAccel.start_frame_callback start_frame;
	/**
	 * Callback for each slice.<br>
	 * * Meaningful slice information (codec specific) is guaranteed to<br>
	 * be parsed at this point. This function is mandatory.<br>
	 * * @param avctx the codec context<br>
	 * @param buf the slice data buffer base<br>
	 * @param buf_size the size of the slice in bytes<br>
	 * @return zero if successful, a negative value otherwise<br>
	 * C type : decode_slice_callback
	 */
	public AVHWAccel.decode_slice_callback decode_slice;
	/**
	 * Called at the end of each frame or field picture.<br>
	 * * The whole picture is parsed at this point and can now be sent<br>
	 * to the hardware accelerator. This function is mandatory.<br>
	 * * @param avctx the codec context<br>
	 * @return zero if successful, a negative value otherwise<br>
	 * C type : end_frame_callback
	 */
	public AVHWAccel.end_frame_callback end_frame;
	/**
	 * Size of HW accelerator private data.<br>
	 * * Private data is allocated with av_mallocz() before<br>
	 * AVCodecContext.get_buffer() and deallocated after<br>
	 * AVCodecContext.release_buffer().
	 */
	public int priv_data_size;
	/// <i>native declaration : src/main/headers/libavcodec/avcodec.h</i>
	public interface start_frame_callback extends Callback {
		int apply(AVCodecContext avctx, Pointer buf, int buf_size);
	};
	/// <i>native declaration : src/main/headers/libavcodec/avcodec.h</i>
	public interface decode_slice_callback extends Callback {
		int apply(AVCodecContext avctx, Pointer buf, int buf_size);
	};
	/// <i>native declaration : src/main/headers/libavcodec/avcodec.h</i>
	public interface end_frame_callback extends Callback {
		int apply(AVCodecContext avctx);
	};
	public AVHWAccel() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"name", "type", "id", "pix_fmt", "capabilities", "next", "start_frame", "decode_slice", "end_frame", "priv_data_size"});
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVHWAccel newInstance() { return new AVHWAccel(); }
	public static AVHWAccel[] newArray(int arrayLength) {
		return Structure.newArray(AVHWAccel.class, arrayLength);
	}
	public static class ByReference extends AVHWAccel implements Structure.ByReference {
		
	};
	public static class ByValue extends AVHWAccel implements Structure.ByValue {
		
	};
}