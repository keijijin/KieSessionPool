package com.sample;

import java.io.IOException;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

public class CreateRuleFile {

	public static void main(String[] args) {
		RuleFile rulefile = new RuleFile();
		rulefile.setName("rulefile/rulefile.module");

		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();

	    try {
			rulefile.write(kContainer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("Rule File Created..." + rulefile.getName());
		}
	}

}
