JBoss Drools monitoring 
=======================

The goal of this project is to create an embbedable framework to monitor JBoss Drools knowledge bases and sessions using JMX, and that can be integrated into a webapp or a standalone application. As a proof of concept in this repository you can also find a minimal Eclipse RCP application using the drools-monitoring project.

Current features
----------------

* XML and API configuration.
* Automatic knowledge base and sessions discovery.
* Configurable knowledge Base and sessions metrics scanner.
* Connection recovery in case of connection lost or JVM restart.
* Custom metrics, recovery and discoverer metrics.
* Pluggable metrics persistence mechanism.
* Easy to configure using the Spring Framework.

What's next:

* Improve the pluggable persistence (Initial implementation is done).
* Custom reporting framework.
* A testing framework is needed.
* Nobody knows!

What doesn't supports yet (but it's supposed to do soon):

* JMX connection timeout (JMX specification doesn't supports timeout so it needs to be implemented in another way. There is current poc on it).

How to use it
-------------

Just write the next lines of code in a Java project:

	MonitoringConfigurationReader configurationReader = DroolsMonitoringFactory.newMonitoringConfigurationReader("/configuration.xml");
    MonitoringConfiguration configuration = configurationReader.read();
    DroolsMonitoring monitor = DroolsMonitoringFactory.newDroolsMonitoring(configuration);
    monitor.start();

And don't forget to create a configuration file with the next format:

	<?xml version="1.0" encoding="UTF-8"?>
	<configuration>
  	  <connections>
        <agent id="jvm1" address="localhost" port="9003" scanInterval="3000" recoveryInterval="10000" />
        <agent id="jvm2" address="192.168.0.11" port="9004" scanInterval="1000" recoveryInterval="5000" />
	  </connections>
	</configuration>

And that's all!

Contributing
------------
Do you feel in mood to contribute? Great, contributions are always welcome. Just fork it or ask me to join the project.
Questions are always welcome.

