package com.b505.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.b505.bean.util.WeChatUtil;
import com.b505.tools.DataProcess;
import com.b505.weixin.tools.TryCatchCoreService;
import com.b505.weixin.utils.SignUtil;

/**
 * 微信的核心入口
 * 
 * @author 少游
 *@see 用户微信接口的相关接入工作。
 * @pram 2016.11.16 
 */

@Controller
@RequestMapping("/WeChat")
public class WeixinController
{
	                       @Autowired
	                       private SignUtil signUtil;
	                       @Autowired
	                       private  TryCatchCoreService tryCatchCollegeService;
	                       @Autowired
	                       private DataProcess dataProcess;
	                              //使用指定类初始化日志对象，在日志输出的时候，可以打印出日志信息所在类
                                   Logger   logger=LoggerFactory.getLogger(WeixinController.class);
                                   /** 
                   			     * 校验信息是否是从微信服务器发过来的。 
                   			     *  @author 少游
                   			     * @param weChat 
                   			     * @param out 
                   			     */ 
                                   @RequestMapping(value="/api.html",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
                                   @ResponseBody
                                   public String WeChatInterface(WeChatUtil wcu)throws Exception{
                                	   System.out.println("连接了");
                                	   //微信加密签名
                                	   String signature=wcu.getSignature();
                                	   //时间戳
                                	   String timestamp = wcu.getTimestamp();
                                	   //随机数
                                	   String nonce=wcu.getNonce();
                                	   //随机字符串
                                	   String echostr=wcu.getEchostr();
                                	   logger.info("signature is :"+signature+"timestamp is"+timestamp+"nonce is :"+nonce);
                                	   System.out.println("signature is :"+signature+"timestamp is"+timestamp+"nonce is :"+nonce);
                                	   if (signUtil.checkSignature(signature, timestamp, nonce))
									{
										return echostr;
									}else {
										System.out.println("不是微信服务器发来的请求,请小心!");
					                	return null;
									}
                                	   
                              }
                                   
                                   /**
                               	 * 处理微信发来的请求
                               	 * @author 少游
                               	 * @pram 2016.11.17
                               	 * @param request
                               	 * @return
                               	 */
                                   
                                   @RequestMapping(value="/api.html",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
                                   @ResponseBody
                                   public String getWeiXinMessage(HttpServletRequest request, HttpServletResponse response)throws Exception{
                                	 //微信服务器POST消息时用的是UTF-8编码，在接收时也要用同样的编码，否则中文会乱码；
                                   	request.setCharacterEncoding("UTF-8"); 
                                   	//在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
                                        response.setCharacterEncoding("UTF-8"); 
                                        String respXml=tryCatchCollegeService.getprocessRequest(request);
                                        if (dataProcess.dataIsNull(respXml))
										{
                                        	  return null;
										}else {
											return respXml;
										}
                                	 
                                   }
}
