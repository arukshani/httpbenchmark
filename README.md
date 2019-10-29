# HTTP/1.1 vs HTTP/2.0 - Throughput - Echo Service

HTTP/1.1 and HTTP/2 implementation to compare the performance of the two. 

This is a simple echo service written using netty. Receiving payload will be echoed back to the caller. 

1) Execute the following command to build the jar and copy the p12 file  
 mvn package shade:shade  
 cp ./src/main/resources/ballerinaKeystore.p12 ./target
 
2) To start HTTP/1.1 with TLS, use the following command    
 java -jar ./target/http-benchmark-1.0-SNAPSHOT.jar --ssl --key-store-file ./target/ballerinaKeystore.p12 --key-store-password ballerina  
 
3) To start HTTP/2 with TLS, use the following command  
 java -jar ./target/http-benchmark-1.0-SNAPSHOT.jar --ssl --key-store-file ./target/ballerinaKeystore.p12 --key-store-password ballerina --http2
 
Request Path >> https://localhost:8688/service/EchoService  

TPS for HTTP/1.1 vs HTTP/2 (100 users/50 bytes payload) - JMeter4 - 5 minutes

HTTP/1.1 = 18633  
HTTP/2.0 = 15968  

| Version | Samples | Average | Min | Max | Std.Dev. | Error% | Throughout | Received KB/sec | Sent KB/sec | Avg.Bytes |  
| --- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | 
|HTTP/1.1|5589685|4|0|303|8.256355974767581|0.0|18633.401337413576|3493.7627507650454|5222.447445154|192.0|  
|HTTP/2.0|4769609|0|0|222|0.47701301506342764|0.0|15968.639306830586|41.99599323160443|0.0|2.6930220066257005| 




