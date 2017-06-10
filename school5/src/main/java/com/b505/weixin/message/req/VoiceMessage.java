package com.b505.weixin.message.req;

/**
 * 语音消息  继承基类消息
 * @author 少游
 *
 */
public class VoiceMessage extends BaseMessage
{
							// 媒体ID
								private String MediaId;
								// 语音格式
								private String Format;
								// 语音识别结果,utf-8编码
								private String Recognition;
								public String getMediaId()
								{
									return MediaId;
								}
								public void setMediaId(String mediaId)
								{
									MediaId = mediaId;
								}
								public String getFormat()
								{
									return Format;
								}
								public void setFormat(String format)
								{
									Format = format;
								}
								public String getRecognition()
								{
									return Recognition;
								}
								public void setRecognition(String recognition)
								{
									Recognition = recognition;
								}
        
} 
