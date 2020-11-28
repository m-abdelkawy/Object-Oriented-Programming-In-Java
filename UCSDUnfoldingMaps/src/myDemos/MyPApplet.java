package myDemos;

import processing.core.*;

public class MyPApplet extends PApplet {

	private String URL = "https://s3.amazonaws.com/photos.bcheights.com/wp-content/uploads/2019/10/03123602/peaky-blinders-online-1024x701.jpg";
	private PImage backgroundImg;

	public void setup() {
		size(400, 400);
		backgroundImg = loadImage(URL, "jpg");
	}

	public void draw() {
		backgroundImg.resize(0, height);
		image(backgroundImg, 0, 0);
		int r = 255;
		int g = 209;
		int b = 0;

		int m = second();

		fill(r-2*m, g-2*m, b + m);
		ellipse(width / 4, height / 5, width / 4, height / 5);
		ellipse(width / 2, height / 5, width / 4, height / 5);
	}

	public static void main(String[] args) {
		// Add main method for running as application
		PApplet.main(new String[] { "--present", "MyPApplet" });
	}

}
