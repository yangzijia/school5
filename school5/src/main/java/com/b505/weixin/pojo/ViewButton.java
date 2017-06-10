package com.b505.weixin.pojo;

/**
 * 表示二级菜单（VIEW类型）并且继承菜单基类
 * @author 少游
 *
 */
public class ViewButton extends Button
{
                                            //菜单类型
	                                       private String type;
	                                       //view路径值
	                                       private String url;
										public String getType()
										{
											return type;
										}
										public void setType(String type)
										{
											this.type = type;
										}
										public String getUrl()
										{
											return url;
										}
										public void setUrl(String url)
										{
											this.url = url;
										}
}
