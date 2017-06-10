package com.b505.weixin.pojo;

/**
 * 表示二级菜单(CLICK类型)，并且继承菜单基类
 * @author 少游
 *
 */
public class CommonButton extends Button
{
                                   //菜单类型
	                               private String  type;
	                               //菜单key值
	                               private String key;
								public String getType()
								{
									return type;
								}
								public void setType(String type)
								{
									this.type = type;
								}
								public String getKey()
								{
									return key;
								}
								public void setKey(String key)
								{
									this.key = key;
								}
	                               
}
