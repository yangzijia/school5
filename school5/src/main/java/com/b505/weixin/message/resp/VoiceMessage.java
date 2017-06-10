package com.b505.weixin.message.resp;

import com.b505.Message.model.Voice;

/**
 * 被动回复语音消息
 * @author 少游
 *
 */
public class VoiceMessage extends BaseMessage
{
                                //语音消息
	                            private Voice Voice;

								public Voice getVoice()
								{
									return Voice;
								}

								public void setVoice(Voice voice)
								{
									Voice = voice;
								}
}
