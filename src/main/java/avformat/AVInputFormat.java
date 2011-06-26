package avformat;
import avcodec.AVPacket;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVInputFormat extends Structure<AVInputFormat, AVInputFormat.ByValue, AVInputFormat.ByReference > {
	/// C type : const char*
	public String name;
	/**
	 * Descriptive name for the format, meant to be more human-readable<br>
	 * than name. You should use the NULL_IF_CONFIG_SMALL() macro<br>
	 * to define it.<br>
	 * C type : const char*
	 */
	public String long_name;
	/// Size of private data so that it can be allocated in the wrapper.
	public int priv_data_size;
	/**
	 * Tell if a given file has a chance of being parsed as this format.<br>
	 * The buffer provided is guaranteed to be AVPROBE_PADDING_SIZE bytes<br>
	 * big so you do not have to check for that unless you need more.<br>
	 * C type : read_probe_callback
	 */
	public AVInputFormat.read_probe_callback read_probe;
	/**
	 * Read the format header and initialize the AVFormatContext<br>
	 * structure. Return 0 if OK. 'ap' if non-NULL contains<br>
	 * additional parameters. Only used in raw format right<br>
	 * now. 'av_new_stream' should be called to create new streams.<br>
	 * C type : read_header_callback
	 */
	public AVInputFormat.read_header_callback read_header;
	/**
	 * Read one packet and put it in 'pkt'. pts and flags are also<br>
	 * set. 'av_new_stream' can be called only if the flag<br>
	 * AVFMTCTX_NOHEADER is used.<br>
	 * @return 0 on success, < 0 on error.<br>
	 * When returning an error, pkt must not have been allocated<br>
	 * or must be freed before returning<br>
	 * C type : read_packet_callback
	 */
	public AVInputFormat.read_packet_callback read_packet;
	/**
	 * Close the stream. The AVFormatContext and AVStreams are not<br>
	 * freed by this function<br>
	 * C type : read_close_callback
	 */
	public AVInputFormat.read_close_callback read_close;
	/**
	 * Seek to a given timestamp relative to the frames in<br>
	 * stream component stream_index.<br>
	 * @param stream_index Must not be -1.<br>
	 * @param flags Selects which direction should be preferred if no exact<br>
	 *              match is available.<br>
	 * @return >= 0 on success (but not necessarily the new offset)<br>
	 * C type : read_seek_callback
	 */
	public AVInputFormat.read_seek_callback read_seek;
	/**
	 * Gets the next timestamp in stream[stream_index].time_base units.<br>
	 * @return the timestamp or AV_NOPTS_VALUE if an error occurred<br>
	 * C type : read_timestamp_callback
	 */
	public AVInputFormat.read_timestamp_callback read_timestamp;
	/// Can use flags: AVFMT_NOFILE, AVFMT_NEEDNUMBER.
	public int flags;
	/**
	 * If extensions are defined, then no probe is done. You should<br>
	 * usually not use extension format guessing because it is not<br>
	 * reliable enough<br>
	 * C type : const char*
	 */
	public Pointer extensions;
	/// General purpose read-only value that the format can use.
	public int value;
	/**
	 * Starts/resumes playing - only meaningful if using a network-based format<br>
	 * (RTSP).<br>
	 * C type : read_play_callback
	 */
	public AVInputFormat.read_play_callback read_play;
	/**
	 * Pauses playing - only meaningful if using a network-based format<br>
	 * (RTSP).<br>
	 * C type : read_pause_callback
	 */
	public AVInputFormat.read_pause_callback read_pause;
	/// C type : AVCodecTag**
	public PointerByReference codec_tag;
	/**
	 * Seeks to timestamp ts.<br>
	 * Seeking will be done so that the point from which all active streams<br>
	 * can be presented successfully will be closest to ts and within min/max_ts.<br>
	 * Active streams are all streams that have AVStream.discard < AVDISCARD_ALL.<br>
	 * C type : read_seek2_callback
	 */
	public AVInputFormat.read_seek2_callback read_seek2;
	/// C type : const AVMetadataConv*
	public Pointer metadata_conv;
	/**
	 * private fields<br>
	 * C type : AVInputFormat*
	 */
	public Pointer next;
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_probe_callback extends Callback {
		int apply(AVProbeData AVProbeDataPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_header_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, AVFormatParameters ap);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_packet_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, AVPacket pkt);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_close_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_seek_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1, int stream_index, long timestamp, int flags);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_timestamp_callback extends Callback {
		long apply(AVFormatContext s, int stream_index, LongByReference pos, long pos_limit);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_play_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_pause_callback extends Callback {
		int apply(AVFormatContext AVFormatContextPtr1);
	};
	/// <i>native declaration : src/main/headers/libavformat/avformat.h</i>
	public interface read_seek2_callback extends Callback {
		int apply(AVFormatContext s, int stream_index, long min_ts, long ts, long max_ts, int flags);
	};
	public AVInputFormat() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"name", "long_name", "priv_data_size", "read_probe", "read_header", "read_packet", "read_close", "read_seek", "read_timestamp", "flags", "extensions", "value", "read_play", "read_pause", "codec_tag", "read_seek2", "metadata_conv", "next"});
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVInputFormat newInstance() { return new AVInputFormat(); }
	public static AVInputFormat[] newArray(int arrayLength) {
		return Structure.newArray(AVInputFormat.class, arrayLength);
	}
	public static class ByReference extends AVInputFormat implements Structure.ByReference {
		
	};
	public static class ByValue extends AVInputFormat implements Structure.ByValue {
		
	};
}
