package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

public class ImageWriterTest {
    private static final String TEST_IMAGE_NAME = "testImage";
    private static final int IMAGE_WIDTH = 801;
    private static final int IMAGE_HEIGHT = 501;

    @Test
    public void testImageWriter() {
        assertDoesNotThrow(()-> {
            ImageWriter imageWriter = new ImageWriter(IMAGE_WIDTH, IMAGE_HEIGHT);
            Color yellow = new Color(255, 255, 0);
            Color red = new Color(255, 0, 0);

            for (int i = 0; i < IMAGE_WIDTH; i++) {
                for (int j = 0; j < IMAGE_HEIGHT; j++) {
                    if (i%50 == 0 || j%50 == 0) {
                        imageWriter.writePixel(i, j, red);
                    } else {
                        imageWriter.writePixel(i, j, yellow);
                    }
                }
            }
            imageWriter.writeToImage(TEST_IMAGE_NAME);
                }, "ERROR: failed to create an image");

    }


}
