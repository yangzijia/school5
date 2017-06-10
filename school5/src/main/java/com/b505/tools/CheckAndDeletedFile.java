package com.b505.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CheckAndDeletedFile 
{
	public boolean checkDelete(File files, int dayNum)
	{
		boolean status = false;
		final String  RealPath = files.getAbsolutePath();
		//files文件存在
		if(files.exists())
		{
			//files文件夹下的文件组
			String fileList[] = files.list();
			//files文件夹下的不被删除的文件夹的组
			String holdFileList[] = new String[dayNum];
			if(fileList.length >0)
			{
				//得到日历
				Calendar calendar = Calendar.getInstance();
				for(int i = 0; i < dayNum; i++)
				{
					//当前日期
					Date date = new Date(); 
					//前i天的日期
					Date beforeDay = new Date();
					calendar.setTime(date);
					//计算i天前的日期
					calendar.add(Calendar.DAY_OF_MONTH, -i);
					//得到i天前的日期
					beforeDay = calendar.getTime();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); 
					String strDate = sdf.format(beforeDay);
					holdFileList[i] = strDate;
				}
				//删除N天之前的文件
				for(int i = 0; i < fileList.length; i++)
				{
					boolean del = true;
					String str = fileList[i];
					for(int j = 0; j < dayNum; j++)
					{
						if(str.equals(holdFileList[j]))
						{
							del = false;
							break;
						}
						else
						{
							continue;
						}
					}
					if(del==false)
					{
						continue;
					}
					else
					{
						File file = new File(RealPath+str);
						if(file.exists())
						{
							System.out.println("删除N天之前的文件"+str+"......");
							try
							{
								file.delete();
							}
							catch(Exception e)
							{
								e.printStackTrace();
								System.err.println("CheckAndDeletedFile_checkDelete_file.delete();出错");
								break;
							}
						}
						else
						{
							continue;
						}
					}
				}
				status = true;
			}
			else
			{
				status = true;
				return status;
			}
		}
		//files文件夹不存在就新建文件夹
		else
		{
			try
			{
				if(files.mkdirs())
				{
					System.out.println("检查/删除文件时新建文件夹成功");
					status = true;
				}
				else
				{
					System.out.println("检查/删除文件时新建文件夹失败");
					status = false;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				status = false;
			}
			return status;
		}
		return status;
	}
}

