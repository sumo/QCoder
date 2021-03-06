package avformat;
import avcodec.AVPacket;

import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVOutputFormat extends Structure<AVOutputFormat, AVOutputFormat.ByValue, AVOutputFormat.ByReference > {
	/// C type : const char*
	public String name;
	/**
	 * Descriptive name for the format, meant to be more human-readable<br>
	 * than name. You should use the NULL_IF_CONFIG_SMALL() macro<br>
	 * to define it.<br>
	 * C type : const char*
	 */
	public String long_name;
	/// C type : const char*
	public String mime_type;
	/**
	 * < comma-separated filename extensions<br>
	 * C type : const char*
	 */
	public String extensions;
	/// size of private data so that it can be allocated in the wrapper
	public int priv_data_size;
	/**
	 * output support<br>
	 * @see avcodec.AvcodecLibrary#CodecID<br>
	 * < default audio codec<br>
	 * C type : CodecID
	 */
	public int audio_codec;
	/**
	 * @see avcodec.AvcodecLibrary#CodecID<br>
	 * < default video codec<br>
	 * C type : CodecID
	 */
	public int video_codec;
	/// C type : write_header_callback
	public AVOutputFormat.write_header_callback write_header;
	/// C type : write_packet_callback
	public avformat.ByteIOContext.write_packet_callback write_packet;
	/// C type : write_trailer_callback
	public AVOutputFormat.write_trailer_callback write_trailer;
	/// can use flags: AVFMT_NOFILE, AVFMT_NEEDNUMBER, AVFMT_GLOBALHEADER
	public int flags;
	/**
	 * Currently only used to set pixel format if not YUV420P.<br>
	 * C type : set_parameters_callback
	 */
	public AVOutputFormat.set_parameters_callback set_parameters;
	/// C type : interleave_packet_callback
	public AVOutputFormat.interleave_packet_callback interleave_packet;
	/**
	 * List of supported codec_id-codec_tag pairs, ordered by "better<br>
	 * choice first". The arrays are all terminated by CODEC_ID_NONE.<br>
	 * C type : AVCodecTag**
	 */
	public PointerByReference codec_tag;
	/**
	 * @see avcodec.AvcodecLibrary#CodecID<br>
	 * < default subtitle codec<br>
	 * C type : CodecID
	 */
	public int subtitle_codec;
	/// C type : const AVMetadataConv*
	public Pointer metadata_conv;
	/**
	 * private fields<br>
	 * C type : AVOutputFormat*
	 */
	public Pointer next;
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface write_header_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface write_packet_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, AVPacket pkt);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface write_trailer_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface set_parameters_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, AVFormatParameters AVFormatParametersPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface interleave_packet_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, AVPacket out, AVPacket in, int flush);
	};
	public AVOutputFormat() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"name", "long_name", "mime_type", "extensions", "priv_data_size", "audio_codec", "video_codec", "write_header", "write_packet", "write_trailer", "flags", "set_parameters", "interleave_packet", "codec_tag", "subtitle_codec", "metadata_conv", "next"});
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVOutputFormat newInstance() { return new AVOutputFormat(); }
	public static AVOutputFormat[] newArray(int arrayLength) {
		return Structure.newArray(AVOutputFormat.class, arrayLength);
	}
	public static class ByReference extends AVOutputFormat implements Structure.ByReference {
		
	};
	public static class ByValue extends AVOutputFormat implements Structure.ByValue {
		
	};
}
