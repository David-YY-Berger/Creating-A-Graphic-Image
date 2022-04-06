package renderer;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test Method for {@link ImageWriter#writeToImage()}
     */
    @Test
    void testWriteToImage() {

        //build an image 800*500 pixels and 16*10 squares
        ImageWriter image = new ImageWriter("myImage", 800, 500);

        //background
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                image.writePixel(i, j, new Color(255, 255, 0));
            }

        }

        //vertical lines
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 500; j++) {
                image.writePixel(i, j, new Color(255,0,0));
            }
        }

        //horizontal lines
        for (int i = 0; i < 500; i += 50) {
            for (int j = 0; j < 800; j++) {
                image.writePixel(j, i, new Color(255, 0, 0));
            }
        }

        //write to file
        image.writeToImage();

    }
}