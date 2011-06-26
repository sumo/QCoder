package avformat;
import avcodec.AVPacket;
import avutil.AVRational;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : src/main/headers/libavformat/avformat.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class AVStream extends Structure<AVStream, AVStream.ByValue, AVStream.ByReference > {
	/// < stream index in AVFormatContext
	public int index;
	/// < format-specific stream ID
	public int id;
	/**
	 * < codec context<br>
	 * C type : AVCodecContext*
	 */
	public avcodec.AVCodecContext.ByReference codec;
	/**
	 * Real base framerate of the stream.<br>
	 * This is the lowest framerate with which all timestamps can be<br>
	 * represented accurately (it is the least common multiple of all<br>
	 * framerates in the stream). Note, this value is just a guess!<br>
	 * For example, if the time base is 1/90000 and all frames have either<br>
	 * approximately 3600 or 1800 timer ticks, then r_frame_rate will be 50/1.<br>
	 * C type : AVRational
	 */
	public AVRational r_frame_rate;
	/// C type : void*
	public Pointer priv_data;
	/// internal data used in av_find_stream_info()
	public long first_dts;
	/**
	 * encoding: pts generation when outputting stream<br>
	 * C type : AVFrac
	 */
	public AVFrac pts;
	/**
	 * This is the fundamental unit of time (in seconds) in terms<br>
	 * of which frame timestamps are represented. For fixed-fps content,<br>
	 * time base should be 1/framerate and timestamp increments should be 1.<br>
	 * C type : AVRational
	 */
	public AVRational time_base;
	/// < number of bits in pts (used for wrapping control)
	public int pts_wrap_bits;
	/**
	 * ffmpeg.c private use<br>
	 * < If set, just copy stream.
	 */
	public int stream_copy;
	/**
	 * @see avcodec.AvcodecLibrary#AVDiscard<br>
	 * < Selects which packets can be discarded at will and do not need to be demuxed.<br>
	 * C type : AVDiscard
	 */
	public int discard;
	/**
	 * Quality, as it has been removed from AVCodecContext and put in AVVideoFrame.<br>
	 * MN: dunno if that is the right place for it
	 */
	public float quality;
	/**
	 * Decoding: pts of the first frame of the stream, in stream time base.<br>
	 * Only set this if you are absolutely 100% sure that the value you set<br>
	 * it to really is the pts of the first frame.<br>
	 * This may be undefined (AV_NOPTS_VALUE).<br>
	 * @note The ASF header does NOT contain a correct start_time the ASF<br>
	 * demuxer must NOT set this.
	 */
	public long start_time;
	/**
	 * Decoding: duration of the stream, in stream time base.<br>
	 * If a source file does not specify a duration, but does specify<br>
	 * a bitrate, this value will be estimated from bitrate and file size.
	 */
	public long duration;
	/**
	 * ISO 639-2/B 3-letter language code (empty string if undefined)<br>
	 * C type : char[4]
	 */
	public byte[] language = new byte[(4)];
	/**
	 * av_read_frame() support<br>
	 * @see AVStreamParseType<br>
	 * C type : AVStreamParseType
	 */
	public int need_parsing;
	/// C type : AVCodecParserContext*
	public avcodec.AVCodecParserContext.ByReference parser;
	public long cur_dts;
	public int last_IP_duration;
	public long last_IP_pts;
	/**
	 * av_seek_frame() support<br>
	 * < Only used if the format does not<br>
	 * support seeking natively.<br>
	 * C type : AVIndexEntry*
	 */
	public avformat.AVIndexEntry.ByReference index_entries;
	public int nb_index_entries;
	public int index_entries_allocated_size;
	/// < number of frames in this stream if known or 0
	public long nb_frames;
	/// C type : int64_t[4 + 1]
	public long[] unused = new long[(4 + 1)];
	/**
	 * < source filename of the stream<br>
	 * C type : char*
	 */
	public Pointer filename;
	/// < AV_DISPOSITION_* bit field
	public int disposition;
	/// C type : AVProbeData
	public AVProbeData probe_data;
	/// C type : int64_t[16 + 1]
	public long[] pts_buffer = new long[(16 + 1)];
	/**
	 * sample aspect ratio (0 if unknown)<br>
	 * - encoding: Set by user.<br>
	 * - decoding: Set by libavformat.<br>
	 * C type : AVRational
	 */
	public AVRational sample_aspect_ratio;
	/// C type : AVMetadata*
	public avformat.AVMetadata.ByReference metadata;
	/**
	 * av_read_frame() support<br>
	 * C type : const uint8_t*
	 */
	public Pointer cur_ptr;
	public int cur_len;
	/// C type : AVPacket
	public AVPacket cur_pkt;
	/**
	 * Timestamp corresponding to the last dts sync point.<br>
	 * * Initialized when AVCodecParserContext.dts_sync_point >= 0 and<br>
	 * a DTS is received from the underlying container. Otherwise set to<br>
	 * AV_NOPTS_VALUE by default.
	 */
	public long reference_dts;
	public int probe_packets;
	/**
	 * last packet in packet_buffer for this stream when muxing.<br>
	 * used internally, NOT PART OF PUBLIC API, dont read or write from outside of libav*<br>
	 * C type : AVPacketList*
	 */
	public avformat.AVPacketList.ByReference last_in_packet_buffer;
	/**
	 * Average framerate<br>
	 * C type : AVRational
	 */
	public AVRational avg_frame_rate;
	/// Number of frames that have been demuxed during av_find_stream_info()
	public int codec_info_nb_frames;
	public AVStream() {
		super();
		initFieldOrder();
	}
	protected void initFieldOrder() {
		setFieldOrder(new java.lang.String[]{"index", "id", "codec", "r_frame_rate", "priv_data", "first_dts", "pts", "time_base", "pts_wrap_bits", "stream_copy", "discard", "quality", "start_time", "duration", "language", "need_parsing", "parser", "cur_dts", "last_IP_duration", "last_IP_pts", "index_entries", "nb_index_entries", "index_entries_allocated_size", "nb_frames", "unused", "filename", "disposition", "probe_data", "pts_buffer", "sample_aspect_ratio", "metadata", "cur_ptr", "cur_len", "cur_pkt", "reference_dts", "probe_packets", "last_in_packet_buffer", "avg_frame_rate", "codec_info_nb_frames"});
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected AVStream newInstance() { return new AVStream(); }
	public static AVStream[] newArray(int arrayLength) {
		return Structure.newArray(AVStream.class, arrayLength);
	}
	public static class ByReference extends AVStream implements Structure.ByReference {
		
	};
	public static class ByValue extends AVStream implements Structure.ByValue {
		
	};
}