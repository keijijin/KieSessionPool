package com.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.sample.DroolsTest.Message;

public class KieSessionPool {
	
	static Map<String, BlockingQueue> pool = new HashMap<String, BlockingQueue>();

	public Map<String, BlockingQueue> getPool() {
		return pool;
	}

	public void setPool(Map<String, BlockingQueue> pool) {
		this.pool = pool;
	}
	
	public static void create(String rulefilename) throws ClassNotFoundException, IOException {
		RuleFile rulefile = new RuleFile();
		Map<String, KieBase> kbs = rulefile.read(rulefilename);
		create(kbs);
	}

	public static void create(Map<String, KieBase> kbs) {
		kbs.keySet().forEach(kbname -> {
			KieBase kbase = kbs.get(kbname);
			LinkedBlockingQueue queue = new LinkedBlockingQueue();
			for ( int i = 0; i < 10; i++) {
				KieSession ksession = kbase.newKieSession();
				try {
					queue.put(ksession);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			pool.put(kbname, queue);
		});
	}
	
	public static void create() {
        KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
	    
	    kContainer.getKieBaseNames().forEach(kbname -> {
		    LinkedBlockingQueue queue = new LinkedBlockingQueue();
	    	for (int i = 0; i < 10; i++) {
	    		KieSession ksession = kContainer.newKieSession("ksession-" + kbname);
	    		try {
					queue.put(ksession);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    	pool.put(kbname, queue);
	    });
	}
		
	public static KieSession borrow(String kbname) {
		LinkedBlockingQueue queue = (LinkedBlockingQueue) pool.get(kbname);
		return (KieSession) queue.element();
	}
}
