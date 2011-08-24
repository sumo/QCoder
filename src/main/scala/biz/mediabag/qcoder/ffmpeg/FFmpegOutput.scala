package biz.mediabag.qcoder.ffmpeg

import biz.mediabag.qcoder.domain._
import biz.mediabag.qcoder._
import avformat._
import avcodec._
import avutil._
import FFmpegUtils._
import AvformatLibrary._
import grizzled.slf4j.Logging

class FFmpegOutputContainer(format: String, videoCodecStr: String, audioCodecStr: String)
  extends FFmpegContainer with Container[FFmpegEncodingStream[_]] with Logging {
  override val formatCtx = FormatLibrary.avformat_alloc_context
  override val output = true
  val oformat = FormatLibrary.av_guess_format(format, null, null)
  if (oformat == null) {
    throw new QCoderException(format + " is an unknown format")
  }
  formatCtx.oformat = oformat.byReference
  debug("Created output format " + oformat.name + " with default video codec " + oformat.video_codec
    + " and default audio codec " + oformat.audio_codec)
  private val videoCodec = CodecLibrary.avcodec_find_encoder_by_name(videoCodecStr)
  if (videoCodec == null) {
    throw new QCoderException(videoCodecStr + " is an unknown video codec")
  }
  private val audioCodec = CodecLibrary.avcodec_find_encoder_by_name(audioCodecStr)
  if (audioCodec == null) {
    throw new QCoderException(audioCodecStr + " is an unknown audio codec")
  }
  oformat.audio_codec = audioCodec.id
  oformat.video_codec = videoCodec.id

  override def streams = Nil
}

class FFmpegEncodingContainerFactory extends EncodingContainerFactory[FFmpegEncodingStream[_]] with Logging {

  def writeTo(format: String, videoCodec: String, audioCodec: String): FFmpegOutputContainer = {
    new FFmpegOutputContainer(format, videoCodec, audioCodec)
  }
}