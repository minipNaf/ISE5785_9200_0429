package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ImageWriter class.
 * Tests the functionality of the ImageWriter class by creating an image and writing pixels to it.
 */
public class ImageWriterTest {
    /**default constructor to satisfy the compiler*/
    ImageWriterTest(){}

    //the name of the image to be created
    private static final String TEST_IMAGE_NAME = "testImage";
    //the width and height of the image. added 1 to with and height for grid to be on all sides
    private static final int IMAGE_WIDTH = 801;
    private static final int IMAGE_HEIGHT = 501;

    /**
     * Test case for the ImageWriter class.
     * This test case creates an image writer object and writes pixels to it.
     * It verifies that the image is created successfully without any exceptions.
     * this test for: {@link renderer.ImageWriter#writeToImage(String)}
     */
    @Test
    public void testImageWriter() {
        assertDoesNotThrow(()-> {
            ImageWriter imageWriter = new ImageWriter(IMAGE_WIDTH, IMAGE_HEIGHT);
            Color yellow = new Color(255, 255, 0);
            Color red = new Color(255, 0, 0);

            // Fill the image with a grid pattern
            for (int i = 0; i < IMAGE_WIDTH; i++) {
                for (int j = 0; j < IMAGE_HEIGHT; j++) {
                    if (i%50 == 0 || j%50 == 0) { // if the pixel is on the grid
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
