package biz.mediabag.qcoder.domain

/**
 * Created by IntelliJ IDEA.
 * User: sumitraja
 * Date: Jun 12, 2010
 * Time: 11:22:16 AM
 * To change this template use File | Settings | File Templates.
 */

class Stream {
  val frames: Seq[Frame] 
}

class File {
  val streams: Map[String, Stream]
}

