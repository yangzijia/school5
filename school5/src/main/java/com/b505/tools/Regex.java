/*
*@包名：com.b505.tools       
*@文档名：Regex.java
*@功能：使用正则表达式验证用户的输入信息      
*@作者：李振强    
*@创建时间：2014.4.27
*@版权：河北北方学院信息技术研究所 
*/
package com.b505.tools;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class Regex 
{
	
	
	/*
	 * @方法名：cardIDRegex
	 * @功能：验证身份证号
	 * @功能说明：验证身份证号
	 * @作者：李振强
	 * @创建时间：2014.4.27
	 * @修改时间：2014.4.27
	 */
	public boolean cardIDRegex(String cardID)
	{
		//身份证号匹配
		String regex = "\\d{17}[0-9|A-Z|a-z]{1}";
		boolean status = Pattern.matches(regex, cardID);
		return status;
	}
	
	/*
	 * @方法名：phoneRegex
	 * @功能：验证手机号
	 * @功能说明：验证手机号
	 * @作者：李振强
	 * @创建时间：2014.4.27
	 * @修改时间：2014.4.27
	 */
	public boolean phoneRegex(String phone)
	{
		//手机号匹配
		String regex ="[1][358]\\d{9}";
		boolean status = Pattern.matches(regex, phone);
		return status;
	}
	
	/*
	 * @方法名：nicknameRegex
	 * @功能：验证昵称
	 * @功能说明：验证昵称
	 * @作者：李振强
	 * @创建时间：2014.5.23
	 * @修改时间：2014.5.23
	 */
	public boolean nicknameRegex(String nickname)
	{
		//昵称匹配
		String regex = "[A-Z|a-z|0-9|\u4E00-\u9FA5]{1,20}";
		boolean status = Pattern.matches(regex, nickname);
		return status;
	}
	
	/*
	 * @方法名：nameRegex
	 * @功能：验证姓名
	 * @功能说明：验证昵称
	 * @作者：李振强
	 * @创建时间：2014.5.23
	 * @修改时间：2014.5.23
	 */
	public boolean nameRegex(String name)
	{
		//汉字匹配
		String regex = "[\u4E00-\u9FA5]{1,50}";
		boolean status = Pattern.matches(regex, name);
		return status;		
	}
	
}

