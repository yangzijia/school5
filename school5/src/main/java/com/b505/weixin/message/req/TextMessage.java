package com.b505.weixin.message.req;

/**
 * 文本消息   继承消息基类
 * @author 少游
 *
 */
public class TextMessage extends BaseMessage
{
	                         //消息内容
                               private String Content;

							public String getContent()
							{
								return Content;
							}

							public void setContent(String content)
							{
								Content = content;
							}       
} 
