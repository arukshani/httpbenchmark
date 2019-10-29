# http-benchmark

HTTP/1.1 and HTTP/2 implementation to compare the performance of the two. 

This is a simple echo service written using netty. Receiving payload will be echoed back to the caller. 

1) Execute the following command to build the jar and copy the p12 file  
 mvn package shade:shade  
 cp ./src/main/resources/ballerinaKeystore.p12 ./target
 
2) To start HTTP/1.1 with TLS, use the following command    
 java -jar ./target/http-benchmark-1.0-SNAPSHOT.jar --ssl --key-store-file ./target/ballerinaKeystore.p12 --key-store-password ballerina  
 
3) To start HTTP/2 with TLS, use the following command  
 java -jar ./target/http-benchmark-1.0-SNAPSHOT.jar --ssl --key-store-file ./target/ballerinaKeystore.p12 --key-store-password ballerina --http2
 