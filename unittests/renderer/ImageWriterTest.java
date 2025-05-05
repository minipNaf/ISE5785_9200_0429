package renderer;

public class ImageWriterTest {
    private static final String TEST_IMAGE_NAME = "testImage";
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 500;

    public static void main(String[] args) {
        ImageWriter imageWriter = new ImageWriter(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageWriter.writeToImage();
        System.out.println("Image written successfully.");
    }
}
