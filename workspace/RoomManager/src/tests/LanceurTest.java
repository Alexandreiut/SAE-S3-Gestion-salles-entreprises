package tests;

import org.junit.runner.JUnitCore;

import tests.items.TestActivite;
import tests.items.TestEmploye;

public class LanceurTest {
	public static void main(String[] args) {
        JUnitCore.runClasses(TestActivite.class);
        JUnitCore.runClasses(TestEmploye.class);
    }
}
