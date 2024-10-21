package tests;

import org.junit.runner.JUnitCore;

import tests.items.TestActivite;

public class LanceurTest {
	public static void main(String[] args) {
        JUnitCore.runClasses(TestActivite.class);
    }
}
