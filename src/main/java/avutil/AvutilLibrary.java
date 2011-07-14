package avutil;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.ochafik.lang.jnaerator.runtime.LibraryExtractor;
import com.ochafik.lang.jnaerator.runtime.Mangling;
import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
/**
 * JNA Wrapper for library <b>avutil</b><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public interface AvutilLibrary extends Library {
	public static final String JNA_LIBRARY_NAME = LibraryExtractor.getLibraryPath("avutil", true, avutil.AvutilLibrary.class);
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(avutil.AvutilLibrary.JNA_LIBRARY_NAME, com.ochafik.lang.jnaerator.runtime.MangledFunctionMapper.DEFAULT_OPTIONS);
	public static final AvutilLibrary INSTANCE = (AvutilLibrary)Native.loadLibrary(avutil.AvutilLibrary.JNA_LIBRARY_NAME, avutil.AvutilLibrary.class, com.ochafik.lang.jnaerator.runtime.MangledFunctionMapper.DEFAULT_OPTIONS);
	/**
	 * Conversion Error : a.num<br>
	 * SKIPPED:<br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h</i><br>
	 * const int64_t tmp = a.num * (int64_t)b.den - b.num * (int64_t)a.den;
	 */
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_PANIC = 0;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_DEBUG = 48;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_WARNING = 24;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_VERBOSE = 40;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_INFO = 32;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_QUIET = -8;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_ERROR = 16;
	/// <i>native declaration : src/main/headers/libavutil/log.h</i>
	public static final int AV_LOG_FATAL = 8;
	/// <i>native declaration : src/main/headers/libavutil/log.h:119</i>
	public interface av_log_set_callback_arg1_callback extends Callback {
		void apply(Pointer voidPtr1, int int1, Pointer charPtr1);
	};
	/**
	 * Reduces a fraction.<br>
	 * This is useful for framerate calculations.<br>
	 * @param dst_num destination numerator<br>
	 * @param dst_den destination denominator<br>
	 * @param num source numerator<br>
	 * @param den source denominator<br>
	 * @param max the maximum allowed for dst_num & dst_den<br>
	 * @return 1 if exact, 0 otherwise<br>
	 * Original signature : <code>int av_reduce(int*, int*, int64_t, int64_t, int64_t)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:73</i><br>
	 * @deprecated use the safer methods {@link #av_reduce(java.nio.IntBuffer, java.nio.IntBuffer, long, long, long)} and {@link #av_reduce(com.sun.jna.ptr.IntByReference, com.sun.jna.ptr.IntByReference, long, long, long)} instead
	 */
	@Mangling({"_Z9av_reducePiPi7int64_t7int64_t7int64_t", "?av_reduce@@YAHPAHPAH7int64_t7int64_t7int64_t@Z"}) 
	@Deprecated 
	int av_reduce(IntByReference dst_num, IntByReference dst_den, long num, long den, long max);
	/**
	 * Reduces a fraction.<br>
	 * This is useful for framerate calculations.<br>
	 * @param dst_num destination numerator<br>
	 * @param dst_den destination denominator<br>
	 * @param num source numerator<br>
	 * @param den source denominator<br>
	 * @param max the maximum allowed for dst_num & dst_den<br>
	 * @return 1 if exact, 0 otherwise<br>
	 * Original signature : <code>int av_reduce(int*, int*, int64_t, int64_t, int64_t)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:73</i>
	 */
	@Mangling({"_Z9av_reducePiPi7int64_t7int64_t7int64_t", "?av_reduce@@YAHPAHPAH7int64_t7int64_t7int64_t@Z"}) 
	int av_reduce(IntBuffer dst_num, IntBuffer dst_den, long num, long den, long max);
	/**
	 * Multiplies two rationals.<br>
	 * @param b first rational<br>
	 * @param c second rational<br>
	 * @return b*c<br>
	 * Original signature : <code>AVRational av_mul_q(AVRational, AVRational)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:81</i>
	 */
	@Mangling({"_Z8av_mul_q10AVRational10AVRational", "?av_mul_q@@YAUAVRational@@UAVRational@@UAVRational@@@Z"}) 
	AVRational.ByValue av_mul_q(AVRational.ByValue b, AVRational.ByValue c);
	/**
	 * Divides one rational by another.<br>
	 * @param b first rational<br>
	 * @param c second rational<br>
	 * @return b/c<br>
	 * Original signature : <code>AVRational av_div_q(AVRational, AVRational)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:89</i>
	 */
	@Mangling({"_Z8av_div_q10AVRational10AVRational", "?av_div_q@@YAUAVRational@@UAVRational@@UAVRational@@@Z"}) 
	AVRational.ByValue av_div_q(AVRational.ByValue b, AVRational.ByValue c);
	/**
	 * Adds two rationals.<br>
	 * @param b first rational<br>
	 * @param c second rational<br>
	 * @return b+c<br>
	 * Original signature : <code>AVRational av_add_q(AVRational, AVRational)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:97</i>
	 */
	@Mangling({"_Z8av_add_q10AVRational10AVRational", "?av_add_q@@YAUAVRational@@UAVRational@@UAVRational@@@Z"}) 
	AVRational.ByValue av_add_q(AVRational.ByValue b, AVRational.ByValue c);
	/**
	 * Subtracts one rational from another.<br>
	 * @param b first rational<br>
	 * @param c second rational<br>
	 * @return b-c<br>
	 * Original signature : <code>AVRational av_sub_q(AVRational, AVRational)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:105</i>
	 */
	@Mangling({"_Z8av_sub_q10AVRational10AVRational", "?av_sub_q@@YAUAVRational@@UAVRational@@UAVRational@@@Z"}) 
	AVRational.ByValue av_sub_q(AVRational.ByValue b, AVRational.ByValue c);
	/**
	 * Converts a double precision floating point number to a rational.<br>
	 * @param d double to convert<br>
	 * @param max the maximum allowed numerator and denominator<br>
	 * @return (AVRational) d<br>
	 * Original signature : <code>AVRational av_d2q(double, int)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:113</i>
	 */
	@Mangling({"_Z6av_d2qdi", "?av_d2q@@YAUAVRational@@NH@Z"}) 
	AVRational.ByValue av_d2q(double d, int max);
	/**
	 * @return 1 if q1 is nearer to q than q2, -1 if q2 is nearer<br>
	 * than q1, 0 if they have the same distance.<br>
	 * Original signature : <code>int av_nearer_q(AVRational, AVRational, AVRational)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:119</i>
	 */
	@Mangling({"_Z11av_nearer_q10AVRational10AVRational10AVRational", "?av_nearer_q@@YAHUAVRational@@UAVRational@@UAVRational@@@Z"}) 
	int av_nearer_q(AVRational.ByValue q, AVRational.ByValue q1, AVRational.ByValue q2);
	/**
	 * Finds the nearest value in q_list to q.<br>
	 * @param q_list an array of rationals terminated by {0, 0}<br>
	 * @return the index of the nearest value found in the array<br>
	 * Original signature : <code>int av_find_nearest_q_idx(AVRational, const AVRational*)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/rational.h:126</i>
	 */
	@Mangling({"_Z21av_find_nearest_q_idx10AVRationalPK10AVRational", "?av_find_nearest_q_idx@@YAHUAVRational@@PAUAVRational@@@Z"}) 
	int av_find_nearest_q_idx(AVRational.ByValue q, AVRational q_list);
	/**
	 * Puts a description of the AVERROR code errnum in errbuf.<br>
	 * In case of failure the global variable errno is set to indicate the<br>
	 * error. Even in case of failure av_strerror() will print a generic<br>
	 * error message indicating the errnum provided to errbuf.<br>
	 * * @param errbuf_size the size in bytes of errbuf<br>
	 * @return 0 on success, a negative value if a description for errnum<br>
	 * cannot be found<br>
	 * Original signature : <code>int av_strerror(int, char*, size_t)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/error.h:69</i><br>
	 * @deprecated use the safer methods {@link #av_strerror(int, java.nio.ByteBuffer, com.ochafik.lang.jnaerator.runtime.NativeSize)} and {@link #av_strerror(int, com.sun.jna.Pointer, com.ochafik.lang.jnaerator.runtime.NativeSize)} instead
	 */
	@Mangling({"_Z11av_strerroriPc6size_t", "?av_strerror@@YAHHPAD6size_t@Z"}) 
	@Deprecated 
	int av_strerror(int errnum, Pointer errbuf, NativeSize errbuf_size);
	/**
	 * Puts a description of the AVERROR code errnum in errbuf.<br>
	 * In case of failure the global variable errno is set to indicate the<br>
	 * error. Even in case of failure av_strerror() will print a generic<br>
	 * error message indicating the errnum provided to errbuf.<br>
	 * * @param errbuf_size the size in bytes of errbuf<br>
	 * @return 0 on success, a negative value if a description for errnum<br>
	 * cannot be found<br>
	 * Original signature : <code>int av_strerror(int, char*, size_t)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/error.h:69</i>
	 */
	@Mangling({"_Z11av_strerroriPc6size_t", "?av_strerror@@YAHHPAD6size_t@Z"}) 
	int av_strerror(int errnum, ByteBuffer errbuf, NativeSize errbuf_size);
	/**
	 * Original signature : <code>void av_log(void*, int, const char*, null)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:111</i><br>
	 * @deprecated use the safer methods {@link #av_log(com.sun.jna.Pointer, int, java.lang.String, java.lang.Object)} and {@link #av_log(com.sun.jna.Pointer, int, com.sun.jna.Pointer, java.lang.Object)} instead
	 */
	@Mangling({"_Z6av_logPviPKcv", "?av_log@@YAXPAXHPADX@Z"}) 
	@Deprecated 
	void av_log(Pointer voidPtr1, int level, Pointer fmt, Object... varargs);
	/**
	 * Original signature : <code>void av_log(void*, int, const char*, null)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:111</i>
	 */
	@Mangling({"_Z6av_logPviPKcv", "?av_log@@YAXPAXHPADX@Z"}) 
	void av_log(Pointer voidPtr1, int level, String fmt, Object... varargs);
	/**
	 * Original signature : <code>void av_vlog(void*, int, const char*)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:116</i><br>
	 * @deprecated use the safer methods {@link #av_vlog(com.sun.jna.Pointer, int, java.lang.String)} and {@link #av_vlog(com.sun.jna.Pointer, int, com.sun.jna.Pointer)} instead
	 */
	@Mangling({"_Z7av_vlogPviPKc", "?av_vlog@@YAXPAXHPAD@Z"}) 
	@Deprecated 
	void av_vlog(Pointer voidPtr1, int level, Pointer fmt);
	/**
	 * Original signature : <code>void av_vlog(void*, int, const char*)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:116</i>
	 */
	@Mangling({"_Z7av_vlogPviPKc", "?av_vlog@@YAXPAXHPAD@Z"}) 
	void av_vlog(Pointer voidPtr1, int level, String fmt);
	/**
	 * Original signature : <code>int av_log_get_level()</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:117</i>
	 */
	@Mangling({"_Z16av_log_get_levelv", "?av_log_get_level@@YAHXZ"}) 
	int av_log_get_level();
	/**
	 * Original signature : <code>void av_log_set_level(int)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:118</i>
	 */
	@Mangling({"_Z16av_log_set_leveli", "?av_log_set_level@@YAXH@Z"}) 
	void av_log_set_level(int int1);
	/**
	 * Original signature : <code>void av_log_set_callback(av_log_set_callback_arg1_callback)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:119</i>
	 */
	@Mangling({"_Z19av_log_set_callbackPFvPviPKcE", "?av_log_set_callback@@YAXPFXPAXHPAD@E@Z"}) 
	void av_log_set_callback(AvutilLibrary.av_log_set_callback_arg1_callback arg1);
	/**
	 * Original signature : <code>void av_log_default_callback(void*, int, const char*, va_list)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:120</i><br>
	 * @deprecated use the safer methods {@link #av_log_default_callback(com.sun.jna.Pointer, int, java.lang.String, avutil.AvutilLibrary.__gnuc_va_list)} and {@link #av_log_default_callback(com.sun.jna.Pointer, int, com.sun.jna.Pointer, avutil.AvutilLibrary.__gnuc_va_list)} instead
	 */
	@Mangling({"_Z23av_log_default_callbackPviPKc14__gnuc_va_list", "?av_log_default_callback@@YAXPAXHPAD14__gnuc_va_list@Z"}) 
	@Deprecated 
	void av_log_default_callback(Pointer ptr, int level, Pointer fmt, Pointer vl);
	/**
	 * Original signature : <code>void av_log_default_callback(void*, int, const char*, va_list)</code><br>
	 * <i>native declaration : src/main/headers/libavutil/log.h:120</i>
	 */
	@Mangling({"_Z23av_log_default_callbackPviPKc14__gnuc_va_list", "?av_log_default_callback@@YAXPAXHPAD14__gnuc_va_list@Z"}) 
	void av_log_default_callback(Pointer ptr, int level, String fmt, Pointer vl);
	/// misc math functions
	public static final class ff_log2_tab {
		private static Pointer ff_log2_tab;
		public static synchronized Pointer get() {
			if (ff_log2_tab == null) 
				ff_log2_tab = AvutilLibrary.JNA_NATIVE_LIB.getGlobalVariableAddress("ff_log2_tab");
			return ff_log2_tab;
		}
	};
	public static final class av_reverse {
		private static Pointer av_reverse;
		public static synchronized Pointer get() {
			if (av_reverse == null) 
				av_reverse = AvutilLibrary.JNA_NATIVE_LIB.getGlobalVariableAddress("av_reverse");
			return av_reverse;
		}
	};
}
