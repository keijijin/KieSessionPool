package com.sample;

import java.util.concurrent.LinkedBlockingQueue;

import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    @SuppressWarnings({ "rawtypes", "static-access" })
	public static final void main(String[] args) {
        try {
            KieSessionPool kspool = new KieSessionPool();
            //kspool.create("rulefile/rulefile.module");
            kspool.create();
            
	        KieSession kSession = kspool.borrow("rules");
	        
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            message.setCode("1");
            kSession.insert(message);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;

        private int status;
        
        private String code;

        public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
