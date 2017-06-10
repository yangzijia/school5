package com.b505.weixin.message.req;

/**
 * 图片消息  继承消息基类
 * @author 少游
 *
 */
public class ImageMessage extends BaseMessage
{
                                //图片链接
	                          private  String  PicUrl;

							public String getPicUrl()
							{
								return PicUrl;
							}

							public void setPicUrl(String picUrl)
							{
								PicUrl = picUrl;
							}
	                          
}
