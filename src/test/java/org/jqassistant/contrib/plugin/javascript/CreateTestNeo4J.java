package org.jqassistant.contrib.plugin.javascript;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.configuration.BoltConnector;

public class CreateTestNeo4J {

	public static void main(String[] args) throws IOException, InterruptedException {
		org.neo4j.kernel.configuration.BoltConnector bolt = new BoltConnector();
        GraphDatabaseService graphDb = new GraphDatabaseFactory()
                .newEmbeddedDatabaseBuilder( new File("target/jqassistant/test-store"))
                .setConfig("dbms.connector.http.enabled","true" )
                .setConfig("dbms.connector.http.address","localhost:7575")
                .setConfig( bolt.type, "BOLT" )
                .setConfig( bolt.enabled, "true" )
                .setConfig( bolt.listen_address, "localhost:8888" )
            	
                .newGraphDatabase();

        
        
        System.in.read();
	}
}
