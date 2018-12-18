package com.sample;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieContainer;

public class RuleFile {

	private Map<String, KieBase> kBases = new HashMap<String, KieBase>();
	
	private String name = "rulefile/rulefile.module";
	
	public Map<String, KieBase> getkBases() {
		return kBases;
	}

	public void setkBases(Map<String, KieBase> kBases) {
		this.kBases = kBases;
	}
	
	public void setkBases(KieContainer kContainer) {
		kContainer.getKieBaseNames().forEach(kbname -> {
			KieBase kbase = kContainer.getKieBase(kbname);
			getkBases().put(kbname, kbase);
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void write(KieContainer kContainer, String filename) throws IOException {
		setName(filename);
		write(kContainer);
	}

	public void write(KieContainer kContainer) throws IOException {
		setkBases(kContainer);
		
		File rulefile = new File(getName());
		Path rulepath = rulefile.toPath();
		OutputStream os = Files.newOutputStream(rulepath);
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(getkBases());
	}

	public Map<String, KieBase> read(String filename) throws ClassNotFoundException, IOException {
		setName(filename);
		return read();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, KieBase> read() throws IOException, ClassNotFoundException {
		Path filepath = Paths.get(getName());
		InputStream is = Files.newInputStream(filepath);
		ObjectInputStream ois = new ObjectInputStream(is);
		return (Map<String, KieBase>) ois.readObject();
	}
}
