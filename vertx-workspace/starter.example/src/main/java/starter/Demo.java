package starter;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;


public class Demo {
	private static final Logger logger = LoggerFactory.getLogger(Demo.class);
	
	public static void main(String[] args){
		
		Vertx vertx = Vertx.vertx();
		//vertx.deployVerticle(new RestServiceVerticle());
		vertx.deployVerticle(new HelloWorldVerticle());
		

	}

}

