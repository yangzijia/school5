package com.b505.weixin.pojo;

/**
 * 表示菜单类的基类，一级菜单和二级菜单的Entity都继承此Entity
 * @author 少游
 *2016.12.10
 */
public class Button
{
                            //所有一级菜单、二级菜单都共有一个相同的属性，那就是name
	                        private String name;

							public String getName()
							{
								return name;
							}

							public void setName(String name)
							{
								this.name = name;
							}
}
