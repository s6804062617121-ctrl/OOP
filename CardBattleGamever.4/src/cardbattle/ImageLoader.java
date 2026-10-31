package cardbattle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * ImageLoader  from the images/ folder .
 */
public class ImageLoader {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();
    private static final String IMAGE_FOLDER = "images/";

    /** Load an image, returns null if the file is not found (result is cached to avoid re-reading every frame) */
    public static BufferedImage load(String filename) {
        if (CACHE.containsKey(filename)) {
            return CACHE.get(filename);
        }
        BufferedImage img = null;
        File f = new File(IMAGE_FOLDER + filename);
        if (f.exists()) {
            try {
                img = ImageIO.read(f);
            } catch (IOException e) {
                img = null;
            }
        }
        CACHE.put(filename, img);
        return img;
    }
}
