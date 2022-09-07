package pl.estrix;

import org.junit.Test;
import org.junit.Ignore;


@Ignore
public class LibTest {

    @Test
    public void shouldCountPercentage(){
        Float imageHeightSrc = 1000f;
        Float imageHeightDsc = 100f;
        float heightOver = 1.0F + (float)(imageHeightDsc - imageHeightSrc) / (float)imageHeightSrc;
        System.out.println("imageHeightSrc: " + imageHeightSrc);
        System.out.println("imageHeightDsc: " + imageHeightDsc);
        System.out.println("heightOver: " + heightOver);
    }

}
