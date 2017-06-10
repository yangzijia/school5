package com.b505.weixin.message.req;

/**
 * 视频消息 继承基类消息
 * @author 少游
 *
 */

public class VideoMessage extends BaseMessage
{
	// 视频消息媒体ID
		private String MediaId;
		// 视频消息缩略图ID
		private String ThumbMediaId;

		public String getMediaId() {
			return MediaId;
		}

		public void setMediaId(String mediaId) {
			MediaId = mediaId;
		}

		public String getThumbMediaId() {
			return ThumbMediaId;
		}

		public void setThumbMediaId(String thumbMediaId) {
			ThumbMediaId = thumbMediaId;
		}

}
