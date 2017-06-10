package com.b505.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;













import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.b505.service.ICoreService;
import com.b505.weixin.message.resp.TextMessage;
import com.b505.weixin.utils.MessageUtil;

@Service("CoreService")
public class CoreServiceImpl implements ICoreService
{
	                        Logger logger=LoggerFactory.getLogger(CoreServiceImpl.class);
						     @Autowired
						     private MessageUtil messageUtil;
							@Override
							public String CoreService(HttpServletRequest request)
							{
								// 返回给微信服务器的消息,默认为null
							      String respMessage=null;
							      try
								{
							    	  // 默认返回的文本消息内容  
							            String respContent = "请求处理异常，请稍候尝试！";  
							            
									 //xml分析
							    	// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
							    	  Map<String, String> map=messageUtil.parseXml(request);
							    	  //发送方账号
							    	  String fromUserName=map.get("FromUserName");
							    	  System.out.println("fromUserName--->"+fromUserName);
							    	  //接受方账号（公众号）
							    	  String toUserName=map.get("ToUserName");
							    	  //消息类型
							    	  String msgType=map.get("MsgType");
							    	  
							    	  logger.info("fromUserName is:" +fromUserName+" toUserName is:" +toUserName+" msgType is:" +msgType);
							    	  
							    	   //默认回复文本消息
							    	  TextMessage textMessage =new TextMessage();
							    	  textMessage.setToUserName(fromUserName);
							    	  textMessage.setFromUserName(toUserName);
							    	  textMessage.setCreateTime(new Date().getTime());
							    	  textMessage.setMsgType(messageUtil.RESP_MESSAGE_TYPE_TEXT);
							    	  textMessage.setFuncFlag(0);
							    	  //文本消息
							    	  if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_TEXT))
									{
										respContent="亲，这是文本消息！";
										textMessage.setContent(respContent);
										respMessage=messageUtil.textMessageToXml(textMessage);
									}
							    	// 图片消息
							            else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
							                respContent = "您发送的是图片消息！";
							            	textMessage.setContent(respContent);
											respMessage=messageUtil.textMessageToXml(textMessage);
							            }
							            // 语音消息
							            else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_VOICE)) {
							                respContent = "您发送的是语音消息！";
							            	textMessage.setContent(respContent);
											respMessage=messageUtil.textMessageToXml(textMessage);
							            }
							            // 视频消息
							            else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
							                respContent = "您发送的是视频消息！";
							            	textMessage.setContent(respContent);
											respMessage=messageUtil.textMessageToXml(textMessage);
							            }
							            // 地理位置消息
							            else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
							                respContent = "您发送的是地理位置消息！";
							            	textMessage.setContent(respContent);
											respMessage=messageUtil.textMessageToXml(textMessage);
							            }
							            // 链接消息
							            else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_LINK)) {
							                respContent = "您发送的是链接消息！";
							            	textMessage.setContent(respContent);
											respMessage=messageUtil.textMessageToXml(textMessage);
							            }
							    	  //事件推送
							    	  else if (msgType.equals(messageUtil.REQ_MESSAGE_TYPE_EVENT)) {
							    		 //事件类型
							    		  String  eventType =map.get("Event");
							    		  //关注
							    		  if (eventType.equals(messageUtil.EVENT_TYPE_SUBSCRIBE))
										{
							    			  respContent="亲，谢谢您的关注！";
							    			// 设置文本消息的内容
											   textMessage.setContent(respContent);
											   //将文本消息转化成xml
											   respMessage=messageUtil.textMessageToXml(textMessage);
										}
							    		 //取消关注
							    		  else if (eventType.equals(messageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
							    			  
							    			// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
											
										}
							    		  //扫描带参数二维码
							    		 else if (eventType.equals(messageUtil.EVENT_TYPE_SCAN)) {
							    			 // TODO 处理扫描带参数二维码事件
										}
										//上报地理位置
							    		 else if (eventType.equals(messageUtil.EVENT_TYPE_LOCATION)) {
							    			// TODO 处理上报地理位置事件
										}
							    		  //自定义菜单（点击菜单拉取消息）
							    		 else if (eventType.equals(messageUtil.EVENT_TYPE_CLICK)) {
							    			// TODO 处理自定义点击菜单事件
										}
							    		  //自定义菜单（(自定义菜单URl视图)）
							    		 else if (eventType.equals(messageUtil.EVENT_TYPE_VIEW)) {
											 // TODO 处理自定义自定义菜单URl视图
										}
									}
							    	  
							    	  
								}
								catch (Exception e)
								{
									// TODO: handle exception
									e.printStackTrace();
									System.out.println("服务器系统出错了！");
								}
								
								return respMessage;
							}

}
