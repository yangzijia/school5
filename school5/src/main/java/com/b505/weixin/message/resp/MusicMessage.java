package com.b505.weixin.message.resp;

import com.b505.Message.model.Music;

/**
 * 被动音乐消息
 * @author 少游
 *
 */
public class MusicMessage extends BaseMessage
{
                               //音乐消息
	                          private Music Music;

							public Music getMusic()
							{
								return Music;
							}

							public void setMusic(Music music)
							{
								Music = music;
							}
}
