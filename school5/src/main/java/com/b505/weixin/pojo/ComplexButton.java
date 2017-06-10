package com.b505.weixin.pojo;

/**
 * 表示一级菜单,父菜单项的封装。
 * 对父菜单项的定义：包含有二级菜单项的一级菜单。
 * 这类菜单项包含有二个属性：name和sub_button，而sub_button是一个子菜单项数组
 * @author 少游
 *
 */
public class ComplexButton extends Button
{
                                          //sub_button是一个子菜单项数组
	                                      private Button[] sub_button;

										public Button[] getSub_button()
										{
											return sub_button;
										}

										public void setSub_button(Button[] sub_button)
										{
											this.sub_button = sub_button;
										}
}
