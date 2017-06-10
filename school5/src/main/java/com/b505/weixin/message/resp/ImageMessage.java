package com.b505.weixin.message.resp;
import com.b505.Message.model.Image;
/**
 * 图片消息
 * @author 少游
 *
 */
public class ImageMessage extends BaseMessage
{
	// 图片
		private Image Image;

		public Image getImage() {
			return Image;
		}

		public void setImage(Image image) {
			Image = image;
		}
}
