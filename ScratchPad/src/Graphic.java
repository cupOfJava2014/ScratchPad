import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Graphic {

	public Graphic(File f, Image i) throws Exception {
		// TODO Auto-generated constructor stub
		
		f.createNewFile();
		
		String format = f.toString().substring(f.toString().length() - 3);
		
		System.out.println(format);
		
		BufferedImage temp = (BufferedImage) i;
		
		i = temp.getSubimage(0, 75, i.getWidth(null), i.getHeight(null) - 75);
		
		ImageIO.write((RenderedImage) i, format, f);
		
	}

}
