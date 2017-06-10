package com.b505.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.b505.bean.BeSavingFile;

@Component
//客户端上传文件
public class FileUpload 
{
	
	private Set<String> checkFileSet = new HashSet<String>();

	public void addSet(String str)
	{
		checkFileSet.add(str);
	}
	public Set<String> getSet()
	{
		return checkFileSet;
	}
	
	/*1、检查是否有文件
	 *2、删除FileUpload.checkAndDeletedFile.daysNumber天之前的文件 方法的参数为文件夹。
	 *例如：File file = new File("picture\\")
	 **/
	public boolean checkAndDeletedFile(File file)throws Exception
	{
		final int fileKeepNumberOfDays = 2;
		CheckAndDeletedFile cdf = new CheckAndDeletedFile();
		boolean boo = cdf.checkDelete(file, fileKeepNumberOfDays);		
		if(boo)
		{
			System.out.println("检查/删除文件成功完成");
			return true;	
		}
		else
		{
			System.out.println("检查/删除文件失败");
			return false;
		}
	}
	
	//接收文件，并保存  /*方法参数：fileKind——文件的种类，如：picture、speech；file——要保存的文件；timerDeleted——文件是否需要定期删除*/
	public String[] saveFile(BeSavingFile beSavingFile) throws Exception
	{
		String fileKind = beSavingFile.getFileURL();
		byte[] file = beSavingFile.getFilesByte();
		boolean timerDeleted = beSavingFile.isTimerDeleted();
		String fileExtension = beSavingFile.getFileExtension();
		
		//添加文件的种类
		if(timerDeleted)
		{
			addSet(fileKind);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date newsDate =new Date();
		//fileDate是上传头像的时间
		String fileDate =format.format(newsDate);
		
		//以文件的类别作为保存文件的文件夹名
		File fileK = new File(fileKind);
		//以日期为文件夹名
		File dateFile = new File(fileKind+"\\"+fileDate);
		
		String[] str = new String[3];
		//文件夹已存在,保存文件
		if(fileK.exists())
		{
			//文件夹已存在,保存文件
			if(dateFile.exists())
			{
				str = this.savefile(fileKind, fileDate, file, fileExtension);
				return str;
			}
			//文件夹不存在，创建文件夹，保存文件
			else
			{
				if(dateFile.mkdirs())
				{
					str = this.savefile(fileKind, fileDate, file, fileExtension);
					return str;
				}
				else
				{
					return str;
				}
			}
		}
		//文件夹不存在，创建文件夹，保存文件
		else
		{
			//新建文件夹
			boolean boo = fileK.mkdirs();
			if(boo)
			{
				//文件夹已存在,保存文件
				if(dateFile.exists())
				{
					str = this.savefile(fileKind, fileDate, file, fileExtension);
					return str;
				}
				//文件夹不存在，创建文件夹，保存文件
				else
				{
					if(dateFile.mkdirs())
					{
						str = this.savefile(fileKind, fileDate, file, fileExtension);
						System.out.println("str------>"+str);
						return str;
					}
					else
					{
						return str;
					}
				}
			}
			else
			{
				return str;
			}
		}
	}
	
	//保存文件
	private String[] savefile(String fileKind,String fileDate,byte[] file,String fileExtension )
	{
		//返回状态
		int status = 0;
		SimpleDateFormat fileN= new SimpleDateFormat("HHmmssSSS");
		Date fileNameDate =new Date();
		String fileName = fileN.format(fileNameDate);
		String[] str = new String[3];
		//保存文件
		FileOutputStream fos = null;
		String URL = "";
		try
		{
             //创建流对象
             fos = new FileOutputStream(fileKind+"\\"+fileDate+"\\"+fileName+"."+fileExtension);
             //转换为byte数组
             byte[] b1 = file;
             //写入文件
             fos.write(b1);
             System.out.println("写入文件......");
             status = 1;
             URL = fileKind+"\\"+fileDate+"\\"+fileName;
             System.out.println("URL为："+URL);
		}
		catch (Exception e)
		{
             e.printStackTrace();
             status = 0;
             System.out.println("写入文件时出错了");
		}
		finally
		{
             try
             {
            	 fos.close();
            	 URL = fileKind+"\\"+fileDate+"\\"+fileName;
             }
             catch(Exception e)
             {
            	 e.printStackTrace();
             }
		}
		//文件保存是否成功，1为成功，0为失败
		str[0] = String.valueOf(status);
		System.out.println("str[0]为："+str[0]);
		//文件在本地的URL
		str[1] = URL;
		//文件的名字
		str[2] = fileName+"."+fileExtension;
		return str;
	}
}

