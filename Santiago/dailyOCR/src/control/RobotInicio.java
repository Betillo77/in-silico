package control;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RobotInicio 
{
	public static void main(String [] args) throws AWTException, InterruptedException
	{
		Thread.sleep(120000);
		Robot r = new Robot();
		Thread.sleep(5000);
		r.mouseMove(85, 239);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(48, 60);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(95, 255);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(48, 60);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(83, 172);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(11, 35);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(86, 467);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_S);
		r.keyRelease(KeyEvent.VK_S);
		r.keyPress(KeyEvent.VK_U);
		r.keyRelease(KeyEvent.VK_U);
		r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D);
		r.keyPress(KeyEvent.VK_O);
		r.keyRelease(KeyEvent.VK_O);
		r.keyPress(KeyEvent.VK_SPACE);
		r.keyRelease(KeyEvent.VK_SPACE);
		r.keyPress(KeyEvent.VK_S);
		r.keyRelease(KeyEvent.VK_S);
		r.keyPress(KeyEvent.VK_U);
		r.keyRelease(KeyEvent.VK_U);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_C);
		r.keyRelease(KeyEvent.VK_C);
		r.keyRelease(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_L);
		r.keyRelease(KeyEvent.VK_L);
		r.keyPress(KeyEvent.VK_I);
		r.keyRelease(KeyEvent.VK_I);
		r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_D);
		r.keyRelease(KeyEvent.VK_D);
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_I);
		r.keyRelease(KeyEvent.VK_I);
		r.keyRelease(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_N);
		r.keyRelease(KeyEvent.VK_N);
		r.keyPress(KeyEvent.VK_G);
		r.keyRelease(KeyEvent.VK_G);
		r.keyPress(KeyEvent.VK_E);
		r.keyRelease(KeyEvent.VK_E);
		r.keyPress(KeyEvent.VK_S);
		r.keyRelease(KeyEvent.VK_S);
		r.keyPress(KeyEvent.VK_I);
		r.keyRelease(KeyEvent.VK_I);
		r.keyPress(KeyEvent.VK_S);
		r.keyRelease(KeyEvent.VK_S);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		r.mouseMove(112, 187);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(3000);
		r.mouseMove(121, 241);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(5000);
	}
}