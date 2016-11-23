package com.wizardhax.bungee.WizardCore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Util {

	public static void setFinalStatic(Field field, Object newValue, Object other) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(other, newValue);
	}
}
