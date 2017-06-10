/*
*@包名：com.b505.web        
*@文档名：IndexController.java 
*@功能：登录系统首页    
*@作者：李振强        
*@创建时间：2014.4.20   
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.web;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.b505.security.MyUserDetailServiceImpl;
import com.b505.tools.TryCatchUserService;


@Controller
public class IndexController 

{
	  @Autowired
	  private MyUserDetailServiceImpl cwSysUserService;
	  @Autowired
	  private AuthenticationManager myAuthenticationManager; 
	  @Autowired
	  private TryCatchUserService tryCatchUsrService;
	  
	//整个系统的入口
	@RequestMapping("/index.html")
	public String getLogin()
	{
		return "index";
	}
	@RequestMapping("/quit.html")
	public String quitMethond(HttpServletRequest request){
		Enumeration<String> e = request.getSession().getAttributeNames();
		String sessionName = e.nextElement();
		request.getSession().removeAttribute(sessionName);
		
		return "index";
	}
	
	@RequestMapping("/getVerifyCode.html")
	public void getVerifyMCode(Model model,HttpServletRequest request,HttpServletResponse response) {
		
		List<Color> colors = new ArrayList<Color>();
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
		
		List<Font> fonts = new ArrayList<Font>();
		fonts.add(new Font("Geneva", 2, 32));
		fonts.add(new Font("Courier", 3, 32));
		fonts.add(new Font("Arial", 1, 32));
	    
		//WordRenderer wordRenderer = new ColoredEdgesWordRenderer(colors, fonts);
		WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);
		
		Captcha captcha = new Captcha.Builder(150, 50).addText(wordRenderer).gimp(new DropShadowGimpyRenderer())
				.addBackground(new TransparentBackgroundProducer()).build();

		HttpSession session=request.getSession();
		session.setAttribute("verifyCode", captcha.getAnswer());
		
		CaptchaServletUtil.writeImage(response, captcha.getImage());

	}
	
	@RequestMapping("/sessionInvalid.html")
	public String sessionInvalid()
	{
		return "index";
	}
	@RequestMapping("/access.html")
	public String access()
	{
		return "access";
	}
	

}
