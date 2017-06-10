package com.b505.weixin.message.resp;

import com.b505.Message.model.Video;

public class VideoMessage extends BaseMessage
{
                //视频消息
	            private Video Video;

				public Video getVideo()
				{
					return Video;
				}

				public void setVideo(Video video)
				{
					Video = video;
				}
}
