package com.lucazamador.drools.monitor.console.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucazamador.drools.monitor.console.model.KnowledgeBaseDataList;
import com.lucazamador.drools.monitor.console.model.KnowledgeSessionDataList;
import com.lucazamador.drools.monitor.console.model.MonitoringAgentDataList;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;

@Path("/kservice")
@Component
public class KnowledgeRestService {

    @Autowired
    private DroolsMonitoringService droolsMonitoringService;

    @GET
    @Path("/agents")
    @Produces("application/json")
    @BadgerFish
    public Response getMonitoringAgents() {
        MonitoringAgentDataList monitoringAgents = droolsMonitoringService.getMonitoringAgents();
        return Response.status(200).entity(monitoringAgents).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbases")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeBaseInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        MonitoringAgent monitoringAgent = droolsMonitoringService.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return Response.status(404).build();
        }
        if (!monitoringAgent.isConnected()) {
            return Response.status(404).entity("monitoring agent is disconnected").build();
        }
        KnowledgeBaseDataList knowledgeBases = droolsMonitoringService.getKnowledgeBaseInformation(monitoringAgentId);
        if (knowledgeBases == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeBases).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbases/previous")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeBasePreviousInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        KnowledgeBaseDataList knowledgeBases = droolsMonitoringService.getKnowledgeBaseInformation(monitoringAgentId);
        if (knowledgeBases == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeBases).build();
    }

    @GET
    @Path("/{monitoringAgentId}/ksessions")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessionInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        MonitoringAgent monitoringAgent = droolsMonitoringService.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return Response.status(404).build();
        }
        if (!monitoringAgent.isConnected()) {
            return Response.status(404).entity("monitoring agent is disconnected").build();
        }
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService
                .getKnowledgeSessionInformation(monitoringAgentId);
        if (knowledgeSessions == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

    @GET
    @Path("/{monitoringAgentId}/ksessions/previous")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessionPreviousInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService
                .getKnowledgeSessionInformation(monitoringAgentId);
        if (knowledgeSessions == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbase/{knowledgeBaseId}/ksessions")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessions(@PathParam("monitoringAgentId") String monitoringAgentId,
            @PathParam("knowledgeBaseId") String knowledgeBaseId) {
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService.getKnowledgeSessions(monitoringAgentId,
                knowledgeBaseId);
        if (knowledgeSessions == null || knowledgeSessions.isEmpty()) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

}
