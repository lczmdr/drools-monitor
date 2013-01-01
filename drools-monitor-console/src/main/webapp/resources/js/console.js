function MonitorinAgent(id, address, port, connected, scanInterval, recoveryInterval) {
    this.id = id;
    this.address = address;
    this.port = port;
    this.connected = connected;
    this.scanInterval = scanInterval;
    this.recoveryInterval = recoveryInterval;
}

var selectedKnowledgeBase = undefined;
var selectedKnowledgeSession = undefined;

$(document).ready(function() {
    renderMonitoringAgents();
    bindConfigurationButtons();
    bindKnowledgeBaseDetailsButton();
});

function bindKnowledgeBaseDetailsButton() {
    $('#knowledgeBaseDetailsButton').click(function(e) {
        var detailsModal = $('#knowledgeBaseDetailsModal');
        $('#knowledgeBaseDetailsModalLabel').text(selectedKnowledgeBase.knowledgeBaseId.$ + ' details');
        var serviceUri = selectedKnowledgeBase.agentId.$ + '/kbase/' + selectedKnowledgeSession.knowledgeBaseId.$ + '/metrics';
        serviceCall(serviceUri, function(response) {
            var knowledgeBaseMetric = $.parseJSON(JSON.stringify(response, undefined, 2));
            $.each(knowledgeBaseMetric, function(index, metric) {
                $('#kbaseTimestamp').text('Timestamp: ' + metric.timestamp.$);
                $('#kbaseSessionCount').text('Knowledge Session count: ' + metric.sessionCount.$);
                var packagesTable = $('#packagesTable');
                var packages = metric.packages.$.split(';');
                for (i=0; i < packages.length; i++) {
                    var packageRow = $(document.createElement('tr'));
                    packageRow.append($(document.createElement('td')).text(packages[i]));
                    packagesTable.append(packageRow);
                }
                var globalsTable = $('#globalsTable');
                $.each(metric.globals, function(index, global) {
                    var globalRow = $(document.createElement('tr'));
                    globalRow.append($(document.createElement('td')).text(global.name.$));
                    globalRow.append($(document.createElement('td')).text(global.classType.$));
                    globalsTable.append(globalRow);
                });
            });
        }, function (e) {
            alert('errorr');
        });
        detailsModal.modal();
    });
}

function renderMonitoringAgents(){
    var monitoringAgentList = $('#monitoringAgentList');

    serviceCall('agents', function(response) {
        var agentsData = $.parseJSON(JSON.stringify(response, undefined, 2));
        var agents = new Array();
        $.each(agentsData, function(array, item) {
                $.each(item, function(monitoringAgentId, monitoringAgentData) {
                        var agent = new MonitorinAgent(monitoringAgentData.id.$, 
                                                       monitoringAgentData.address.$,
                                                       monitoringAgentData.port.$,
                                                       monitoringAgentData.connected.$,
                                                       monitoringAgentData.scanInterval.$,
                                                       monitoringAgentData.recoveryInterval.$);
                        agents.push(agent);
                    }
                );
            }
        );
        $.each(agents, function(index, agent) {
            var menuElement = $(document.createElement('li')).attr('id', 'menuElement');
            if (index==0) menuElement.addClass('active');
            var menuElementLink = $(document.createElement('a')).attr('href', '#').text(agent.id);
            menuElementLink.click({id : agent.id}, function(e) {
                activateCurrentNavListElement(menuElement);
                renderKnowledgeBases(e.data);
            });
            menuElement.append(menuElementLink);
            monitoringAgentList.append(menuElement);
        });
        if (agents.length > 0) {
            renderKnowledgeBases(agents[0]);
            $('#content').show();
            $('#layout').show();
        }
        else {
            $('#layout').empty();
            $('#warningAlert').show();
        }
        dismissErrorMessages();
    }, function() {
        showErrorMessage('layout', createErrroMessageElement('Monitoring agents information not available'))
    });
}

function createErrroMessageElement(message) {
    $('#errorAlertMessage').text(message);
    var errorAlert = $('#errorAlertTemplate').clone();
    errorAlert.attr('id', 'errorAlert');
    errorAlert.show();
    return errorAlert;
}

function showErrorMessage(parentElementName, errroMessageElement) {
    var parentElement = $('#' + parentElementName);
    parentElement.hide();
    parentElement.append(errroMessageElement);
    parentElement.show();
}   

function activateCurrentNavListElement(menuElement) {
    $('li[id=menuElement]').removeClass();
    menuElement.addClass('active');
}

function renderKnowledgeBases(monitoringAgent) {
    var knowledgeBaseNav = $('#knowledgeBaseNav');
    knowledgeBaseNav.empty();
    $('#knowledgeBaseContent').empty();
    serviceCall(monitoringAgent.id + '/kbases', function(response) {
        var knowledgeBasesData = $.parseJSON(JSON.stringify(response, undefined, 2));
        $.each(knowledgeBasesData, function(array, item) {
                $.each(item, function(index, knowledgeBases) {
                    if ($.isArray(knowledgeBases)) {
                        $.each(knowledgeBases, function(index, knowledgeBase) {
                            createKnowledgeBaseTab(index, knowledgeBase, knowledgeBaseNav);
                        });
                    }
                    else {
                        createKnowledgeBaseTab(0, knowledgeBases, knowledgeBaseNav);
                    }
                });
            }
        );
        if (selectedKnowledgeBase != undefined) renderKnowledgeSessions(monitoringAgent.id, selectedKnowledgeBase.knowledgeBaseId.$);
        dismissErrorMessages();
    }, function(e) {
        showErrorMessage('content', createErrroMessageElement(e.responseText));
    });
}

function createKnowledgeBaseTab(index, knowledgeBaseData, knowledgeBaseNav) {
    var navElement = $(document.createElement('li'));
    if (index==0) {
        navElement.addClass('active');
        selectedKnowledgeBase = knowledgeBaseData;
    }
    var knowledgeBaseId = knowledgeBaseData.knowledgeBaseId.$;
    var navElementLink = $(document.createElement('a')).attr('href', '#' + knowledgeBaseId).attr('data-toggle', 'tab').text(knowledgeBaseId);
    navElement.append(navElementLink);
    knowledgeBaseNav.append(navElement);

    var knowledgeBasePanel = $(document.createElement('div')).attr('id', knowledgeBaseId);
    if (index==0)
        knowledgeBasePanel.addClass('tab-pane active');
    else
        knowledgeBasePanel.addClass('tab-pane');
    $('#knowledgeBaseContent').append(knowledgeBasePanel);
    knowledgeBasePanel.append($('#informationTemplate').clone().show());
}

function renderKnowledgeSessions(monitoringAgentId, knowledgeBaseId) {
    var knowledgeSessionNav = $('#knowledgeSessionNav');
    knowledgeSessionNav.empty();

    serviceCall(monitoringAgentId + '/kbase/' + knowledgeBaseId + '/ksessions', function(response) {
        var knowledgeSessionData = $.parseJSON(JSON.stringify(response, undefined, 2));
        $.each(knowledgeSessionData, function(array, item) {
            $.each(item, function(index, knowledgeSessions) {
                    if ($.isArray(knowledgeSessions)) {
                        $.each(knowledgeSessions, function(index, knowledgeSession) {
                            createKnowledgeSessionNavs(index, knowledgeSession, knowledgeSessionNav);
                        });
                    }
                    else
                        createKnowledgeSessionNavs(0, knowledgeSessions, knowledgeSessionNav);
                });
        });
        $('#knowledgeBaseContent').show();
        dismissErrorMessages();
    }, function(e) {
        $('#knowledgeBaseContent').hide();
        showErrorMessage('knowledgeSessionSection', createErrroMessageElement(e.responseText));
    });
}

function createKnowledgeSessionNavs(index, knowledgeSession, knowledgeSessionNav) {
    var navElement = $(document.createElement('li'));
    if (index==0) {
        navElement.addClass('active');
        selectedKnowledgeSession = knowledgeSession;
    }
    var navElementLink = $(document.createElement('a')).attr('href', '').text('ksession-' + knowledgeSession.knowledgeSessionId.$);
    navElementLink.click(function(e) {
        selectedKnowledgeSession = knowledgeSession;
        showKnowledgeSessionInformation(navElement);
        e.preventDefault();
    });
    navElement.append(navElementLink);
    knowledgeSessionNav.append(navElement);
    if (index==0)
        showKnowledgeSessionInformation(navElement);
};

function showKnowledgeSessionInformation(navElement) {
    $('#knowledgeSessionNav > li').removeClass();
    navElement.addClass('active');
    var knowledgeSessionInfoDiv = $('#knowledgeSessionInfo');
    knowledgeSessionInfoDiv.empty();
    knowledgeSessionInfoDiv.append($('#knowledgeSessionInfoAccordion').clone().show());

    var serviceUri = selectedKnowledgeBase.agentId.$ + '/kbase/' + selectedKnowledgeSession.knowledgeBaseId.$ + '/ksession/' + 
        selectedKnowledgeSession.knowledgeSessionId.$ + '/metrics/1';

    var statistics = $('#ksessionStatisticsBody');
    statistics.empty();

    serviceCall(serviceUri, function(response) {
        var metricsData = $.parseJSON(JSON.stringify(response, undefined, 2));
        $.each(metricsData, function(array, item) {
            $.each(item, function(index, metric) {
                if ($.isArray(metric.ruleStats.ruleMetric)) {
                    $.each(metric.ruleStats.ruleMetric, function(index, ruleMetric) {
                        renderRuleStatistic(statistics, ruleMetric);
                    });
                }
                else {
                    if (metric.ruleStats.ruleMetric != undefined) {
                        renderRuleStatistic(statistics, metric.ruleStats.ruleMetric);
                    }
                    else {
                        var metricRow = $(document.createElement('tr'));
                        metricRow.append(createMetricField('no information available').attr('colspan', '5'));
                        statistics.append(metricRow);
                    }
                }
            });
        });
        dismissErrorMessages();
    }, function(e) {
        var errorMessageRow = $(document.createElement('tr'));
        var errorMessageField = $(document.createElement('td')).attr('colspan', '5');
        var errorMessageElement = createErrroMessageElement(e.responseText);
        errorMessageElement.children().removeClass();
        errorMessageElement.children().addClass('offset1');
        errorMessageField.append(errorMessageElement);
        errorMessageRow.append(errorMessageField);
        showErrorMessage('ksessionStatisticsBody', errorMessageRow);
    });
}

function renderRuleStatistic(statistics, ruleMetric) {
    var metricRow = $(document.createElement('tr'));
    metricRow.append(createMetricField(ruleMetric.name.$));
    metricRow.append(createMetricField(ruleMetric.activationsCreated.$));
    metricRow.append(createMetricField(ruleMetric.activationsCancelled.$));
    metricRow.append(createMetricField(ruleMetric.activationsFired.$));
    metricRow.append(createMetricField(ruleMetric.firingTime.$));
    statistics.append(metricRow);
}

function createMetricField(value) {
    return $(document.createElement('td')).text(value);
}

function dismissErrorMessages() {
    $('#errorAlert').remove();
}

function bindConfigurationButtons() {
    bindConfigurationCheckButton('showRuleStatistics', 'ruleStatisticsGroup');
    bindConfigurationCheckButton('showGraphics', 'graphicsGroup');
    bindConfigurationCheckButton('showProcessStatistics', 'processGroup');
    bindConfigurationCheckButton('showProcessInstancesStatistics', 'processInstancesGroup');
}

function bindConfigurationCheckButton(buttonName, groupName) {
    $('#' + buttonName).click(function (e) {
        if ($("#" + buttonName).is(":checked"))
            $("#" + groupName).show();
        else
            $("#" + groupName).hide();
    });
}


function serviceCall(serviceName, serviceCallback, errorServiceCallback) {
    var serviceEndpoint = getServicesUrl() + serviceName;
    $.get(serviceEndpoint, serviceCallback, 'json').error(errorServiceCallback);
}

function getServicesUrl() {
    var servicesBaseDomain = $.console.domain;
    var appName = $.console.appName;
    var restService = $.console.restService;
    return servicesBaseDomain + appName + restService;
}
