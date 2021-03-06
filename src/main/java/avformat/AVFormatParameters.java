package avformat;
import avutil.AVRational;
import com.ochafik.lang.jnaerator.runtime.Bits;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVFormatParameters extends Structure<AVFormatParameters, AVFormatParameters.ByValue, AVFormatParameters.ByReference > {
	/// C type : AVRational
	public AVRational time_base;
	public int sample_rate;
	public int channels;
	public int width;
	public int height;
	/**
	 * @see avcodec.AvcodecLibrary#PixelFormat<br>
	 * C type : PixelFormat
	 */
	public int pix_fmt;
	/// < Used to select DV channel.
	public int channel;
	/**
	 * < TV standard, NTSC, PAL, SECAM<br>
	 * C type : const char*
	 */
	public Pointer standard;
	/// < Force raw MPEG-2 transport stream output, if possible.
	@Bits(1) 
	public byte mpeg2ts_raw;
	/**
	 * < Compute exact PCR for each transport<br>
	 * stream packet (only meaningful if<br>
	 * mpeg2ts_raw is TRUE).
	 */
	@Bits(1) 
	public byte mpeg2ts_compute_pcr;
	/**
	 * < Do not begin to play the stream<br>
	 * immediately (RTSP only).
	 */
	@Bits(1) 
	public byte initial_pause;
	@Bits(1) 
	public byte prealloced_context;
	/**
	 * @see avcodec.AvcodecLibrary#CodecID<br>
	 * C type : CodecID
	 */
	public int video_codec_id;
	/**
	 * @see avcodec.AvcodecLibrary#CodecID<br>
	 * C type : CodecID
	 */
	public int audio_codec_id;
	public AVFormatParameters() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"time_base", "sample_rate", "channels", "width", "height", "pix_fmt", "channel", "standard", "mpeg2ts_raw", "mpeg2ts_compute_pcr", "initial_pause", "prealloced_context", "video_codec_id", "audio_codec_id"});
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVFormatParameters newInstance() { return new AVFormatParameters(); }
	public static AVFormatParameters[] newArray(int arrayLength) {
		return Structure.newArray(AVFormatParameters.class, arrayLength);
	}
	public static class ByReference extends AVFormatParameters implements Structure.ByReference {
		
	};
	public static class ByValue extends AVFormatParameters implements Structure.ByValue {
		
	};
}
