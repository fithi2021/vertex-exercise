package starter;

import java.util.Random;
import java.util.UUID;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;


public class RestServiceVerticle extends AbstractVerticle {
	
	private static final Logger logger = LoggerFactory.getLogger(RestServiceVerticle.class);
	private static final int httpPort = Integer.parseInt(System.getenv().getOrDefault("HTTP_PORT", "8080"));
	
	private final Random random = new Random();
	private final String sensorId = UUID.randomUUID().toString();
    private double temperature = 21.0;
	  
	
	  
	@Override
    public void start(Promise<Void> startPromise) {
		
		  vertx.setPeriodic(2000, this::updateTempreture); 
		  startPromise.complete();
		    
        
        Router router =  Router.router(vertx);
        router.get("/data").handler(this::getData);
        
        vertx.createHttpServer()
        			.requestHandler(router)
        			.listen(httpPort)
        			.onSuccess(ok ->{
        				logger.info("http server running: http://127.0.0.1 {}",httpPort);
        					//startPromise.complete();
        				
        			})
        			.onFailure(startPromise::fail);
               
    }
	

	private void updateTempreture(Long id) {
		temperature = temperature + (random.nextGaussian() / 2.0d);
		logger.info("Temperature updated {} ", temperature);
		
		//System.out.println("Temperature updated {} "+temperature);
		
	}

	private void getData(RoutingContext context) {
		logger.info("Processing HTTP request from {}",context.request().remoteAddress());
		JsonObject payload = new JsonObject()
				.put("uuid",sensorId)
				.put("temperature",temperature)
				.put("timestamp", System.currentTimeMillis());
		 context.response()
				.putHeader("Content-Type", "application/json")
				.setStatusCode(200)
				.end(payload.encode());
		
		
	}

	

}
