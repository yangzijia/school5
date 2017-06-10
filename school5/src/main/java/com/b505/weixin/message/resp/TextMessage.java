package com.b505.weixin.message.resp;

/**
 * 被动消息    继承基类消息
 * @author 少游
 *
 */

public class TextMessage extends BaseMessage
{
	                              //消息内容
									private String Content;
								
									public String getContent() {
										return Content;
									}
								
									public void setContent(String content) {
										Content = content;
									}
}
