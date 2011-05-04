//
//  QCoder
//
//  Created by Sumit Raja on 2010-06-09.
//  Copyright (c) 2010 Mediabag. All rights reserved.
//

package biz.mediabag.qcoder

class QCoder {

}

class QCoderException(code: Int, message: String, cause: Throwable) extends RuntimeException(
  (if (code != null.asInstanceOf[Int]) { code + ": " + message } else { message }), cause) {

  def this(code: Int, message: String) { this(code, message, null) }

  def this(message: String) { this(null.asInstanceOf[Int], message, null) }

}