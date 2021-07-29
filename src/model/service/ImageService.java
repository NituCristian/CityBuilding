package model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageService {

	/**
	 * the image from a specified path
	 * @param imagePath the path of the image
	 * @return the bufferedImage object
	 */
	public BufferedImage loadImage(String imagePath) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return img;
	}
}
